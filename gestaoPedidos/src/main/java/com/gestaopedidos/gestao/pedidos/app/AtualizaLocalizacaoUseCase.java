package com.gestaopedidos.gestao.pedidos.app;

import org.springframework.stereotype.Service;

import com.gestaopedidos.gestao.pedidos.domain.entity.UpdateLocalizacaoPedidoDomain;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtualizaLocalizacaoUseCase {

    private final IPedidoGateway pedidoGateway;
    
    public void atualizaLocalizacaoPedido(long idPedido,UpdateLocalizacaoPedidoDomain localizaoPedido) throws SystemBaseHandleException {
    	pedidoGateway.atualizarLocalizacao(idPedido, localizaoPedido);
    }

}
