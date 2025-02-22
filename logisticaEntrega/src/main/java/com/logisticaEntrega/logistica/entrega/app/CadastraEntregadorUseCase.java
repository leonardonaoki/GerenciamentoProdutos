package com.logisticaEntrega.logistica.entrega.app;

import org.springframework.stereotype.Service;

import com.logisticaEntrega.logistica.entrega.domain.entity.InsertEntregadorDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.IEntregadorGateway;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CadastraEntregadorUseCase {

	private final IEntregadorGateway entregadorGateway;

	public void inserirEntregador (InsertEntregadorDTO dto) {
		entregadorGateway.inserirEntregador(dto);
	}
}
