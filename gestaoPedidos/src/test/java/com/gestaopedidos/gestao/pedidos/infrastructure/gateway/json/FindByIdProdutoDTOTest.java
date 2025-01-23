package com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class FindByIdProdutoDTOTest {

    @Test
    void testFindByIdProdutoDTOCreation() {
        long id = 1L;
        String descricao = "Produto Teste";
        BigDecimal preco = new BigDecimal("99.99");
        long quantidadeEstoque = 100L;

        FindByIdProdutoDTO produtoDTO = new FindByIdProdutoDTO(id, descricao, preco, quantidadeEstoque);

        assertEquals(id, produtoDTO.Id());
        assertEquals(descricao, produtoDTO.Descricao());
        assertEquals(preco, produtoDTO.Preco());
        assertEquals(quantidadeEstoque, produtoDTO.QuantidadeEstoque());
    }
}