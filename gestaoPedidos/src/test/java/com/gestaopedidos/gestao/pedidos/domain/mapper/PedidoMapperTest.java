package com.gestaopedidos.gestao.pedidos.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

class PedidoMapperTest {

    private PedidoMapper pedidoMapper;

    @BeforeEach
    public void setUp() {
        pedidoMapper = new PedidoMapper();
    }

    @Test
    void testToDTO() {
        PedidosEntity entity = new PedidosEntity();
        entity.setIdPedido(1);
        entity.setIdCliente(2);
        entity.setStatus("Pending");
        entity.setPrecoFinal(new BigDecimal(100.0));
        entity.setCep("12345-678");
        entity.setLatitude(12.345678);
        entity.setLongitude(98.765432);

        PedidoDTO dto = pedidoMapper.toDTO(entity);

        assertEquals(1, dto.getIdPedido());
        assertEquals(2, dto.getIdCliente());
        assertEquals("Pending", dto.getStatus());
        assertEquals(new BigDecimal(100.0), dto.getPrecoFinal());
        assertEquals("12345-678", dto.getCep());
        assertEquals(12.345678, dto.getLatitude());
        assertEquals(98.765432, dto.getLongitude());
    }

    @Test
    void testToEntity() {
        InsertPedidoDTO dto = new InsertPedidoDTO(2, new ArrayList<>(),"12345-678", 12.345678, 98.765432);

        PedidosEntity entity = pedidoMapper.toEntity(dto);

        assertEquals(2, entity.getIdCliente());
        assertEquals("12345-678", entity.getCep());
        assertEquals(12.345678, entity.getLatitude());
        assertEquals(98.765432, entity.getLongitude());
    }
}
