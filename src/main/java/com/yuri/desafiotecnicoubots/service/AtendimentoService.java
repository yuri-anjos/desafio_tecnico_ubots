package com.yuri.desafiotecnicoubots.service;

import com.yuri.desafiotecnicoubots.dto.FinalizarAtendimentoDTO;
import com.yuri.desafiotecnicoubots.enums.SetorAtendimento;
import com.yuri.desafiotecnicoubots.enums.TipoSolicitacao;
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
	private final Map<SetorAtendimento, Time> times = new EnumMap<>(SetorAtendimento.class);

	public AtendimentoService() {
		var atendentesCartoes = new HashSet<Atendente>();
		atendentesCartoes.add(new Atendente("1", "Alice", SetorAtendimento.CARTOES));
		atendentesCartoes.add(new Atendente("4", "Wesley", SetorAtendimento.CARTOES));
		times.put(SetorAtendimento.CARTOES, new Time(atendentesCartoes, SetorAtendimento.CARTOES));

		var atendentesEmprestimo = new HashSet<Atendente>();
		atendentesEmprestimo.add(new Atendente("2", "Marcus", SetorAtendimento.EMPRESTIMOS));
		times.put(SetorAtendimento.EMPRESTIMOS, new Time(atendentesEmprestimo, SetorAtendimento.EMPRESTIMOS));

		var atendentesAssuntosDiversos = new HashSet<Atendente>();
		atendentesAssuntosDiversos.add(new Atendente("3", "Eric", SetorAtendimento.OUTROS_ASSUNTOS));
		times.put(SetorAtendimento.OUTROS_ASSUNTOS, new Time(atendentesAssuntosDiversos, SetorAtendimento.OUTROS_ASSUNTOS));
	}

	public void adicionarSolicitacao(Solicitacao solicitacao) {
		var setorAtendimento = definirSetorAtendimento(solicitacao.getTipoSolicitacao());
		var time = times.get(setorAtendimento);
		time.adicionarSolicitacao(solicitacao);
		time.distribuirSolicitacoes();
	}

	public void finalizarSolicitacao(FinalizarAtendimentoDTO solicitacao) throws BadRequestException {
		var setorAtendimento = definirSetorAtendimento(solicitacao.tipoSolicitacao());
		times.get(setorAtendimento).removerSolicitacao(solicitacao.id());
	}

	private SetorAtendimento definirSetorAtendimento(TipoSolicitacao tipoSolicitacao) {
		return switch (tipoSolicitacao) {
			case PROBLEMAS_COM_CARTAO -> SetorAtendimento.CARTOES;
			case CONTRATACAO_DE_EMPRESTIMO -> SetorAtendimento.EMPRESTIMOS;
			default -> SetorAtendimento.OUTROS_ASSUNTOS;
		};
	}
}
