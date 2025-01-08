package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.mapper;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.entityjpa.ProdutoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper implements IProdutoMapper{
    @Override
    public ProdutoDTO toDTO(ProdutoEntity produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getQuantidadeEstoque()
        );
    }
}
