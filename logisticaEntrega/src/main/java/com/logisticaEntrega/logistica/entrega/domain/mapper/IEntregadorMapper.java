package com.logisticaEntrega.logistica.entrega.domain.mapper;

import com.logisticaEntrega.logistica.entrega.domain.entity.InsertEntregadorDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.entityjpa.EntregadorEntity;

public interface IEntregadorMapper {

	EntregadorEntity toEntity(InsertEntregadorDTO dto);

}
