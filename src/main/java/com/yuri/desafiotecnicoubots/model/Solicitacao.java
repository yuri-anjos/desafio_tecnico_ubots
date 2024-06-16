package com.yuri.desafiotecnicoubots.model;

import com.yuri.desafiotecnicoubots.enums.TipoSolicitacao;

public class Solicitacao {
	private String id;
	private Cliente cliente;
	private TipoSolicitacao tipoSolicitacao;
	private String titulo;

	public Solicitacao() {
	}

	public Solicitacao(String id, Cliente cliente, TipoSolicitacao tipoSolicitacao, String titulo) {
		this.id = id;
		this.cliente = cliente;
		this.tipoSolicitacao = tipoSolicitacao;
		this.titulo = titulo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public TipoSolicitacao getTipoSolicitacao() {
		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(TipoSolicitacao tipoSolicitacao) {
		this.tipoSolicitacao = tipoSolicitacao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return "\nSolicitacao{" +
				"id='" + id + '\'' +
				", cliente=" + cliente +
				", tipoSolicitacao=" + tipoSolicitacao +
				", titulo='" + titulo + '\'' +
				'}';
	}
}
