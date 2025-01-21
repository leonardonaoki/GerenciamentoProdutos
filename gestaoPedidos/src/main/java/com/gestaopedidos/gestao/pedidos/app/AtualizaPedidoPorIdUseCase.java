package com.gestaopedidos.gestao.pedidos.app;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertAndUpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizaPedidoPorIdUseCase {
    private final IPedidoGateway pedidoGateway;

    public PedidoDTO atualizaPedidoPorId(long id, InsertAndUpdatePedidoDTO dto) throws SystemBaseHandleException {
        return pedidoGateway.atualizarPedidoPorId(id,dto);
    }
}
