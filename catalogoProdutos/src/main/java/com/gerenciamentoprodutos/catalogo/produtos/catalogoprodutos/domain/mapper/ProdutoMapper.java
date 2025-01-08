package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.mapper;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutoEntity;
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
