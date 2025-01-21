package com.gestaopedidos.gestao.pedidos.app;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarPedidoPorIdUseCase {
    private final IPedidoGateway pedidoGateway;

    public PedidoDTO listarPedidoPorId(int id) throws SystemBaseHandleException {
        return pedidoGateway.listarPedidoPorId(id);
    }
}
