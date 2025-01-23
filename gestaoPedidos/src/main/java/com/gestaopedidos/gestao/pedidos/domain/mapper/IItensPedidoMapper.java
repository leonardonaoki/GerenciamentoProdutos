package com.gestaopedidos.gestao.pedidos.domain.mapper;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.ItensPedidosEntity;

public interface IItensPedidoMapper {
    ProdutoDTO toProdutoDTO(ItensPedidosEntity produto);
}
