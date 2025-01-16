package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job.mapper;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class JobProdutoMapper implements FieldSetMapper<ProdutosEntity>  {

    @Override
    public ProdutosEntity mapFieldSet(FieldSet fieldSet) {
        ProdutosEntity produto = new ProdutosEntity();
        produto.setDescricao(fieldSet.readString("descricao"));
        produto.setPreco(fieldSet.readBigDecimal("preco"));
        produto.setQuantidadeEstoque(fieldSet.readLong("quantidadeEstoque"));
        return produto;
    }
}