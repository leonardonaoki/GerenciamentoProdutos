package com.gestaopedidos.gestao.pedidos.domain.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.UpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.entity.InsertPedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.entity.UpdatePedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IClienteGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IProdutoGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class PedidoMapperTest {

    private PedidoMapper pedidoMapper;

    @BeforeEach
    public void setUp() {
        IProdutoGateway produtoGateway = mock(IProdutoGateway.class);
        IClienteGateway clienteGateway = mock(IClienteGateway.class);
        pedidoMapper = new PedidoMapper(produtoGateway,clienteGateway);
    }

    @Test
    void testToDTO() {
        PedidosEntity entity = new PedidosEntity();
        entity.setIdPedido(1);
        entity.setIdCliente(2);
        entity.setStatus("Pending");
        entity.setPrecoFinal(new BigDecimal(100.0));
        entity.setCep("12345-678");
        entity.setLatitude(12.345678);
        entity.setLongitude(98.765432);

        PedidoDTO dto = pedidoMapper.toDTO(entity);

        assertEquals(1, dto.getIdPedido());
        assertEquals(2, dto.getIdCliente());
        assertEquals("Pending", dto.getStatus());
        assertEquals(new BigDecimal(100.0), dto.getPrecoFinal());
        assertEquals("12345-678", dto.getCep());
        assertEquals(12.345678, dto.getLatitude());
        assertEquals(98.765432, dto.getLongitude());
    }

    @Test
    void testToEntity() {
        IProdutoGateway pedidoGateway = mock(IProdutoGateway.class);
        IClienteGateway clienteGateway = mock(IClienteGateway.class);
        InsertPedidoDomain domain = new InsertPedidoDomain(pedidoGateway,clienteGateway);
        domain.setIdCliente(2);
        domain.setListaProdutos(new ArrayList<>());
        domain.setCep("12345-678");
        domain.setLatitude(12.345678);
        domain.setLongitude( 98.765432);

        PedidosEntity entity = pedidoMapper.toEntity(domain);

        assertEquals(2, entity.getIdCliente());
        assertEquals("12345-678", entity.getCep());
        assertEquals(12.345678, entity.getLatitude());
        assertEquals(98.765432, entity.getLongitude());
    }
    @Test
    void testToInsertDomain() {
        ProdutoDTO produto1 = new ProdutoDTO();
        produto1.setIdProduto(1L);
        produto1.setQuantidadeDesejada(300L);
        InsertPedidoDTO pedidoDTO = new InsertPedidoDTO(1L, List.of(produto1),"12345-678",12.345678,98.765432);
        InsertPedidoDomain domain = pedidoMapper.toInsertDomain(pedidoDTO);

        assertEquals(1, domain.getIdCliente());
        assertEquals("12345-678", domain.getCep());
        assertEquals(12.345678, domain.getLatitude());
        assertEquals(98.765432, domain.getLongitude());
    }
    @Test
    void testToUpdateDomain() {
        ProdutoDTO produto1 = new ProdutoDTO();
        produto1.setIdProduto(1L);
        produto1.setQuantidadeDesejada(300L);

        UpdatePedidoDTO dto = new UpdatePedidoDTO(StatusEnum.EM_CURSO,"12345-678",12.345678,98.765432);
        UpdatePedidoDomain domain = pedidoMapper.toUpdateDomain(dto);

        assertEquals(StatusEnum.EM_CURSO, domain.getStatus());
        assertEquals("12345-678", domain.getCep());
        assertEquals(12.345678, domain.getLatitude());
        assertEquals(98.765432, domain.getLongitude());
    }
}
