package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.consumer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AtualizacaoProdutosDomainTest {

    @Test
    void deveCriarAtualizacaoProdutosDomainCorretamente() {
        // Dado
        ListaProdutosDomain produto1 = new ListaProdutosDomain(1L, 10L);
        ListaProdutosDomain produto2 = new ListaProdutosDomain(2L, 5L);
        List<ListaProdutosDomain> listaProdutos = List.of(produto1, produto2);
        String acao = "ADICIONAR";

        // Quando
        AtualizacaoProdutosDomain atualizacao = new AtualizacaoProdutosDomain(listaProdutos, acao);

        // Então
        assertNotNull(atualizacao);
        assertEquals(listaProdutos, atualizacao.listaProdutos());
        assertEquals(acao, atualizacao.acao());
    }

    @Test
    void deveTestarEqualsEHashCode() {
        // Dado
        ListaProdutosDomain produto1 = new ListaProdutosDomain(1L, 10L);
        ListaProdutosDomain produto2 = new ListaProdutosDomain(2L, 5L);
        List<ListaProdutosDomain> listaProdutos1 = List.of(produto1, produto2);
        List<ListaProdutosDomain> listaProdutos2 = List.of(produto1, produto2);
        String acao = "ADICIONAR";

        AtualizacaoProdutosDomain atualizacao1 = new AtualizacaoProdutosDomain(listaProdutos1, acao);
        AtualizacaoProdutosDomain atualizacao2 = new AtualizacaoProdutosDomain(listaProdutos2, acao);

        // Então
        assertEquals(atualizacao1, atualizacao2); // Verifica que ambos são iguais
        assertEquals(atualizacao1.hashCode(), atualizacao2.hashCode()); // Verifica que os hashCodes são iguais
    }

    @Test
    void deveTestarToString() {
        // Dado
        ListaProdutosDomain produto1 = new ListaProdutosDomain(1L, 10L);
        ListaProdutosDomain produto2 = new ListaProdutosDomain(2L, 5L);
        List<ListaProdutosDomain> listaProdutos = List.of(produto1, produto2);
        String acao = "ADICIONAR";

        // Quando
        AtualizacaoProdutosDomain atualizacao = new AtualizacaoProdutosDomain(listaProdutos, acao);

        // Então
        String expectedToString = "AtualizacaoProdutosDomain[listaProdutos=" + listaProdutos + ", acao=" + acao + "]";
        assertEquals(expectedToString, atualizacao.toString());
    }
}