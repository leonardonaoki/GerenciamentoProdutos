package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job.config;

public class ProdutoBatchCommands {
    public static String insertProdutoCommand = "INSERT INTO produtos (descricao,preco,quantidade_estoque) VALUES (:descricao, :preco, :quantidadeEstoque) ";
}
