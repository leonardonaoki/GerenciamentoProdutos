package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses.ListProdutosResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ListarProdutosUseCaseTest {

    @Mock private IProdutoGateway produtoGateway;
    @InjectMocks
    private ListarProdutosUseCase listarProdutosUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveRetornarListaDeProdutosQuandoListarProdutosForChamado() {
        int offset = 0;
        int limit = 10;

        ProdutoDTO produto1 = new ProdutoDTO(1, "Produto 1", new BigDecimal(100),300);
        ProdutoDTO produto2 = new ProdutoDTO(2, "Produto 2", new BigDecimal(200),400);
        List<ProdutoDTO> listaDeProdutos = Arrays.asList(produto1, produto2);
        Page<ProdutoDTO> page = new PageImpl<>(listaDeProdutos);

        when(produtoGateway.listarProdutos(offset, limit)).thenReturn(page);

        ListProdutosResponseDTO resposta = listarProdutosUseCase.listarProdutos(offset, limit);

        assertNotNull(resposta);
        assertEquals(2, resposta.Produtos().getSize());
        assertEquals(produto1, resposta.Produtos().getContent().get(0));
        assertEquals(produto2, resposta.Produtos().getContent().get(1));

        verify(produtoGateway, times(1)).listarProdutos(offset, limit);
    }
}
