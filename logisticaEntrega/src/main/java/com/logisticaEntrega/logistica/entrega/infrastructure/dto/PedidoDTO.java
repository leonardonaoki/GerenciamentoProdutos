package com.logisticaEntrega.logistica.entrega.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDTO {

    @JsonProperty("IdPedido")
    private long idPedido;  

    @JsonProperty("Status")
    private String status;

    @JsonProperty("CEP")
    private String cep;
}