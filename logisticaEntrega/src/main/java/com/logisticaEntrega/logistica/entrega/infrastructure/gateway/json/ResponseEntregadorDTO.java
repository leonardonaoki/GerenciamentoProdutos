package com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json;

import java.util.List;

import com.logisticaEntrega.logistica.entrega.infrastructure.dto.PedidoDTO;

public record ResponseEntregadorDTO(
		List<PedidoDTO> dto) {

}
