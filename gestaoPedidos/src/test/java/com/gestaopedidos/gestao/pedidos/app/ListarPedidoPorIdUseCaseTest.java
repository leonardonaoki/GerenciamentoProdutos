package com.gestaopedidos.gestao.pedidos.app;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ListarPedidoPorIdUseCaseTest {

    @Mock
    private IPedidoGateway pedidoGateway;

    @InjectMocks
    private ListarPedidoPorIdUseCase listarPedidoPorIdUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarPedidoPorId_Success() throws SystemBaseHandleException {
        // Arrange
        int id = 1;
        PedidoDTO expectedPedido = new PedidoDTO();
        when(pedidoGateway.listarPedidoPorId(id)).thenReturn(expectedPedido);

        // Act
        PedidoDTO actualPedido = listarPedidoPorIdUseCase.listarPedidoPorId(id);

        // Assert
        assertEquals(expectedPedido, actualPedido);
        verify(pedidoGateway, times(1)).listarPedidoPorId(id);
    }

    @Test
    void testListarPedidoPorId_ThrowsException() throws SystemBaseHandleException {
        // Arrange
        int id = 1;
        when(pedidoGateway.listarPedidoPorId(id)).thenThrow(new SystemBaseHandleException("Error"));

        // Act & Assert
        assertThrows(SystemBaseHandleException.class, () -> {
            listarPedidoPorIdUseCase.listarPedidoPorId(id);
        });
        verify(pedidoGateway, times(1)).listarPedidoPorId(id);
    }
}