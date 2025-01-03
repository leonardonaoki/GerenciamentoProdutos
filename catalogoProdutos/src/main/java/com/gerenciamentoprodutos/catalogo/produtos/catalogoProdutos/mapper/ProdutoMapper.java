package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.mapper;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.entities.ProdutoEntity;

public interface ProdutoMapper {
    ProdutoDTO toDTO(ProdutoEntity produto);
}
