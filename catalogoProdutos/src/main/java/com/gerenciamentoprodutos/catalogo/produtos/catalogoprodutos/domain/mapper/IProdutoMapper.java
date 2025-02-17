package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.mapper;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.ProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;

public interface IProdutoMapper {
    ProdutoDTO toDTO(ProdutosEntity produto);
    ProdutosEntity toEntity(ProdutosDomain produtosDomain);
    ProdutosDomain toDomain(InsertAndUpdateProdutoDTO dto);
}
