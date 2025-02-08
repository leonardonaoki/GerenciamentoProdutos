package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.consumer.AtualizacaoProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizaListaProdutoUseCase {
    private final IProdutoGateway produtoGateway;

    public void atualizaListaProdutos(AtualizacaoProdutosDomain dto){
        produtoGateway.atualizarListaProdutos(dto);
    }
}
