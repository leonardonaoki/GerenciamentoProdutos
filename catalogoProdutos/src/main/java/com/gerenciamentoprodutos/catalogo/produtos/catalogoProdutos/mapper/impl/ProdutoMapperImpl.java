package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.mapper.impl;


import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.entities.ProdutoEntity;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.mapper.IProdutoMapper;

public class ProdutoMapperImpl implements IProdutoMapper {
    @Override
    public ProdutoDTO toDTO(ProdutoEntity produto){
        return new ProdutoDTO(
                produto.getId(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeEstoque());
        }
}
