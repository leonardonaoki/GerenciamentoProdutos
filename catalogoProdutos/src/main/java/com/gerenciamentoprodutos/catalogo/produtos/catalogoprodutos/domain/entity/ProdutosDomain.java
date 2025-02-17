package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutosDomain {
    private String descricao;
    private BigDecimal preco;
    private long quantidadeEstoque;
}
