package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByClienteIdResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ClienteGateway implements IClienteGateway {

    private final RestTemplate restTemplate;
    @Value("${api-cliente-base-path}")
    private String urlCliente;
    @Override
    public FindByClienteIdResponseDTO findById(long id) {
        String url = urlCliente + "/" + id;
        return restTemplate.getForObject(url, FindByClienteIdResponseDTO.class);
    }
}
