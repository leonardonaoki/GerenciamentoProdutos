package com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json;

public record FindByIdClienteDTO(
        long Id,
        String Nome,
        String Email
) {}
