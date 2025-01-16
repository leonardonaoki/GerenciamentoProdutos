package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletaProdutoPorIdUseCase {
    private final IProdutoGateway produtoJobGateway;

    public void deletaProdutoPorId(long id) throws SystemBaseHandleException {
        produtoJobGateway.deletarProdutoPorId(id);
    }
}