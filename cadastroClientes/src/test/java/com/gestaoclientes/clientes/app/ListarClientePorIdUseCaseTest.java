package com.gestaoclientes.clientes.app;

import com.gerenciamentoclientes.clientes.app.ListarClientePorIdUseCase;
import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.exception.SystemBaseHandleException;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.IClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarClientePorIdUseCaseTest {

    @Mock
    private IClienteGateway clienteGateway;  // Mock da dependência

    @InjectMocks
    private ListarClientePorIdUseCase listarClientePorIdUseCase;  // Classe a ser testada

    @BeforeEach
    void setUp() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarClienteQuandoClienteExistir() throws SystemBaseHandleException {
        long idCliente = 1L;
        ClienteDTO clienteEsperado = new ClienteDTO(idCliente, "Cliente Teste", "cliente@teste.com");

        when(clienteGateway.listarClientePorId(idCliente)).thenReturn(clienteEsperado);

        ClienteDTO clienteRetornado = listarClientePorIdUseCase.listarClientePorId(idCliente);

        assertNotNull(clienteRetornado);
        assertEquals(clienteEsperado, clienteRetornado);

        verify(clienteGateway, times(1)).listarClientePorId(idCliente);
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoExistir() throws SystemBaseHandleException {
        int idCliente = 999;

        when(clienteGateway.listarClientePorId(idCliente)).thenThrow(new SystemBaseHandleException("Cliente não encontrado"));

        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> {
            listarClientePorIdUseCase.listarClientePorId(idCliente);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(clienteGateway, times(1)).listarClientePorId(idCliente);
    }
}
