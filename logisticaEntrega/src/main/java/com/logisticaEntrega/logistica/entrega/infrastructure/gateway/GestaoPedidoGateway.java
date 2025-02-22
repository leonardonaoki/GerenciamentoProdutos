package com.logisticaEntrega.logistica.entrega.infrastructure.gateway;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.logisticaEntrega.logistica.entrega.infrastructure.dto.PedidoDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.RequisicaoGestaoPedidoDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.ResponseCepDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.ResponseDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.ResponseEntregadorDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GestaoPedidoGateway implements IGestaoPedidoGateway{

	private final RestTemplate restTemplate;
    @Value("${api-gestaoPedido-base-path}")
    private String urlGestaoPedido;
    
    @Override
    public ResponseDTO atualizaLocalizacao(long id,RequisicaoGestaoPedidoDTO dto) {
        String url = urlGestaoPedido + "/" + id;
        return restTemplate.getForObject(url, ResponseDTO.class);
    }
    @Override
    public ResponseDTO concluiEntregaPedido(long id) {
        String url = urlGestaoPedido + "/" + id;
        return restTemplate.getForObject(url,ResponseDTO.class);
    }
    @Override
    public ResponseDTO atualizaEntregadorPedido(long id,long idEntregador) {
        String url = urlGestaoPedido + "/" + id + "/"+ idEntregador;
        return restTemplate.getForObject(url, ResponseDTO.class);
    }
    @Override
    public ResponseCepDTO buscaPedidoPorCep(String cep) {
        String url = urlGestaoPedido + "/" + cep;
        return restTemplate.getForObject(url, ResponseCepDTO.class);
    }
    @Override
    public ResponseEntregadorDTO buscaPedidoEmAberto(long idEntragador) {
        String url = urlGestaoPedido + "/" + idEntragador;
        return restTemplate.getForObject(url, ResponseEntregadorDTO.class);
    }
}
