package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarProdutoPorIdUseCaseTest {

    @Mock
    private IProdutoGateway produtoGateway;  // Mock da dependência

    @InjectMocks
    private ListarProdutoPorIdUseCase listarProdutoPorIdUseCase;  // Classe a ser testada

    @BeforeEach
    void setUp() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarProdutoQuandoProdutoExistir() throws SystemBaseHandleException {
        int idProduto = 1;
        ProdutoDTO produtoEsperado = new ProdutoDTO(idProduto, "Produto Teste", new BigDecimal(100),200);

        when(produtoGateway.listarProdutoPorId(idProduto)).thenReturn(produtoEsperado);

        ProdutoDTO produtoRetornado = listarProdutoPorIdUseCase.listarProdutoPorId(idProduto);

        assertNotNull(produtoRetornado);
        assertEquals(produtoEsperado, produtoRetornado);

        verify(produtoGateway, times(1)).listarProdutoPorId(idProduto);
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() throws SystemBaseHandleException {
        int idProduto = 999;

        when(produtoGateway.listarProdutoPorId(idProduto)).thenThrow(new SystemBaseHandleException("Produto não encontrado"));

        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> {
            listarProdutoPorIdUseCase.listarProdutoPorId(idProduto);
        });

        assertEquals("Produto não encontrado", exception.getMessage());
        verify(produtoGateway, times(1)).listarProdutoPorId(idProduto);
    }
}