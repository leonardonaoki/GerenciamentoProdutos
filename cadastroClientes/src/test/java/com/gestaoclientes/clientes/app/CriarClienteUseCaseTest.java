package com.gestaoclientes.clientes.app;

import com.gerenciamentoclientes.clientes.app.CriarClienteUseCase;
import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.entity.ClienteDomain;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.IClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CriarClienteUseCaseTest {

    @Mock
    private IClienteGateway clienteGateway;  // Mock da dependência

    @InjectMocks
    private CriarClienteUseCase criarClienteUseCase;  // Classe a ser testada

    @BeforeEach
    void setUp() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarClienteQuandoDadosValidos() {

        ClienteDomain clienteDomain = new ClienteDomain();
        clienteDomain.setNome("Cliente Teste");
        clienteDomain.setEmail("Rua Teste");
        ClienteDTO clienteEsperado = new ClienteDTO(1L, "Cliente Teste", "Rua Teste");

        // Configurando o mock para retornar o cliente quando o método for chamado
        when(clienteGateway.criarCliente(clienteDomain)).thenReturn(clienteEsperado);

        // Chama o método que estamos testando
        ClienteDTO clienteRetornado = criarClienteUseCase.criarCliente(clienteDomain);

        // Verificação: garantir que o cliente retornado seja igual ao esperado
        assertNotNull(clienteRetornado);
        assertEquals(clienteEsperado, clienteRetornado);

        // Verifica se o método criarCliente do gateway foi chamado com o DTO correto
        verify(clienteGateway, times(1)).criarCliente(clienteDomain);
    }
}