package com.gestaopedidos.gestao.pedidos.app;

import org.springframework.stereotype.Service;

import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizaEntregadorPedidoUseCase {

    private final IPedidoGateway pedidoGateway;
    
    public void atualizaEngtregadorPedido(long idPedido, long idEntregador) throws SystemBaseHandleException {
    	pedidoGateway.atualizarEntregadorPedido(idPedido, idEntregador);
    }

}
