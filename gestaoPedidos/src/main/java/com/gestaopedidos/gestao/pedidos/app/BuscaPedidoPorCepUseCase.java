package com.gestaopedidos.gestao.pedidos.app;

import org.springframework.stereotype.Service;

import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscaPedidoPorCepUseCase {

    private final IPedidoGateway pedidoGateway;

}
