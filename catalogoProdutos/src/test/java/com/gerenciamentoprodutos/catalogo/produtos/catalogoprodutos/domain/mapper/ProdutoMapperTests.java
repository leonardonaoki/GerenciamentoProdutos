package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.mapper;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ProdutoDTO;
import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ProdutoMapperTests {
    @Mock
    private IProdutoMapper produtoMapper;
    @BeforeEach
    void setup(){
        produtoMapper = new ProdutoMapper();
    }
    @Test
    void deveTransformarCorretamenteParaDTO(){
        long idTeste = 1;
        String descTeste = "Descrição Teste";
        BigDecimal precoTeste = new BigDecimal("21.76");
        long quantidadeEstoqueTeste = 300;

        ProdutosEntity produto = new ProdutosEntity();
        produto.setId(idTeste);
        produto.setDescricao(descTeste);
        produto.setPreco(precoTeste);
        produto.setQuantidadeEstoque(quantidadeEstoqueTeste);

        ProdutoDTO dto = produtoMapper.toDTO(produto);

        assertEquals(dto.Id(),produto.getId());
        assertEquals(dto.Descricao(),produto.getDescricao());
        assertEquals(dto.Preco(),produto.getPreco());
        assertEquals(dto.QuantidadeEstoque(),produto.getQuantidadeEstoque());
    }
}
