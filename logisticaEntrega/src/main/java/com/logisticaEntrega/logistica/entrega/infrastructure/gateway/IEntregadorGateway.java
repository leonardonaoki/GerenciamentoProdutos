package com.logisticaEntrega.logistica.entrega.infrastructure.gateway;

import com.logisticaEntrega.logistica.entrega.domain.entity.InsertEntregadorDTO;

public interface IEntregadorGateway {

	void inserirEntregador(InsertEntregadorDTO dto);

}
