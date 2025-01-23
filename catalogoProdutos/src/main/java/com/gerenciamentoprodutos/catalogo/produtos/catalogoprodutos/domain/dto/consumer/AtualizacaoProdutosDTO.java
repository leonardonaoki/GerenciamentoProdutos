package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.consumer;

import java.util.List;

public record AtualizacaoProdutosDTO (
        List<ListaProdutosDTO> listaProdutos,
        String acao
) {

}
