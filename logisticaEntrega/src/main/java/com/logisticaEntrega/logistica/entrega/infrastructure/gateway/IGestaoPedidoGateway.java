package com.logisticaEntrega.logistica.entrega.infrastructure.gateway;

import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.RequisicaoGestaoPedidoDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.ResponseDTO;

public interface IGestaoPedidoGateway {

	ResponseDTO concluiEntregaPedido(long id);

	ResponseDTO atualizaEntregadorPedido(long id, long idEntregador);

	ResponseDTO atualizaLocalizacao(long id, RequisicaoGestaoPedidoDTO dto);

}
