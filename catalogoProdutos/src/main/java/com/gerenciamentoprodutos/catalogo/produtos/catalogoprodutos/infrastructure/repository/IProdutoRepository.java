package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.repository;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProdutoRepository extends JpaRepository<ProdutosEntity,Long> {
}
