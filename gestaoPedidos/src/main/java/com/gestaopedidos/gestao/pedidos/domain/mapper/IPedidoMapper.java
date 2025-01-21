package com.gestaopedidos.gestao.pedidos.domain.mapper;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertAndUpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IPedidoMapper {
    @Mapping(source = "entity.idPedido", target = "IdPedido")
    @Mapping(source = "entity.idCliente", target = "IdCliente")
    @Mapping(source = "entity.status", target = "Status")
    @Mapping(source = "entity.precoFinal", target = "PrecoFinal")
    PedidoDTO toDTO(PedidosEntity produto);

    @Mapping(source = "idCliente", target = "entity.idCliente")
    PedidosEntity toEntity(InsertAndUpdatePedidoDTO dto);
}
