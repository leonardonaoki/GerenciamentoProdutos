package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ListaProdutosDomainTest {

    @Test
    void testCreateListaProdutosDomain() {
        long idProduto = 1L;
        long quantidadeDesejada = 10L;
        ListaProdutosDomain listaProdutos = new ListaProdutosDomain(idProduto, quantidadeDesejada);

        assertEquals(idProduto, listaProdutos.idProduto());
        assertEquals(quantidadeDesejada, listaProdutos.quantidadeDesejada());
    }

    @Test
    void testGetIdProduto() {
        long idProduto = 2L;
        long quantidadeDesejada = 20L;
        ListaProdutosDomain listaProdutos = new ListaProdutosDomain(idProduto, quantidadeDesejada);

        assertEquals(idProduto, listaProdutos.idProduto());
    }

    @Test
    void testGetQuantidadeDesejada() {
        long idProduto = 3L;
        long quantidadeDesejada = 30L;
        ListaProdutosDomain listaProdutos = new ListaProdutosDomain(idProduto, quantidadeDesejada);

        assertEquals(quantidadeDesejada, listaProdutos.quantidadeDesejada());
    }
}