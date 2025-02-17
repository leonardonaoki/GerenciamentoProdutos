package com.gestaopedidos.gestao.pedidos.domain.entity;

import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UpdatePedidoDomain {
    private final StatusEnum status;
    private final String cep;
    private final Double latitude;
    private final Double longitude;
}
