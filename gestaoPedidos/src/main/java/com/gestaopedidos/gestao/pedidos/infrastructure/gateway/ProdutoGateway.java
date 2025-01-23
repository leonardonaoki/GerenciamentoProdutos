package com.gestaopedidos.gestao.pedidos.infrastructure.gateway;

import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByProdutoIdResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ProdutoGateway implements IProdutoGateway {

    private final RestTemplate restTemplate;
    @Value("${api-produto-base-path}")
    private static String urlProduto;
    @Override
    public FindByProdutoIdResponseDTO findById(long id) {
        String url = urlProduto + "/" + id;
        return restTemplate.getForObject(url, FindByProdutoIdResponseDTO.class);
    }
}
