package com.gestaopedidos.gestao.pedidos.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

    @JsonProperty("IdPedido")
    private long idPedido;

    @JsonProperty("IdCliente")
    private long idCliente;

    @JsonProperty("listaProdutos")
    private List<ProdutoDTO> listaProdutos;

    @JsonProperty("PrecoFinal")
    private BigDecimal precoFinal;

    @JsonProperty("Status")
    private String status;

    @JsonProperty("CEP")
    private String cep;

    @JsonProperty("Latitude")
    private Double latitude;

    @JsonProperty("Longitude")
    private Double longitude;
}