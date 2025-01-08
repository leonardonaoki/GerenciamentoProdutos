package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.entityjpa;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "produto")
@Entity
@Data
public class ProdutoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String descricao;
    private long preco;
    private long quantidadeEstoque;
}
