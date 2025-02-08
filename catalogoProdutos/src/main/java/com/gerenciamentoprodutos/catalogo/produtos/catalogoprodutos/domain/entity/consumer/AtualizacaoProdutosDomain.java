package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.consumer;

import java.util.List;

public record AtualizacaoProdutosDomain(
        List<ListaProdutosDomain> listaProdutos,
        String acao
) {

}
