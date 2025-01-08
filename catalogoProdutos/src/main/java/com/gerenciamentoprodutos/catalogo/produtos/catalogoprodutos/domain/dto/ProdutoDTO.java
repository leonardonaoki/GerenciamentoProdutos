package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto;

import java.math.BigDecimal;

public record ProdutoDTO(
    long Id,
    String Descricao,
    BigDecimal Preco,
    long QuantidadeEstoque
){}
