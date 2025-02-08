package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.entity.InsertPedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.entity.UpdatePedidoDomain;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface IPedidoGateway {
    Page<PedidoDTO> listarPedidos(int offset, int limit);
    PedidoDTO listarPedidoPorId(long id) throws SystemBaseHandleException;
    PedidoDTO criarPedido(InsertPedidoDomain entity, BigDecimal valorTotal);
    void atualizarStatusPedidoPorId(long id, UpdatePedidoDomain pedido) throws SystemBaseHandleException;
}
