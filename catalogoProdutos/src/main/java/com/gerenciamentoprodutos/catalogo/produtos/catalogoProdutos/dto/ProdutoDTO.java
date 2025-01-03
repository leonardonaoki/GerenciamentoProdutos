package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.dto;

public record ProdutoDTO(
    long Id,
    String Descricao,
    long Preco,
    long QuantidadeEstoque
){}
