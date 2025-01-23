package com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ItensPedidosEntityTest {

    @Test
    void testGettersAndSetters() {
        ItensPedidosEntity item = new ItensPedidosEntity();

        item.setId(1L);
        assertEquals(1L, item.getId());

        item.setIdPedido(2L);
        assertEquals(2L, item.getIdPedido());

        item.setIdProduto(3L);
        assertEquals(3L, item.getIdProduto());

        item.setQuantidade(4L);
        assertEquals(4L, item.getQuantidade());
    }
}
