package com.yuri.desafiotecnicoubots.service;

import com.yuri.desafiotecnicoubots.enums.SetorAtendimento;
import com.yuri.desafiotecnicoubots.model.Atendente;
import com.yuri.desafiotecnicoubots.model.Solicitacao;
import com.yuri.desafiotecnicoubots.model.Time;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;

@Service
public class AtendimentoService {
	private Map<SetorAtendimento, Time> times;

	public AtendimentoService() {
		times = new EnumMap<>(SetorAtendimento.class);
		var atendentesCartoes = new HashSet<Atendente>();
		atendentesCartoes.add(new Atendente("1", "Alice", SetorAtendimento.CARTOES));
		atendentesCartoes.add(new Atendente("4", "Wesley", SetorAtendimento.CARTOES));

		times.put(SetorAtendimento.CARTOES, new Time(atendentesCartoes, SetorAtendimento.CARTOES));

		var atendentesEmprestimo = new HashSet<Atendente>();
		atendentesEmprestimo.add(new Atendente("2", "Marcus", SetorAtendimento.EMPRESTIMOS));
		times.put(SetorAtendimento.EMPRESTIMOS, new Time(atendentesEmprestimo, SetorAtendimento.EMPRESTIMOS));

		var atendentesAssuntosDiversos = new HashSet<Atendente>();
		atendentesAssuntosDiversos.add(new Atendente("3", "Eric", SetorAtendimento.OUTROS_ASSUNTOS));
		times.put(SetorAtendimento.OUTROS_ASSUNTOS, new Time(atendentesAssuntosDiversos, SetorAtendimento.CARTOES));
	}

	public void adicionarSolicitacao(Solicitacao solicitacao) {
		var setorAtendimento = definirSetorAtendimento(solicitacao);
		var time = times.get(setorAtendimento);
		time.adicionarSolicitacao(solicitacao);
		time.distribuirSolicitacoes();
	}

	public void finalizarSolicitacao(Solicitacao solicitacao) throws BadRequestException {
		var setorAtendimento = definirSetorAtendimento(solicitacao);
		times.get(setorAtendimento).removerSolicitacao(solicitacao);
	}

	private SetorAtendimento definirSetorAtendimento(Solicitacao solicitacao) {
		return switch (solicitacao.getTipoSolicitacao()) {
			case PROBLEMAS_COM_CARTAO -> SetorAtendimento.CARTOES;
			case CONTRATACAO_DE_EMPRESTIMO -> SetorAtendimento.EMPRESTIMOS;
			default -> SetorAtendimento.OUTROS_ASSUNTOS;
		};
	}
}
