package com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa;

import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PedidosEntityTest {

    @Test
    void testDefaultStatus() {
        PedidosEntity pedidosEntity = new PedidosEntity();
        assertEquals(StatusEnum.EM_CURSO.name(), pedidosEntity.getStatus());
    }

    @Test
    void testSettersAndGetters() {
        PedidosEntity pedidosEntity = new PedidosEntity();

        pedidosEntity.setIdPedido(1L);
        assertEquals(1L, pedidosEntity.getIdPedido());

        pedidosEntity.setIdCliente(2L);
        assertEquals(2L, pedidosEntity.getIdCliente());

        pedidosEntity.setStatus("COMPLETED");
        assertEquals("COMPLETED", pedidosEntity.getStatus());

        pedidosEntity.setCep("12345-678");
        assertEquals("12345-678", pedidosEntity.getCep());

        pedidosEntity.setLatitude(12.34);
        assertEquals(12.34, pedidosEntity.getLatitude());

        pedidosEntity.setLongitude(56.78);
        assertEquals(56.78, pedidosEntity.getLongitude());

        pedidosEntity.setPrecoFinal(new BigDecimal("99.99"));
        assertEquals(new BigDecimal("99.99"), pedidosEntity.getPrecoFinal());
    }
}