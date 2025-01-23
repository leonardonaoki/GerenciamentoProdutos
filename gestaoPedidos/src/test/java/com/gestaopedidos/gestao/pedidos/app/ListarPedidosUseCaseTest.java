package com.gestaopedidos.gestao.pedidos.app;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.responses.ListPedidosResponseDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

class ListarPedidosUseCaseTest {

    @Mock
    private IPedidoGateway pedidoGateway;

    @InjectMocks
    private ListarPedidosUseCase listarPedidosUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarPedidos() {
        // Arrange
        int offset = 0;
        int limit = 10;
        List<PedidoDTO> pedidoDTOS = new ArrayList<>();
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTOS.add(pedidoDTO);
        Page<PedidoDTO> pedidos = new PageImpl<>(pedidoDTOS);
        when(pedidoGateway.listarPedidos(offset, limit)).thenReturn(pedidos);

        // Act
        ListPedidosResponseDTO response = listarPedidosUseCase.listarPedidos(offset, limit);

        // Assert
        assertNotNull(response);
        assertEquals(pedidos, response.Pedidos());
        verify(pedidoGateway, times(1)).listarPedidos(offset, limit);
    }
}
