package com.logisticaEntrega.logistica.entrega.infrastructure.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.RequisicaoGestaoPedidoDTO;
import com.logisticaEntrega.logistica.entrega.infrastructure.gateway.json.ResponseDTO;

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
    /*@Override
    public ResponseGestaoPedidosDTO buscaPedidoPorCep(long id) {
        String url = urlGestaoPedido + "/" + id;
        return restTemplate.getForObject(url, ResponseGestaoPedidosDTO.class);
    }*/
}
