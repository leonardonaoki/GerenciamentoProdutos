package com.gestaoclientes.clientes.app;

import com.gerenciamentoclientes.clientes.app.ListarClientesUseCase;
import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.response.ListaClientesResponseDTO;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.IClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ListarClientesUseCaseTest {

    @Mock
    private IClienteGateway clienteGateway;  // Mock da dependência

    @InjectMocks
    private ListarClientesUseCase listarClientesUseCase;  // Classe a ser testada

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarListaDeClientesQuandoListarClientesForChamado() {
        int offset = 0;
        int limit = 10;

        ClienteDTO cliente1 = new ClienteDTO(1L, "Cliente 1", "cliente1@teste.com");
        ClienteDTO cliente2 = new ClienteDTO(2L, "Cliente 2", "cliente2@teste.com");
        List<ClienteDTO> listaDeClientes = Arrays.asList(cliente1, cliente2);
        Page<ClienteDTO> page = new PageImpl<>(listaDeClientes);

        when(clienteGateway.listarClientes(offset, limit)).thenReturn(page);

        ListaClientesResponseDTO resposta = listarClientesUseCase.listarClientes(offset, limit);

        assertNotNull(resposta);
        assertEquals(2, resposta.clientes().getSize());
        assertEquals(cliente1, resposta.clientes().getContent().get(0));
        assertEquals(cliente2, resposta.clientes().getContent().get(1));

        verify(clienteGateway, times(1)).listarClientes(offset, limit);
    }
}