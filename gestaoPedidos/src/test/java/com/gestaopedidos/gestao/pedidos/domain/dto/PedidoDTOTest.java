package com.gestaopedidos.gestao.pedidos.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PedidoDTOTest {

    private PedidoDTO pedidoDTO;

    @BeforeEach
    public void setUp() {
        pedidoDTO = new PedidoDTO();
    }

    @Test
    void testIdPedido() {
        long idPedido = 12345L;
        pedidoDTO.setIdPedido(idPedido);
        assertEquals(idPedido, pedidoDTO.getIdPedido());
    }

    @Test
    void testIdCliente() {
        long idCliente = 67890L;
        pedidoDTO.setIdCliente(idCliente);
        assertEquals(idCliente, pedidoDTO.getIdCliente());
    }

    @Test
    void testListaProdutos() {
        List<ProdutoDTO> listaProdutos = new ArrayList<>();
        pedidoDTO.setListaProdutos(listaProdutos);
        assertNotNull(pedidoDTO.getListaProdutos());
        assertEquals(listaProdutos, pedidoDTO.getListaProdutos());
    }

    @Test
    void testPrecoFinal() {
        BigDecimal precoFinal = new BigDecimal("99.99");
        pedidoDTO.setPrecoFinal(precoFinal);
        assertEquals(precoFinal, pedidoDTO.getPrecoFinal());
    }

    @Test
    void testStatus() {
        String status = "Pending";
        pedidoDTO.setStatus(status);
        assertEquals(status, pedidoDTO.getStatus());
    }

    @Test
    void testCep() {
        String cep = "12345-678";
        pedidoDTO.setCep(cep);
        assertEquals(cep, pedidoDTO.getCep());
    }

    @Test
    void testLatitude() {
        Double latitude = 12.345678;
        pedidoDTO.setLatitude(latitude);
        assertEquals(latitude, pedidoDTO.getLatitude());
    }

    @Test
    void testLongitude() {
        Double longitude = 98.765432;
        pedidoDTO.setLongitude(longitude);
        assertEquals(longitude, pedidoDTO.getLongitude());
    }
}
