package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.entityjpa.ProdutoEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;


class ProdutoEntityTests {
    @Test
    void deveCriarESetarAsPropriedadesCorretamente(){
        long idTeste = 1;
        String descTeste = "Descrição Teste";
        BigDecimal precoTeste = new BigDecimal("21.76");

        long quantidadeEstoqueTeste = 300;

        ProdutoEntity produto = new ProdutoEntity();
        produto.setId(idTeste);
        produto.setDescricao(descTeste);
        produto.setPreco(precoTeste);
        produto.setQuantidadeEstoque(quantidadeEstoqueTeste);

        assertEquals(idTeste,produto.getId());
        assertEquals(descTeste,produto.getDescricao());
        assertEquals(precoTeste,produto.getPreco());
        assertEquals(quantidadeEstoqueTeste,produto.getQuantidadeEstoque());
    }
}
