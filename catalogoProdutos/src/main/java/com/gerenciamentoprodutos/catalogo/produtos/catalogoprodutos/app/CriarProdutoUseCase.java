package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request.InsertAndUpdateProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarProdutoUseCase {
    private final IProdutoGateway produtoJobGateway;

    public ProdutoDTO criarProduto(InsertAndUpdateProdutoDTO dto){
        return produtoJobGateway.criarProduto(dto);
    }
}
