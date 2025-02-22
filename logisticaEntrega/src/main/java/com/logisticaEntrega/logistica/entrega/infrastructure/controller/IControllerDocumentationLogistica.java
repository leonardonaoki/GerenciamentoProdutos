package com.logisticaEntrega.logistica.entrega.infrastructure.controller;

import java.util.List;

import com.logisticaEntrega.logistica.entrega.domain.entity.InsertEntregadorDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.dto.PedidoDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.RequisicaoGestaoPedidoDTO;

import jakarta.validation.Valid;

public interface IControllerDocumentationLogistica {

	void atualizaLocalizacaoPedido(long idPedido, @Valid RequisicaoGestaoPedidoDTO dto);

	void atualizaStatusEntreguePedido(long idPedido);

	List<PedidoDTO> buscaRemessaPedido(long idEntregador);

	void gerarRemessaEntrega(long idEntregador, String cep);

	void cadastraEntregador(@Valid InsertEntregadorDTO dto);


}
