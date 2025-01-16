package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizaProdutoPorIdUseCase {
    private final IProdutoGateway produtoJobGateway;

    public ProdutoDTO atualizaProdutoPorId(long id, InsertAndUpdateProdutoDTO dto) throws SystemBaseHandleException {
        return produtoJobGateway.atualizarProdutoPorId(id,dto);
    }
}
