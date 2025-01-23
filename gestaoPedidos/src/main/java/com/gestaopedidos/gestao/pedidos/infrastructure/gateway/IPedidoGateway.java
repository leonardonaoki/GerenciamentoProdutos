package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.UpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface IPedidoGateway {
    Page<PedidoDTO> listarPedidos(int offset, int limit);
    PedidoDTO listarPedidoPorId(long id) throws SystemBaseHandleException;
    PedidoDTO criarPedido(InsertPedidoDTO entity, BigDecimal valorTotal);
    void atualizarStatusPedidoPorId(long id, UpdatePedidoDTO pedido) throws SystemBaseHandleException;
}
