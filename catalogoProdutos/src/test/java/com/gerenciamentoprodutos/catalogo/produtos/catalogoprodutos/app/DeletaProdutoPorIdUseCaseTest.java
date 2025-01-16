package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeletaProdutoPorIdUseCaseTest {

    @Mock
    private IProdutoGateway produtoGateway;  // Mock da dependência

    @InjectMocks
    private DeletaProdutoPorIdUseCase deletaProdutoPorIdUseCase;  // Classe a ser testada

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveDeletarProdutoQuandoIdValido() throws SystemBaseHandleException {
        // ID do produto a ser deletado
        long idProduto = 1;

        // Chama o método que estamos testando
        deletaProdutoPorIdUseCase.deletaProdutoPorId(idProduto);

        // Verifica se o método deletarProdutoPorId foi chamado com o id correto
        verify(produtoGateway, times(1)).deletarProdutoPorId(idProduto);
    }

    @Test
    void deveLancarExcecaoQuandoFalharDeletarProduto() throws SystemBaseHandleException {
        // ID do produto a ser deletado
        long idProduto = 1;

        // Configura o mock para lançar uma exceção quando o método deletarProdutoPorId for chamado
        doThrow(new SystemBaseHandleException("Erro ao deletar produto")).when(produtoGateway).deletarProdutoPorId(idProduto);

        // Chama o método e verifica se a exceção é lançada corretamente
        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> {
            deletaProdutoPorIdUseCase.deletaProdutoPorId(idProduto);
        });

        // Verifica se a mensagem da exceção é a esperada
        assertEquals("Erro ao deletar produto", exception.getMessage());

        // Verifica se o método deletarProdutoPorId do gateway foi chamado corretamente
        verify(produtoGateway, times(1)).deletarProdutoPorId(idProduto);
    }
}
