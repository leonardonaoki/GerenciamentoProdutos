package com.gestaopedidos.gestao.pedidos.app;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuscaPedidoPorCepUseCase {

    private final IPedidoGateway pedidoGateway;
    
    public List<Long> buscaPedidoPorCep(String cep) {
    	return pedidoGateway.buscaPorCep(cep);
    }

}
