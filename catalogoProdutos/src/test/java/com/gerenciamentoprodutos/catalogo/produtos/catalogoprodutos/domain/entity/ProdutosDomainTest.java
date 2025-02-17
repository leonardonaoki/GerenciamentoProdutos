package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutosDomainTest {

    @Test
    void testGettersAndSetters() {
        // Cria uma instância de ProdutosDomain
        ProdutosDomain produto = new ProdutosDomain();

        // Define os valores
        String descricao = "Produto Teste";
        BigDecimal preco = new BigDecimal("19.99");
        long quantidadeEstoque = 100;

        // Usa os setters para atribuir valores
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeEstoque(quantidadeEstoque);

        // Usa os getters para verificar se os valores foram atribuídos corretamente
        assertEquals(descricao, produto.getDescricao());
        assertEquals(preco, produto.getPreco());
        assertEquals(quantidadeEstoque, produto.getQuantidadeEstoque());
    }

    @Test
    void testProdutoWithBigDecimalPreco() {
        // Teste específico para validar BigDecimal
        ProdutosDomain produto = new ProdutosDomain();

        BigDecimal preco = new BigDecimal("99.99");
        produto.setPreco(preco);

        assertEquals(preco, produto.getPreco());
    }

    @Test
    void testProdutoComDescricao() {
        // Teste para validar a descrição do produto
        ProdutosDomain produto = new ProdutosDomain();

        String descricao = "Novo Produto";
        produto.setDescricao(descricao);

        assertEquals(descricao, produto.getDescricao());
    }

    @Test
    void testProdutoComQuantidadeEstoque() {
        // Teste para validar a quantidade de estoque
        ProdutosDomain produto = new ProdutosDomain();

        long quantidadeEstoque = 500;
        produto.setQuantidadeEstoque(quantidadeEstoque);

        assertEquals(quantidadeEstoque, produto.getQuantidadeEstoque());
    }
}
