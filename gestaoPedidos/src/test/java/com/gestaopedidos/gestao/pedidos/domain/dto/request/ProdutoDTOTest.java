package com.gestaopedidos.gestao.pedidos.domain.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoDTOTest {

    @Test
    void testIdProdutoGetterSetter() {
        ProdutoDTO produto = new ProdutoDTO();
        long expectedId = 12345L;
        produto.setIdProduto(expectedId);
        assertEquals(expectedId, produto.getIdProduto());
    }

    @Test
    void testQuantidadeDesejadaGetterSetter() {
        ProdutoDTO produto = new ProdutoDTO();
        long expectedQuantidade = 10L;
        produto.setQuantidadeDesejada(expectedQuantidade);
        assertEquals(expectedQuantidade, produto.getQuantidadeDesejada());
    }
}
