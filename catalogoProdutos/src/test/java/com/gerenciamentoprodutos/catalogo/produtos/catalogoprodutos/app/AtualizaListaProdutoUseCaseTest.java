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

import static org.mockito.Mockito.*;

class AtualizaListaProdutoUseCaseTest {

    @Mock
    private IProdutoGateway produtoGateway;

    @InjectMocks
    private AtualizaListaProdutoUseCase atualizaListaProdutoUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAtualizaListaProdutos() {
        ListaProdutosDomain produto1 = new ListaProdutosDomain(1L, 10L);
        List<ListaProdutosDomain> listaProdutos1 = List.of(produto1);
        String acao = "BAIXAR_ESTOQUE";
        AtualizacaoProdutosDomain dto = new AtualizacaoProdutosDomain(listaProdutos1,acao);

        atualizaListaProdutoUseCase.atualizaListaProdutos(dto);

        verify(produtoGateway).atualizarListaProdutos(dto);
    }
}