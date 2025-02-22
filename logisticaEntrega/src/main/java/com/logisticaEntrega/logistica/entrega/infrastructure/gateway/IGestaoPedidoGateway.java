package com.logisticaEntrega.logistica.entrega.infrastructure.gateway;

import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.RequisicaoGestaoPedidoDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.ResponseCepDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.ResponseDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.ResponseEntregadorDTO;

public interface IGestaoPedidoGateway {

	ResponseDTO concluiEntregaPedido(long id);

	ResponseDTO atualizaEntregadorPedido(long id, long idEntregador);

	ResponseDTO atualizaLocalizacao(long id, RequisicaoGestaoPedidoDTO dto);

	ResponseCepDTO buscaPedidoPorCep(String cep);

	ResponseEntregadorDTO buscaPedidoEmAberto(long idEntragador);

}
