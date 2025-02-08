package com.gestaoclientes.clientes.app;

import com.gerenciamentoclientes.clientes.app.AtualizaClientePorIdUseCase;
import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
import com.gerenciamentoclientes.clientes.domain.entity.ClienteDomain;
import com.gerenciamentoclientes.clientes.exception.SystemBaseHandleException;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.IClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtualizaClientePorIdUseCaseTest {

    @Mock
    private IClienteGateway clienteGateway;  // Mock da dependência

    @InjectMocks
    private AtualizaClientePorIdUseCase atualizaClientePorIdUseCase;  // Classe a ser testada

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAtualizarClienteQuandoDadosValidos() throws SystemBaseHandleException {
        long idCliente = 1;
        ClienteDomain clienteDomain = new ClienteDomain();
        clienteDomain.setNome("Cliente Atualizado");
        clienteDomain.setEmail("Rua Atualizada");

        ClienteDTO clienteAtualizado = new ClienteDTO(idCliente, "Cliente Atualizado", "Rua Atualizada");

        // Configurando o mock para retornar o cliente atualizado quando o método for chamado
        when(clienteGateway.atualizarClientePorId(idCliente, clienteDomain)).thenReturn(clienteAtualizado);

        // Chama o método que estamos testando
        ClienteDTO clienteRetornado = atualizaClientePorIdUseCase.atualizaClientePorId(idCliente, clienteDomain);

        // Verificação: garantir que o cliente retornado seja igual ao esperado
        assertNotNull(clienteRetornado);
        assertEquals(clienteAtualizado, clienteRetornado);

        // Verifica se o método atualizarClientePorId do gateway foi chamado com os parâmetros corretos
        verify(clienteGateway, times(1)).atualizarClientePorId(idCliente, clienteDomain);
    }

    @Test
    void deveLancarExcecaoQuandoFalharAtualizarCliente() throws SystemBaseHandleException {
        // Dados de entrada (DTO)
        long idCliente = 1;
        ClienteDomain clienteDomain = new ClienteDomain();
        clienteDomain.setNome("Cliente Inválido");
        clienteDomain.setEmail("");

        // Configura o mock para lançar uma exceção quando o método atualizarClientePorId for chamado com dados inválidos
        when(clienteGateway.atualizarClientePorId(idCliente, clienteDomain)).thenThrow(new SystemBaseHandleException("Erro ao atualizar cliente"));

        // Chama o método e verifica se a exceção é lançada corretamente
        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> {
            atualizaClientePorIdUseCase.atualizaClientePorId(idCliente, clienteDomain);
        });

        // Verifica se a mensagem da exceção é a esperada
        assertEquals("Erro ao atualizar cliente", exception.getMessage());

        // Verifica se o método atualizarClientePorId do gateway foi chamado corretamente
        verify(clienteGateway, times(1)).atualizarClientePorId(idCliente, clienteDomain);
    }
}