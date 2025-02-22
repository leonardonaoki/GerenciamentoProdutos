package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.entity.InsertPedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.entity.UpdateLocalizacaoPedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.entity.UpdatePedidoDomain;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface IPedidoGateway {
    Page<PedidoDTO> listarPedidos(int offset, int limit);
    PedidoDTO listarPedidoPorId(long id) throws SystemBaseHandleException;
    PedidoDTO criarPedido(InsertPedidoDomain entity, BigDecimal valorTotal);
    void atualizarStatusPedidoPorId(long id, UpdatePedidoDomain pedido) throws SystemBaseHandleException;
	void atualizarEntregadorPedido(long id, long idEntregador) throws SystemBaseHandleException;
	void atualizarStatusEntregue(long id) throws SystemBaseHandleException;
	void atualizarLocalizacao(long id, UpdateLocalizacaoPedidoDomain domain) throws SystemBaseHandleException;
	List<PedidoDTO> buscaPedidosEmAbertos(long idEntregador);
	List<Long> buscaPorCep(String cep);
}
