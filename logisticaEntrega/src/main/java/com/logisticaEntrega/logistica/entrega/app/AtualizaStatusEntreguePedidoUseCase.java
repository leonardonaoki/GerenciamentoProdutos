package com.logisticaEntrega.logistica.entrega.app;

import org.springframework.stereotype.Service;

import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.IGestaoPedidoGateway;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizaStatusEntreguePedidoUseCase {

	private final IGestaoPedidoGateway gestaoPedidoGateway;

	public void atualizaStatusEntrgue(long idPedido) {
	    gestaoPedidoGateway.concluiEntregaPedido(idPedido);
	}
}
