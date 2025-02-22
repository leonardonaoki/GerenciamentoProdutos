package com.gestaopedidos.gestao.pedidos.app;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscarPedidoAbertoUseCase {

    private final IPedidoGateway pedidoGateway;

    public List<PedidoDTO> buscaPedidoAberto(long idEntregador){
    	return pedidoGateway.buscaPedidosEmAbertos(idEntregador);
    }
}
