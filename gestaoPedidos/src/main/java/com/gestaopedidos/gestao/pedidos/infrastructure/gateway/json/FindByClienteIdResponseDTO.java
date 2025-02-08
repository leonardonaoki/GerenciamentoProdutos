package com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json;

public record FindByClienteIdResponseDTO(
    FindByIdClienteDTO Cliente,
    int HttpStatusCode,
    String Message
){}
