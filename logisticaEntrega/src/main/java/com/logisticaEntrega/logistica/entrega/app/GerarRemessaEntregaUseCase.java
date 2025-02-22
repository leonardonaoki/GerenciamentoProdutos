package com.logisticaEntrega.logistica.entrega.app;

import java.util.List;

import org.springframework.stereotype.Service;

import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.IGestaoPedidoGateway;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GerarRemessaEntregaUseCase {
	private final IGestaoPedidoGateway gestaoPedidoGateway;
	
	public void gerarRemessaEntrega(String cep,long idEntregador) {
		List<Long> pedidoList = gestaoPedidoGateway.buscaPedidoPorCep(cep).lista();
		for(Long id:pedidoList) {
			gestaoPedidoGateway.atualizaEntregadorPedido(id, idEntregador);
		}
		
	}
}
