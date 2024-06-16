package com.yuri.desafiotecnicoubots.controller;

import com.yuri.desafiotecnicoubots.model.Solicitacao;
import com.yuri.desafiotecnicoubots.service.AtendimentoService;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atendimentos")
public class AtendimentoController {

	private final Logger logger = LoggerFactory.getLogger(AtendimentoController.class);

	private final AtendimentoService service;

	public AtendimentoController(AtendimentoService service) {
		this.service = service;
	}

	@PostMapping("/solicitar")
	public ResponseEntity<Void> solicitarAtendimento(@RequestBody Solicitacao solicitacao) {
		this.service.adicionarSolicitacao(solicitacao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping("/finalizar")
	public ResponseEntity<Void> finalizarAtendimento(@RequestBody Solicitacao solicitacao) {
		try {
			this.service.finalizarSolicitacao(solicitacao);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (BadRequestException ex) {
			logger.error("Erro: {}", ex.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
