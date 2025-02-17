package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.responses.ListProdutosResponseDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarProdutosUseCase {
    private final IProdutoGateway produtoGateway;

    public ListProdutosResponseDTO listarProdutos(int offset, int limit){
        return new ListProdutosResponseDTO(produtoGateway.listarProdutos(offset,limit));
    }
}
