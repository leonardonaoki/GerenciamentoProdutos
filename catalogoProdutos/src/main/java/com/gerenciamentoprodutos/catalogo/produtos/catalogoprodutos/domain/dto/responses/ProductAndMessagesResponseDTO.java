package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;

public record ProductAndMessagesResponseDTO(
    ProdutoDTO Produto,
    int HttpStatusCode,
    String Message
){}
