package com.logisticaEntrega.logistica.entrega.infrastructure.gateway;

import org.springframework.stereotype.Component;

import com.logisticaEntrega.logistica.entrega.domain.entity.InsertEntregadorDTO;
import com.logisticaEntrega.logistica.entrega.domain.mapper.IEntregadorMapper;
import com.logisticaEntrega.logistica.entrega.infrastructure.repository.IEntregadorRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EntregadorGateway implements IEntregadorGateway {

	private final IEntregadorRepository entregadorRepository;
	private final IEntregadorMapper entregadorMapper;
	
	@Override
	public void inserirEntregador(InsertEntregadorDTO dto) {
	  entregadorRepository.save(entregadorMapper.toEntity(dto));
	}
}
