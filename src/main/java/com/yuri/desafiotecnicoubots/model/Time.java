package com.yuri.desafiotecnicoubots.model;

import com.yuri.desafiotecnicoubots.enums.SetorAtendimento;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Time {
	private Set<Atendente> atendentes;
	private final BlockingQueue<Solicitacao> solicitacoes = new LinkedBlockingDeque<>();
	private SetorAtendimento setor;

	private final Logger logger = LoggerFactory.getLogger(Time.class);

	public Time(Set<Atendente> atendentes, SetorAtendimento setor) {
		this.atendentes = atendentes;
		this.setor = setor;
	}

	public void adicionarSolicitacao(Solicitacao solicitacao) {
		this.solicitacoes.add(solicitacao);
	}

	public void distribuirSolicitacoes() {
		for (var atendente : atendentes) {
			while (!this.solicitacoes.isEmpty() && atendente.podeAtender()) {
				var solicitacao = this.solicitacoes.poll();
				atendente.atender(solicitacao);
				logger.info("Atendente {} está atendendo a solicitação: {} - {}", atendente.getNome(), solicitacao.getId(), solicitacao.getTitulo());
			}
		}
		logger.info("Solicitações em espera: \n{}\n", solicitacoes);
	}

	public void removerSolicitacao(String solicitacaoId) throws BadRequestException {
		for (var atendente : atendentes) {
			if (atendente.liberarSolicitacao(solicitacaoId)) {
				logger.info("Solicitação {} removida!", solicitacaoId);
				distribuirSolicitacoes();
				return;
			}
		}

		throw new BadRequestException("Solicitação não encontrada ou impossível finalizar!");
	}

	public Set<Atendente> getAtendentes() {
		return atendentes;
	}

	public BlockingQueue<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public SetorAtendimento getSetor() {
		return setor;
	}
}
