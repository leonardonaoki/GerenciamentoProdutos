package com.gestaopedidos.gestao.pedidos.app;

import com.gestaopedidos.gestao.pedidos.domain.dto.responses.ListPedidosResponseDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarPedidosUseCase {
    private final IPedidoGateway pedidoGateway;

    public ListPedidosResponseDTO listarPedidos(int offset, int limit){
        return new ListPedidosResponseDTO(pedidoGateway.listarPedidos(offset,limit));
    }
}
