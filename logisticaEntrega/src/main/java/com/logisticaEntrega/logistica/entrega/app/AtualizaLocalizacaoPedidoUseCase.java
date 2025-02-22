package com.logisticaEntrega.logistica.entrega.app;

import org.springframework.stereotype.Service;

import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.IGestaoPedidoGateway;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.RequisicaoGestaoPedidoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizaLocalizacaoPedidoUseCase {

	private final IGestaoPedidoGateway gestaoPedidoGateway;

	public void atualizaLocalizaPedido (long idPedido,RequisicaoGestaoPedidoDTO dto) {
		gestaoPedidoGateway.atualizaLocalizacao(idPedido, dto);
	}
}
