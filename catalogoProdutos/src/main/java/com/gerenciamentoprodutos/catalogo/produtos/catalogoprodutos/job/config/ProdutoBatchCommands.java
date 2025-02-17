package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job.config;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ProdutoBatchCommands {
    private String insertProdutoCommand = "INSERT INTO produtos (descricao,preco,quantidade_estoque) VALUES (:descricao, :preco, :quantidadeEstoque) ";
    public String getInsertCommand(){
        return this.insertProdutoCommand;
    }
}