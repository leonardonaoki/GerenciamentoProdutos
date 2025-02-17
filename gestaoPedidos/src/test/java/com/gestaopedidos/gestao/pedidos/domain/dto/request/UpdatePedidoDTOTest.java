package com.gestaopedidos.gestao.pedidos.domain.dto.request;

import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpdatePedidoDTOTest {

    @Test
    void testUpdatePedidoDTOCreation() {
        StatusEnum status = StatusEnum.CONCLUIDO;
        String cep = "12345-678";
        Double latitude = -23.550520;
        Double longitude = -46.633308;

        UpdatePedidoDTO updatePedidoDTO = new UpdatePedidoDTO(status, cep, latitude, longitude);

        assertEquals(status, updatePedidoDTO.status());
        assertEquals(cep, updatePedidoDTO.CEP());
        assertEquals(latitude, updatePedidoDTO.Latitude());
        assertEquals(longitude, updatePedidoDTO.Longitude());
    }

    @Test
    void testUpdatePedidoDTOWithNullValues() {
        StatusEnum status = null;
        String cep = null;
        Double latitude = null;
        Double longitude = null;

        UpdatePedidoDTO updatePedidoDTO = new UpdatePedidoDTO(status, cep, latitude, longitude);

        assertNull(updatePedidoDTO.status());
        assertNull(updatePedidoDTO.CEP());
        assertNull(updatePedidoDTO.Latitude());
        assertNull(updatePedidoDTO.Longitude());
    }

    @Test
    void testUpdatePedidoDTOWithInvalidCEP() {
        StatusEnum status = StatusEnum.CANCELADO;
        String invalidCep = "invalid-cep";
        Double latitude = -23.550520;
        Double longitude = -46.633308;

        UpdatePedidoDTO updatePedidoDTO = new UpdatePedidoDTO(status, invalidCep, latitude, longitude);

        assertEquals(status, updatePedidoDTO.status());
        assertEquals(invalidCep, updatePedidoDTO.CEP());
        assertEquals(latitude, updatePedidoDTO.Latitude());
        assertEquals(longitude, updatePedidoDTO.Longitude());
    }
}
