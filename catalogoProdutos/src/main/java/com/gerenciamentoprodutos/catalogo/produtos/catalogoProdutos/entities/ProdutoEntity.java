package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.entities;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "produto")
@Entity
@Data
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String descricao;
    private long preco;
    private long quantidadeEstoque;
}
