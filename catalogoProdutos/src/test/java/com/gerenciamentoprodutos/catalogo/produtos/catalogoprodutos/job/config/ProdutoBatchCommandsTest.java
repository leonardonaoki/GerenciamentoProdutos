package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProdutoBatchCommandsTest {

    @Test
    void testInsertProdutoCommand() {
        // Arrange
        String expectedCommand = "INSERT INTO produtos (descricao,preco,quantidade_estoque) VALUES (:descricao, :preco, :quantidadeEstoque) ";

        // Act & Assert
        assertEquals(expectedCommand, ProdutoBatchCommands.insertProdutoCommand,
                "O comando de inserção não está correto!");
    }
}
