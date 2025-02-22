package com.logisticaEntrega.logistica.entrega.app;

import java.util.List;

import org.springframework.stereotype.Service;

import com.logisticaEntrega.logistica.entrega.infrastructure.dto.PedidoDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.IGestaoPedidoGateway;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscaRemessaPedidoUseCase {
	
	private final IGestaoPedidoGateway gestaoPedidoGateway;

	public List<PedidoDTO> buscaRemessa(Long idEntregador){
		return gestaoPedidoGateway.buscaPedidoEmAberto(idEntregador).dto();
	}
}
