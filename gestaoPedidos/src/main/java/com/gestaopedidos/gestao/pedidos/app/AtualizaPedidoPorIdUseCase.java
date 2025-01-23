package com.gestaopedidos.gestao.pedidos.app;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertAndUpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizaPedidoPorIdUseCase {
    private final IPedidoGateway pedidoGateway;

    public PedidoDTO atualizaPedidoPorId(long id, StatusEnum status) throws SystemBaseHandleException {
        return pedidoGateway.atualizarStatusPedidoPorId(id,status);
    }
}
