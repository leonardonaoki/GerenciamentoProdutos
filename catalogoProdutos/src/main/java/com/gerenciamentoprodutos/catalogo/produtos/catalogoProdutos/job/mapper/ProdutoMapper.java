package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.job.mapper;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.infrastructure.entityjpa.ProdutoEntity;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class ProdutoMapper implements FieldSetMapper<ProdutoEntity> {

    @Override
    public ProdutoEntity mapFieldSet(FieldSet fieldSet) throws BindException {
        try{
            ProdutoEntity produto = new ProdutoEntity();
            produto.setPreco(fieldSet.readLong("preco"));
            produto.setDescricao(fieldSet.readString("descricao"));
            produto.setQuantidadeEstoque(fieldSet.readLong("quantidadeEstoque"));
            return produto;
        }catch (Exception e){
            throw new RuntimeException("Não foi possível converter para o tipo Produto." + e.toString());
        }

    }
}