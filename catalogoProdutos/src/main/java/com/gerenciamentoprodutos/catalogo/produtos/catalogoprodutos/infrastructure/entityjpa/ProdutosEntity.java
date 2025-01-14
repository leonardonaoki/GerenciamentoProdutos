package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "produtos")
@Entity
@Getter
@Setter
public class ProdutosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String descricao;
    private BigDecimal preco;
    private long quantidadeEstoque;
}