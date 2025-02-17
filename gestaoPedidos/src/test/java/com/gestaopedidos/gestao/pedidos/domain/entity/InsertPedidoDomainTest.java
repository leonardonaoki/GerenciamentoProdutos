package com.gestaopedidos.gestao.pedidos.domain.entity;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.ClienteGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IClienteGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IProdutoGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.ProdutoGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByClienteIdResponseDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByIdProdutoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByProdutoIdResponseDTO;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsertPedidoDomainTest {
    @Mock
    private IProdutoGateway produtoGateway;
    @Mock
    private IClienteGateway clienteGateway;
    @InjectMocks
    private InsertPedidoDomain insertPedidoDomain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetValorTotalProdutos_AllProductsAvailable() throws SystemBaseHandleException {
        ProdutoDTO produto1 = new ProdutoDTO();
        produto1.setIdProduto(1L);
        produto1.setQuantidadeDesejada(2L);
        ProdutoDTO produto2 = new ProdutoDTO();
        produto2.setIdProduto(2L);
        produto2.setQuantidadeDesejada(1L);
        insertPedidoDomain.setListaProdutos(Arrays.asList(produto1, produto2));

        FindByProdutoIdResponseDTO response1 = mock(FindByProdutoIdResponseDTO.class);
        FindByProdutoIdResponseDTO response2 = mock(FindByProdutoIdResponseDTO.class);
        FindByIdProdutoDTO produtoDTO = mock(FindByIdProdutoDTO.class);
        when(response1.HttpStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(response1.Produto()).thenReturn(produtoDTO);
        when(response1.Produto().QuantidadeEstoque()).thenReturn(10L);
        when(response1.Produto().Preco()).thenReturn(new BigDecimal("100.00"));

        when(response2.HttpStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(response2.Produto()).thenReturn(produtoDTO);
        when(response2.Produto().QuantidadeEstoque()).thenReturn(10L);
        when(response2.Produto().Preco()).thenReturn(new BigDecimal("200.00"));

        when(produtoGateway.findById(1)).thenReturn(response1);
        when(produtoGateway.findById(2)).thenReturn(response2);

        BigDecimal valorTotal = insertPedidoDomain.getValorTotalProdutos();
        assertEquals(new BigDecimal("400.00"), valorTotal);
    }

    @Test
    void testGetValorTotalProdutos_ProductNotAvailable() {
        ProdutoDTO produto = new ProdutoDTO();
        produto.setIdProduto(1L);
        produto.setQuantidadeDesejada(2L);
        insertPedidoDomain.setListaProdutos(Arrays.asList(produto));

        FindByProdutoIdResponseDTO response = mock(FindByProdutoIdResponseDTO.class);
        when(response.HttpStatusCode()).thenReturn(HttpStatus.SC_NOT_FOUND);
        when(produtoGateway.findById(1)).thenReturn(response);

        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> {
            insertPedidoDomain.getValorTotalProdutos();
        });

        assertEquals("Produto com o identificador: 1 - Não está disponível", exception.getMessage());
    }

    @Test
    void testGetValorTotalProdutos_ProductOutOfStock() {
        ProdutoDTO produto = new ProdutoDTO();
        produto.setIdProduto(1L);
        produto.setQuantidadeDesejada(2L);
        insertPedidoDomain.setListaProdutos(Arrays.asList(produto));

        FindByProdutoIdResponseDTO response = mock(FindByProdutoIdResponseDTO.class);
        FindByIdProdutoDTO produtoDTO = mock (FindByIdProdutoDTO.class);
        when(response.Produto()).thenReturn(produtoDTO);
        when(response.HttpStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(response.Produto().QuantidadeEstoque()).thenReturn(1L);
        when(produtoGateway.findById(1)).thenReturn(response);

        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> {
            insertPedidoDomain.getValorTotalProdutos();
        });

        assertEquals("Produto com o identificador: 1 - Não tem estoque o suficiente. Quantidade disponivel:1", exception.getMessage());
    }

    @Test
    void testVerificaClienteExiste_ClientExists() {
        insertPedidoDomain.setIdCliente(1);

        FindByClienteIdResponseDTO response = mock(FindByClienteIdResponseDTO.class);
        when(response.HttpStatusCode()).thenReturn(HttpStatus.SC_OK);
        when(clienteGateway.findById(1)).thenReturn(response);

        assertDoesNotThrow(() -> insertPedidoDomain.verificaClienteExiste());
    }

    @Test
    void testVerificaClienteExiste_ClientDoesNotExist() {
        insertPedidoDomain.setIdCliente(1);

        FindByClienteIdResponseDTO response = mock(FindByClienteIdResponseDTO.class);
        when(response.HttpStatusCode()).thenReturn(HttpStatus.SC_NOT_FOUND);
        when(clienteGateway.findById(1)).thenReturn(response);

        SystemBaseHandleException exception = assertThrows(SystemBaseHandleException.class, () -> {
            insertPedidoDomain.verificaClienteExiste();
        });

        assertEquals("Cliente com o id 1 não encontrado", exception.getMessage());
    }
    @Test
    void testInjections(){
        RestTemplate restTemplate = new RestTemplate();
        produtoGateway = new ProdutoGateway(restTemplate);
        clienteGateway = new ClienteGateway(restTemplate);

        insertPedidoDomain = new InsertPedidoDomain(produtoGateway,clienteGateway);
        insertPedidoDomain.getClienteGateway();
        insertPedidoDomain.getProdutoGateway();
        Assertions.assertThat(insertPedidoDomain).isNotNull();
    }
}
