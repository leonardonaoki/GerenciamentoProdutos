package com.gestaopedidos.gestao.pedidos.domain.mapper;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.ItensPedidosEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IItensPedidoMapper {
    @Mapping(source = "entity.idProduto", target = "idProduto")
    @Mapping(source = "entity.quantidade", target = "quantidadeDesejada")
    ProdutoDTO toProdutoDTO(ItensPedidosEntity produto);
}
