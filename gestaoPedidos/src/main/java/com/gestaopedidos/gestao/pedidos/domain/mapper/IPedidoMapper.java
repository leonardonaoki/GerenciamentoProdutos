package com.gestaopedidos.gestao.pedidos.domain.mapper;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;

public interface IPedidoMapper {
    PedidoDTO toDTO(PedidosEntity produto);
    PedidosEntity toEntity(InsertPedidoDTO dto);
}
