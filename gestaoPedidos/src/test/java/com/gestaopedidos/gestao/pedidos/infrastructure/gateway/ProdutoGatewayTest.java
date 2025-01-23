package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByIdProdutoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByProdutoIdResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class ProdutoGatewayTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ProdutoGateway produtoGateway;

    @Value("${api-produto-base-path}")
    private String urlProduto;

    @Test
    void testFindById() {
        long id = 1L;
        String url = urlProduto + "/" + id;
        FindByProdutoIdResponseDTO mockResponse = new FindByProdutoIdResponseDTO(
                new FindByIdProdutoDTO(1,"descTeste",new BigDecimal("2"),30),200,"Sucesso");

        when(restTemplate.getForObject(url, FindByProdutoIdResponseDTO.class)).thenReturn(mockResponse);

        FindByProdutoIdResponseDTO response = produtoGateway.findById(id);

        assertNotNull(response);
        assertEquals(mockResponse, response);
        verify(restTemplate, times(1)).getForObject(url, FindByProdutoIdResponseDTO.class);
    }
}
