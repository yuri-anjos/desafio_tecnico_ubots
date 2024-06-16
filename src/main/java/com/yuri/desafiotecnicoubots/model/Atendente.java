package com.yuri.desafiotecnicoubots.model;

import com.yuri.desafiotecnicoubots.enums.SetorAtendimento;

import java.util.HashSet;
import java.util.Set;

public class Atendente {
	private String id;
	private String nome;
	private SetorAtendimento time;
	private Set<Solicitacao> solicitacoes;

	private static final int LIMITE_CAPACIDADE = 3;

	public Atendente() {
	}

	public Atendente(String id, String nome, SetorAtendimento time) {
		this.id = id;
		this.nome = nome;
		this.time = time;
		this.solicitacoes = new HashSet<>();
	}

	public boolean podeAtender() {
		return solicitacoes.size() < LIMITE_CAPACIDADE;
	}

	public void atender(Solicitacao solicitacao) {
		solicitacoes.add(solicitacao);
	}

	public boolean liberarSolicitacao(Solicitacao solicitacao) {
		return solicitacoes.removeIf(item -> item.getId().equals(solicitacao.getId()));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public SetorAtendimento getTime() {
		return time;
	}

	public void setTime(SetorAtendimento time) {
		this.time = time;
	}
}
