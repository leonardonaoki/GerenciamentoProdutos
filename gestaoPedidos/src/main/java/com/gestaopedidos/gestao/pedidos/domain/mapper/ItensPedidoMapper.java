package com.gestaopedidos.gestao.pedidos.domain.mapper;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.ItensPedidosEntity;
import org.springframework.stereotype.Component;

@Component
public class ItensPedidoMapper implements IItensPedidoMapper{
    @Override
    public ProdutoDTO toProdutoDTO(ItensPedidosEntity produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.idProduto = produto.getIdProduto();
        dto.quantidadeDesejada = produto.getQuantidade();
        return dto;
    }
}
