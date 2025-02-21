package com.gestaopedidos.gestao.pedidos.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UpdateLocalizacaoPedidoDomain {
    private final Double latitude;
    private final Double longitude;
}
