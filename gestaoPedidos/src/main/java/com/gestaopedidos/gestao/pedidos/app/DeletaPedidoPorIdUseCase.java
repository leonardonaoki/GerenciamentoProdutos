package com.gestaopedidos.gestao.pedidos.app;

import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletaPedidoPorIdUseCase {
    private final IPedidoGateway pedidoGateway;

    public void deletaPedidoPorId(long id) throws SystemBaseHandleException {
        pedidoGateway.deletarPedidoPorId(id);
    }
}
