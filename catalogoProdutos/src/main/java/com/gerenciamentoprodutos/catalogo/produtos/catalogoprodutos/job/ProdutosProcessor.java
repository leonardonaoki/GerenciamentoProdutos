package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;
import org.springframework.batch.item.ItemProcessor;

public class ProdutosProcessor implements ItemProcessor<ProdutosEntity, ProdutosEntity> {

    @Override
    public ProdutosEntity process(ProdutosEntity item) throws Exception {
        if (item.getPreco().doubleValue() < 0) {
            throw new SystemBaseHandleException("Produto importado não pode ter preço abaixo de zero.");
        }
        if (item.getQuantidadeEstoque() < 0) {
            throw new SystemBaseHandleException("Produto importado não pode ter quantidade_estoque menor do que zero.");
        }

        return item;
    }
}
