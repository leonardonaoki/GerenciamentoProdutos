package com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.service;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoProdutos.dto.ProdutoDTO;

import java.util.List;

public interface ProdutoService {
    List<ProdutoDTO> listarProdutos();
}
