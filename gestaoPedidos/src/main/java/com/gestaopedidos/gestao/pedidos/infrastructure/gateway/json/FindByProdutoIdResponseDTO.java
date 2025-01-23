package com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json;

public record FindByProdutoIdResponseDTO(
    FindByIdProdutoDTO Produto,
    int HttpStatusCode,
    String Message
){}
