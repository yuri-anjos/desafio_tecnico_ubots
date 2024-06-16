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
	private BlockingQueue<Solicitacao> solicitacoes;
	private SetorAtendimento setor;

	private final Logger logger = LoggerFactory.getLogger(Time.class);

	public Time(Set<Atendente> atendentes, SetorAtendimento setor) {
		this.solicitacoes = new LinkedBlockingDeque<>();
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
				logger.info("Atendente {} está atendendo a solicitação: {}", atendente.getNome(), solicitacao.getTitulo());
			}
		}
		logger.info("Solicitações em espera: \n{}\n", solicitacoes);
	}

	public void removerSolicitacao(Solicitacao solicitacao) throws BadRequestException {
		for (var atendente : atendentes) {
			if (atendente.liberarSolicitacao(solicitacao)) {
				logger.info("Solicitação {} removida!", solicitacao.getId());
				distribuirSolicitacoes();
				return;
			}
		}

		throw new BadRequestException("Solicitação não encontrada ou não pode ser finalizada!");
	}
}
