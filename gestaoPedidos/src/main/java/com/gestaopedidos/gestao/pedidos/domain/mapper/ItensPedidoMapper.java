package com.gestaopedidos.gestao.pedidos.domain.mapper;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.ItensPedidosEntity;
import org.springframework.stereotype.Component;

@Component
public class ItensPedidoMapper implements IItensPedidoMapper{
    @Override
    public ProdutoDTO toProdutoDTO(ItensPedidosEntity produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setIdProduto(produto.getIdProduto());
        dto.setQuantidadeDesejada(produto.getQuantidade());
        return dto;
    }
}
