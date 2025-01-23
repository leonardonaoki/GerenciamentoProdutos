package com.gestaopedidos.gestao.pedidos.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.ItensPedidosEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ItensPedidoMapperTest {

    private ItensPedidoMapper itensPedidoMapper;

    @BeforeEach
    public void setUp() {
        itensPedidoMapper = new ItensPedidoMapper();
    }

    @Test
    void testToProdutoDTO() {
        // Arrange
        ItensPedidosEntity mockEntity = Mockito.mock(ItensPedidosEntity.class);
        Mockito.when(mockEntity.getIdProduto()).thenReturn(1L);
        Mockito.when(mockEntity.getQuantidade()).thenReturn(5l);

        // Act
        ProdutoDTO result = itensPedidoMapper.toProdutoDTO(mockEntity);

        // Assert
        assertEquals(1L, result.getIdProduto());
        assertEquals(5, result.getQuantidadeDesejada());
    }
}
