package com.gestaopedidos.gestao.pedidos.domain.entity;

import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePedidoDomain {
    public StatusEnum status;
    public String CEP;
    public Double Latitude;
    public Double Longitude;
}
