package com.logisticaEntrega.logistica.entrega.domain.mapper;

import org.springframework.stereotype.Component;

import com.logisticaEntrega.logistica.entrega.domain.entity.InsertEntregadorDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.entityjpa.EntregadorEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EntregadorMapper implements IEntregadorMapper{

	@Override
	public EntregadorEntity toEntity(InsertEntregadorDTO dto) {
		EntregadorEntity entity = new EntregadorEntity();
		entity.setDocEntregador(dto.getDocEntregador());
		entity.setNomeEntregador(dto.getNomeEntregador());
		return entity;
	}
}
