package com.gestaopedidos.gestao.pedidos.app;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertAndUpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarPedidoUseCase {
    private final IPedidoGateway pedidoGateway;

    public PedidoDTO criarPedido(InsertAndUpdatePedidoDTO dto){
        return pedidoGateway.criarPedido(dto);
    }
}
