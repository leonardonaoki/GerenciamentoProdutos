package com.gestaoclientes.clientes.app;

import com.gerenciamentoclientes.clientes.app.DeletaClientePorIdUseCase;
import com.gerenciamentoclientes.clientes.exception.SystemBaseHandleException;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.IClienteGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeletaClientePorIdUseCaseTest {

    @Mock
    private IClienteGateway clienteGateway;  // Mock da dependência

    @InjectMocks
    private DeletaClientePorIdUseCase deletaClientePorIdUseCase;  // Classe a ser testada

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveDeletarClienteQuandoIdValido() throws SystemBaseHandleException {
        // ID do cliente a ser deletado
        long idCliente = 1;

        // Chama o metodo que estamos testando
        deletaClientePorIdUseCase.deletaClientePorId(idCliente);

        // Verifica se o metodo deletarClientePorId foi chamado com o id correto
        verify(clienteGateway, times(1)).deletarClientePorId(idCliente);
    }

    @Test
    void deveLancarExcecaoQuandoFalharDeletarCliente() throws SystemBaseHandleException {
        // ID do cliente a ser deletado
        long idCliente = 1;

        // Configura o mock para lançar uma exceção quando o metodo deletarClientePorId for chamado
        doThrow(new SystemBaseHandleException("Erro ao deletar cliente")).when(clienteGateway).deletarClientePorId(idCliente);

        // Chama o metodo e verifica se a exceção é lançada corretamente
        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> {
            deletaClientePorIdUseCase.deletaClientePorId(idCliente);
        });

        // Verifica se a mensagem da exceção é a esperada
        assertEquals("Erro ao deletar cliente", exception.getMessage());

        // Verifica se o metodo deletarClientePorId do gateway foi chamado corretamente
        verify(clienteGateway, times(1)).deletarClientePorId(idCliente);
    }
}