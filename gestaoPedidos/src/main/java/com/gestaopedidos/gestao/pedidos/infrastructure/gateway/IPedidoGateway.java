package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertAndUpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface IPedidoGateway {
    Page<PedidoDTO> listarPedidos(int offset, int limit);
    PedidoDTO listarPedidoPorId(long id) throws SystemBaseHandleException;
    PedidoDTO criarPedido(InsertAndUpdatePedidoDTO entity, BigDecimal valorTotal);
    PedidoDTO atualizarStatusPedidoPorId(long id, StatusEnum status) throws SystemBaseHandleException;
}
