package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProdutoDTOTests {
    @Test
    void deveCriarProdutoDTOCorretamente(){
        long idTeste = 1;
        String descTeste = "Produto 1";
        BigDecimal precoTeste = new BigDecimal("21.05");
        long quantidadeEstoqueTeste = 300;
        ProdutoDTO dto = new ProdutoDTO(
                idTeste,
                descTeste,
                precoTeste,
                quantidadeEstoqueTeste);

        assertEquals(idTeste,dto.Id());
        assertEquals(descTeste,dto.Descricao());
        assertEquals(precoTeste,dto.Preco());
        assertEquals(quantidadeEstoqueTeste,dto.QuantidadeEstoque());
    }
}
