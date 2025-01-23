package com.gestaopedidos.gestao.pedidos.domain.dto.responses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

class ListPedidosResponseDTOTest {

    @Test
    void testPedidosField() {
        // Arrange
        Page<PedidoDTO> mockPage = mock(Page.class);
        ListPedidosResponseDTO responseDTO = new ListPedidosResponseDTO(mockPage);

        // Act
        Page<PedidoDTO> result = responseDTO.Pedidos();

        // Assert
        assertEquals(mockPage, result);
    }
}