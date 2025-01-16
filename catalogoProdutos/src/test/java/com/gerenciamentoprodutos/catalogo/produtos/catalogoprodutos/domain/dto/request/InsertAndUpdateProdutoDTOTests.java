package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


class InsertAndUpdateProdutoDTOTests {
    @Test
    void deveCriarInsertAndUpdateDTOCorretamente(){
        String descTeste = "Produto 1";
        BigDecimal precoTeste = new BigDecimal("21.05");
        long quantidadeEstoqueTeste = 300;
        InsertAndUpdateProdutoDTO dto = new InsertAndUpdateProdutoDTO(
                descTeste,
                precoTeste,
                quantidadeEstoqueTeste);

        assertEquals(descTeste,dto.Descricao());
        assertEquals(precoTeste,dto.Preco());
        assertEquals(quantidadeEstoqueTeste,dto.QuantidadeEstoque());
    }
}
