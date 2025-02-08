package com.gestaopedidos.gestao.pedidos.app;

import com.gestaopedidos.gestao.pedidos.domain.entity.UpdatePedidoDomain;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizaPedidoPorIdUseCase {
    private final IPedidoGateway pedidoGateway;

    public void atualizaPedidoPorId(long id, UpdatePedidoDomain domain) throws SystemBaseHandleException {
        pedidoGateway.atualizarStatusPedidoPorId(id,domain);
    }
}
