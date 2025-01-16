package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import org.springframework.data.domain.Page;

public record ListProdutosResponseDTO(
    Page<ProdutoDTO> Produtos
){}
