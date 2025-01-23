package com.gestaopedidos.gestao.pedidos.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IProdutoGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByIdProdutoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByProdutoIdResponseDTO;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.Arrays;

class CriarPedidoUseCaseTest {

    @Mock
    private IPedidoGateway pedidoGateway;

    @Mock
    private IProdutoGateway produtoGateway;

    @InjectMocks
    private CriarPedidoUseCase criarPedidoUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarPedido_Success() throws SystemBaseHandleException {
        InsertPedidoDTO dto = mock(InsertPedidoDTO.class);
        ProdutoDTO produto = mock(ProdutoDTO.class);
        FindByProdutoIdResponseDTO response = mock(FindByProdutoIdResponseDTO.class);
        FindByIdProdutoDTO produtoResponse = mock(FindByIdProdutoDTO.class);

        when(dto.listaProdutos()).thenReturn(Arrays.asList(produto));
        when(produto.getIdProduto()).thenReturn(1L);
        when(produtoGateway.findById(1L)).thenReturn(response);
        when(response.HttpStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(response.Produto()).thenReturn(produtoResponse);
        when(produto.getQuantidadeDesejada()).thenReturn(5L);
        when(produtoResponse.QuantidadeEstoque()).thenReturn(10L);
        when(produtoResponse.Preco()).thenReturn(new BigDecimal("100.00"));
        when(pedidoGateway.criarPedido(dto, new BigDecimal("100.00"))).thenReturn(new PedidoDTO());

        PedidoDTO result = criarPedidoUseCase.criarPedido(dto);

        assertNotNull(result);
        verify(pedidoGateway).criarPedido(dto, new BigDecimal("100.00"));
    }

    @Test
    void testCriarPedido_ProdutoNotAvailable() {
        InsertPedidoDTO dto = mock(InsertPedidoDTO.class);
        ProdutoDTO produto = mock(ProdutoDTO.class);
        FindByProdutoIdResponseDTO response = mock(FindByProdutoIdResponseDTO.class);

        when(dto.listaProdutos()).thenReturn(Arrays.asList(produto));
        when(produto.getIdProduto()).thenReturn(1L);
        when(produtoGateway.findById(1L)).thenReturn(response);
        when(response.HttpStatusCode()).thenReturn(HttpStatus.SC_NOT_FOUND);

        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> {
            criarPedidoUseCase.criarPedido(dto);
        });

        assertEquals("Produto com o identificador: 1 - Não está disponível", exception.getMessage());
    }

    @Test
    void testCriarPedido_ProdutoQuantidadeInsuficiente() {
        InsertPedidoDTO dto = mock(InsertPedidoDTO.class);
        ProdutoDTO produto = mock(ProdutoDTO.class);
        FindByProdutoIdResponseDTO response = mock(FindByProdutoIdResponseDTO.class);
        FindByIdProdutoDTO produtoResponse = mock(FindByIdProdutoDTO.class);

        when(dto.listaProdutos()).thenReturn(Arrays.asList(produto));
        when(produto.getIdProduto()).thenReturn(1L);
        when(produtoGateway.findById(1L)).thenReturn(response);
        when(response.HttpStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(response.Produto()).thenReturn(produtoResponse);
        when(produto.getQuantidadeDesejada()).thenReturn(15L);
        when(produtoResponse.QuantidadeEstoque()).thenReturn(10L);

        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> {
            criarPedidoUseCase.criarPedido(dto);
        });

        assertEquals("Produto com o identificador: 1 - Não tem estoque o suficiente. Quantidade disponivel:10", exception.getMessage());
    }
}
