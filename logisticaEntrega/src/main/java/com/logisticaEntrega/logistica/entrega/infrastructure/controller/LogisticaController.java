package com.logisticaEntrega.logistica.entrega.infrastructure.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logisticaEntrega.logistica.entrega.app.AtualizaLocalizacaoPedidoUseCase;
import com.logisticaEntrega.logistica.entrega.app.AtualizaStatusEntreguePedidoUseCase;
import com.logisticaEntrega.logistica.entrega.app.BuscaRemessaPedidoUseCase;
import com.logisticaEntrega.logistica.entrega.app.CadastraEntregadorUseCase;
import com.logisticaEntrega.logistica.entrega.app.GerarRemessaEntregaUseCase;
import com.logisticaEntrega.logistica.entrega.domain.entity.InsertEntregadorDTO;
import com.logisticaEntrega.logistica.entrega.domain.mapper.IEntregadorMapper;
import com.logisticaEntrega.logistica.entrega.infrastructure.dto.PedidoDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.RequisicaoGestaoPedidoDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/logistica")
@RequiredArgsConstructor
public class LogisticaController implements IControllerDocumentationLogistica {

	private final AtualizaLocalizacaoPedidoUseCase atualizaLocalizacaoPedidoUseCase;
	private final AtualizaStatusEntreguePedidoUseCase atualizaStatusEntreguePedidoUseCase;
	private final BuscaRemessaPedidoUseCase buscaRemessaPedidoUseCase;
	private final CadastraEntregadorUseCase cadastraEntregadorUseCase;
	private final GerarRemessaEntregaUseCase gerarRemessaEntregaUseCase;

	private final IEntregadorMapper entregadorMapper;
	
	@PatchMapping("/{idPedido}/localizacao")
    @Override
	public void atualizaLocalizacaoPedido(@PathVariable(value = "idPedido", required = true) long idPedido, @Valid @RequestBody(required = true) RequisicaoGestaoPedidoDTO dto) {
		atualizaLocalizacaoPedidoUseCase.atualizaLocalizaPedido(idPedido, dto);
	}
	@PatchMapping("/{idPedido}")
    @Override
	public void atualizaStatusEntreguePedido(@PathVariable(value = "idPedido", required = true) long idPedido) {
		atualizaStatusEntreguePedidoUseCase.atualizaStatusEntrgue(idPedido);
	}
	@PatchMapping("/{idEntregador}")
    @Override
	public List<PedidoDTO> buscaRemessaPedido(@PathVariable(value = "idPedido", required = true) long idEntregador) {
		return buscaRemessaPedidoUseCase.buscaRemessa(idEntregador);
	}
	@PatchMapping("")
    @Override
	public void cadastraEntregador(@Valid @RequestBody(required = true) InsertEntregadorDTO dto) {
		cadastraEntregadorUseCase.inserirEntregador(dto);
	}
	@PatchMapping("/{idEntregador}/{cep}")
    @Override
	public void gerarRemessaEntrega(@PathVariable(value = "idPedido", required = true) long idEntregador,@PathVariable(value = "cep", required = true) String cep) {
		gerarRemessaEntregaUseCase.gerarRemessaEntrega(cep, idEntregador);
	}

}
