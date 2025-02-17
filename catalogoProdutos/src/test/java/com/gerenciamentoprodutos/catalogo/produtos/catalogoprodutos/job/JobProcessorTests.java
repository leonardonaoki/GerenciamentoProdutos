package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job;


import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.exception.SystemBaseHandleException;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JobProcessorTests {
    private ProdutosProcessor produtoProcessor;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produtoProcessor = new ProdutosProcessor();
    }

    @Test
    void deveOcorrerUmErroAoPassarPrecoMenorQueZero(){
        ProdutosEntity produtoTeste = new ProdutosEntity();
        produtoTeste.setPreco(new BigDecimal( -1));

        assertThrows(SystemBaseHandleException.class,() ->
            produtoProcessor.process(produtoTeste)
        );
    }
    @Test
    void deveOcorrerUmErroAoPassarQuantidadeEstoqueMenorQueZero(){
        ProdutosEntity produtoTeste = new ProdutosEntity();
        produtoTeste.setPreco(new BigDecimal(200));
        produtoTeste.setQuantidadeEstoque(-1);

        assertThrows(SystemBaseHandleException.class,() ->
                produtoProcessor.process(produtoTeste)
        );
    }
    @Test
    void deveRetornarUmItemValido() throws Exception{
        ProdutosEntity produtoTeste = new ProdutosEntity();
        produtoTeste.setPreco(new BigDecimal(200));
        produtoTeste.setQuantidadeEstoque(300);

        ProdutosEntity produto = produtoProcessor.process(produtoTeste);
        assertEquals(produtoTeste,produto);
    }
}
