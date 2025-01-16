package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProductAndMessagesResponseDTOTests {
    @Test
    void deveCriarProductAndMessagesResponseDTOCorretamente(){
        String descTeste = "Produto 1";
        BigDecimal precoTeste = new BigDecimal("21.05");
        long quantidadeEstoqueTeste = 300;
        ProdutoDTO produtoDTO = new ProdutoDTO(1,descTeste,precoTeste,quantidadeEstoqueTeste);

        ProductAndMessagesResponseDTO responseDTO = new ProductAndMessagesResponseDTO(
                produtoDTO,
                HttpStatus.OK.value(),
                "Sucesso"
                );

        assertEquals(descTeste,responseDTO.Produto().Descricao());
        assertEquals(precoTeste,responseDTO.Produto().Preco());
        assertEquals(quantidadeEstoqueTeste,responseDTO.Produto().QuantidadeEstoque());
        assertEquals(HttpStatus.OK.value(),responseDTO.HttpStatusCode());
        assertEquals("Sucesso",responseDTO.Message());
    }
}
