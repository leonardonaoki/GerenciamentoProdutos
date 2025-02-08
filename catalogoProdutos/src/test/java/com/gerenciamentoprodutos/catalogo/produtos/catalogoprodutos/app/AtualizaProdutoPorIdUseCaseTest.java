package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.ProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AtualizaProdutoPorIdUseCaseTest {

    @Mock
    private IProdutoGateway produtoGateway;  // Mock da dependência

    @InjectMocks
    private AtualizaProdutoPorIdUseCase atualizaProdutoPorIdUseCase;  // Classe a ser testada

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAtualizarProdutoQuandoDadosValidos() throws SystemBaseHandleException {
        long idProduto = 1;
        ProdutosDomain produtosDomain = new ProdutosDomain();
        produtosDomain.setDescricao("Produto Atualizado");
        produtosDomain.setPreco(new BigDecimal(200));
        produtosDomain.setQuantidadeEstoque(300);

        ProdutoDTO produtoAtualizado = new ProdutoDTO(idProduto, "Produto Atualizado", new BigDecimal(200),300);

        // Configurando o mock para retornar o produto atualizado quando o método for chamado
        when(produtoGateway.atualizarProdutoPorId(idProduto, produtosDomain)).thenReturn(produtoAtualizado);

        // Chama o método que estamos testando
        ProdutoDTO produtoRetornado = atualizaProdutoPorIdUseCase.atualizaProdutoPorId(idProduto, produtosDomain);

        // Verificação: garantir que o produto retornado seja igual ao esperado
        assertNotNull(produtoRetornado);
        assertEquals(produtoAtualizado, produtoRetornado);

        // Verifica se o método atualizarProdutoPorId do gateway foi chamado com os parâmetros corretos
        verify(produtoGateway, times(1)).atualizarProdutoPorId(idProduto, produtosDomain);
    }

    @Test
    void deveLancarExcecaoQuandoFalharAtualizarProduto() throws SystemBaseHandleException {
        // Dados de entrada (DTO)
        long idProduto = 1;
        ProdutosDomain produtosDomain = new ProdutosDomain();
        produtosDomain.setDescricao("Produto Inválido");
        produtosDomain.setPreco(new BigDecimal(-50)); // Preço inválido
        produtosDomain.setQuantidadeEstoque(-300);

        // Configura o mock para lançar uma exceção quando o método atualizarProdutoPorId for chamado com dados inválidos
        when(produtoGateway.atualizarProdutoPorId(idProduto, produtosDomain)).thenThrow(new SystemBaseHandleException("Erro ao atualizar produto"));

        // Chama o método e verifica se a exceção é lançada corretamente
        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> {
            atualizaProdutoPorIdUseCase.atualizaProdutoPorId(idProduto, produtosDomain);
        });

        // Verifica se a mensagem da exceção é a esperada
        assertEquals("Erro ao atualizar produto", exception.getMessage());

        // Verifica se o método atualizarProdutoPorId do gateway foi chamado corretamente
        verify(produtoGateway, times(1)).atualizarProdutoPorId(idProduto, produtosDomain);
    }
}
