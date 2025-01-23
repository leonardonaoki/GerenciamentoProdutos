package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByProdutoIdResponseDTO;

public interface IProdutoGateway {
    FindByProdutoIdResponseDTO findById(long id);
}
