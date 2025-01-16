package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarProdutoPorIdUseCase {
    private final IProdutoGateway produtoGateway;

    public ProdutoDTO listarProdutoPorId(int id) throws SystemBaseHandleException {
        return produtoGateway.listarProdutoPorId(id);
    }
}
