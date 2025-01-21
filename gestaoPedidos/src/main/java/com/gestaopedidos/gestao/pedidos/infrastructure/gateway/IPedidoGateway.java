package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertAndUpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import org.springframework.data.domain.Page;

public interface IPedidoGateway {
    Page<PedidoDTO> listarPedidos(int offset, int limit);
    PedidoDTO listarPedidoPorId(long id) throws SystemBaseHandleException;
    PedidoDTO criarPedido(InsertAndUpdatePedidoDTO dto);
    PedidoDTO atualizarPedidoPorId(long id, InsertAndUpdatePedidoDTO dto) throws SystemBaseHandleException;
    void deletarPedidoPorId(long id) throws SystemBaseHandleException;
}
