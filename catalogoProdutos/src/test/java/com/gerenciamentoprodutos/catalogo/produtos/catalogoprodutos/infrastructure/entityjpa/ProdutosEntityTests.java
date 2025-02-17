package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProdutosEntityTests {
    @Test
    void deveCriarESetarAsPropriedadesCorretamente(){
        long idTeste = 1;
        String descTeste = "Descrição Teste";
        BigDecimal precoTeste = new BigDecimal("21.76");

        long quantidadeEstoqueTeste = 300;

        ProdutosEntity produto = new ProdutosEntity();
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
