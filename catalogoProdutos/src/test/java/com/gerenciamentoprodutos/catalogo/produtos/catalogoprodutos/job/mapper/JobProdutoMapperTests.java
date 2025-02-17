package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.job.mapper;


import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.infrastructure.entityjpa.ProdutosEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.batch.item.file.transform.FieldSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class JobProdutoMapperTests {
    @Mock
    private JobProdutoMapper produtoMapper;
    @Mock
    private FieldSet fieldSetMock;
    @BeforeEach
    void setUp() {
        produtoMapper = new JobProdutoMapper();
        fieldSetMock = mock(FieldSet.class);
    }

    @Test
    void deveTransformarCorretamenteParaEntity(){
        when(fieldSetMock.readBigDecimal("preco")).thenReturn(new java.math.BigDecimal("100.50"));
        when(fieldSetMock.readString("descricao")).thenReturn("Produto Exemplo");
        when(fieldSetMock.readLong("quantidadeEstoque")).thenReturn(10L);

        ProdutosEntity result = produtoMapper.mapFieldSet(fieldSetMock);

        assertNotNull(result);
        assertEquals(new java.math.BigDecimal("100.50"), result.getPreco());
        assertEquals("Produto Exemplo", result.getDescricao());
        assertEquals(10L, result.getQuantidadeEstoque());
    }
}
