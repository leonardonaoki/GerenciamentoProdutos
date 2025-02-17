package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.app;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.consumer.AtualizacaoProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.entity.consumer.ListaProdutosDomain;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.gateway.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AtualizaListaProdutoUseCaseTest {

    @Mock
    private IProdutoGateway produtoGateway;

    @InjectMocks
    private AtualizaListaProdutoUseCase atualizaListaProdutoUseCase;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAtualizarListaProdutos() {
        // Dado: cria um ListaProdutosDomain
        ListaProdutosDomain produto = new ListaProdutosDomain(1L, 10L); // Exemplo com idProduto = 1L e quantidadeDesejada = 10L
        AtualizacaoProdutosDomain dto = new AtualizacaoProdutosDomain(
                List.of(produto), // Passa a lista com o produto
                "ACAO_EXEMPLO" // Exemplo de ação
        );

        // Quando
        atualizaListaProdutoUseCase.atualizaListaProdutos(dto);

        // Então
        verify(produtoGateway, times(1)).atualizarListaProdutos(dto);
    }
}

