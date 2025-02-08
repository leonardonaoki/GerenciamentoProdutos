package com.gestaopedidos.gestao.pedidos.app;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.entity.InsertPedidoDomain;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CriarPedidoUseCase {
    private final IPedidoGateway pedidoGateway;

    public PedidoDTO criarPedido(InsertPedidoDomain domain) throws SystemBaseHandleException {
        domain.verificaClienteExiste();
        BigDecimal valorTotal = domain.getValorTotalProdutos();
        return pedidoGateway.criarPedido(domain,valorTotal);
    }
}
