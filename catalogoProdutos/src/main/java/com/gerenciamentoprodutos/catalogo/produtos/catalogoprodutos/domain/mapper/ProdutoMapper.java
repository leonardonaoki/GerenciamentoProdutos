package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.mapper;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.ProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper implements IProdutoMapper{
    @Override
    public ProdutoDTO toDTO(ProdutosEntity produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeEstoque()
        );
    }
    @Override
    public ProdutosDomain toDomain(InsertAndUpdateProdutoDTO dto){
        ProdutosDomain domain = new ProdutosDomain();
        domain.setDescricao(dto.Descricao());
        domain.setPreco(dto.Preco());
        domain.setQuantidadeEstoque(dto.QuantidadeEstoque());
        return domain;
    }
    @Override
    public ProdutosEntity toEntity(ProdutosDomain produtosDomain) {
        ProdutosEntity entity = new ProdutosEntity();
        entity.setDescricao(produtosDomain.getDescricao());
        entity.setPreco(produtosDomain.getPreco());
        entity.setQuantidadeEstoque(produtosDomain.getQuantidadeEstoque());
        return entity;
    }
}