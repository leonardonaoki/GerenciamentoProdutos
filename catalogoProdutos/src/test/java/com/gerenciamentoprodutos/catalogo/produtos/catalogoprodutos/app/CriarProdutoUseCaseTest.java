package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CriarProdutoUseCaseTest {

    @Mock
    private IProdutoGateway produtoGateway;  // Mock da dependência

    @InjectMocks
    private CriarProdutoUseCase criarProdutoUseCase;  // Classe a ser testada

    @BeforeEach
    void setUp() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarProdutoQuandoDadosValidos() {
        // Dados de entrada (DTO) e produto esperado
        InsertAndUpdateProdutoDTO produtoDTO = new InsertAndUpdateProdutoDTO("Produto Teste", new BigDecimal(100),200);
        ProdutoDTO produtoEsperado = new ProdutoDTO(1, "Produto Teste", new BigDecimal(100),200);

        // Configurando o mock para retornar o produto quando o método for chamado
        when(produtoGateway.criarProduto(produtoDTO)).thenReturn(produtoEsperado);

        // Chama o método que estamos testando
        ProdutoDTO produtoRetornado = criarProdutoUseCase.criarProduto(produtoDTO);

        // Verificação: garantir que o produto retornado seja igual ao esperado
        assertNotNull(produtoRetornado);
        assertEquals(produtoEsperado, produtoRetornado);

        // Verifica se o método criarProduto do gateway foi chamado com o DTO correto
        verify(produtoGateway, times(1)).criarProduto(produtoDTO);
    }
}
