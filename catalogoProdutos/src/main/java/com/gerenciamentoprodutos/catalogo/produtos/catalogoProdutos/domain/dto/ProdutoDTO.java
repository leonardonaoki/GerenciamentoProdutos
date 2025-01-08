package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.dto;

public record ProdutoDTO(
    long Id,
    String Descricao,
    long Preco,
    long QuantidadeEstoque
){}
