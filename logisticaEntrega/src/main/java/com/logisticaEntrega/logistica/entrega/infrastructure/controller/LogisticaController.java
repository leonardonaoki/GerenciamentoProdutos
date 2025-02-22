package com.logisticaEntrega.logistica.entrega.infrastructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logisticaEntrega.logistica.entrega.app.AtualizaLocalizacaoPedidoUseCase;
import com.logisticaEntrega.logistica.entrega.app.AtualizaStatusEntreguePedidoUseCase;
import com.logisticaEntrega.logistica.entrega.app.BuscaRemessaPedidoUseCase;
import com.logisticaEntrega.logistica.entrega.app.CadastraEntregadorUseCase;
import com.logisticaEntrega.logistica.entrega.app.GerarRemessaEntregaUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/logistica")
@RequiredArgsConstructor
public class LogisticaController implements IControllerDocumentationLogistica {

	private final AtualizaLocalizacaoPedidoUseCase atualizaLocalizacaoPedidoUseCase;
	private final AtualizaStatusEntreguePedidoUseCase atualizaStatusEntreguePedidoUseCase;
	private final BuscaRemessaPedidoUseCase buscaRemessaPedidoUseCase;
	private final CadastraEntregadorUseCase cadastraEntregadorUseCase;
	private final GerarRemessaEntregaUseCase gerarRemessaEntregaUseCase;
	
	
}
