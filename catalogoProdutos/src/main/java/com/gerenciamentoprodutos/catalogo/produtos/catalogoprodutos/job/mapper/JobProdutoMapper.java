package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job.mapper;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutoEntity;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class JobProdutoMapper implements FieldSetMapper<ProdutoEntity>  {

    @Override
    public ProdutoEntity mapFieldSet(FieldSet fieldSet) {
        ProdutoEntity produto = new ProdutoEntity();
        produto.setPreco(fieldSet.readBigDecimal("preco"));
        produto.setDescricao(fieldSet.readString("descricao"));
        produto.setQuantidadeEstoque(fieldSet.readLong("quantidadeEstoque"));
        return produto;
    }
}