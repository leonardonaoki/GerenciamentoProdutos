package com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class FindByProdutoIdResponseDTOTest {

    @Test
    void testFindByProdutoIdResponseDTOCreation() {
        FindByIdProdutoDTO produto = new FindByIdProdutoDTO(1,"DescTeste",new BigDecimal("2"),2);
        int httpStatusCode = 200;
        String message = "Success";

        FindByProdutoIdResponseDTO responseDTO = new FindByProdutoIdResponseDTO(produto, httpStatusCode, message);

        assertEquals(produto, responseDTO.Produto());
        assertEquals(httpStatusCode, responseDTO.HttpStatusCode());
        assertEquals(message, responseDTO.Message());
    }

    @Test
    void testGetProduto() {
        FindByIdProdutoDTO produto = new FindByIdProdutoDTO(1,"DescTeste",new BigDecimal("2"),2);
        FindByProdutoIdResponseDTO responseDTO = new FindByProdutoIdResponseDTO(produto, 200, "Success");

        assertEquals(produto, responseDTO.Produto());
    }

    @Test
    void testGetHttpStatusCode() {
        int httpStatusCode = 200;
        FindByProdutoIdResponseDTO responseDTO = new FindByProdutoIdResponseDTO(new FindByIdProdutoDTO(1,"DescTeste",new BigDecimal("2"),2), httpStatusCode, "Success");

        assertEquals(httpStatusCode, responseDTO.HttpStatusCode());
    }

    @Test
    void testGetMessage() {
        String message = "Success";
        FindByProdutoIdResponseDTO responseDTO = new FindByProdutoIdResponseDTO(new FindByIdProdutoDTO(1,"DescTeste",new BigDecimal("2"),2), 200, message);

        assertEquals(message, responseDTO.Message());
    }
}