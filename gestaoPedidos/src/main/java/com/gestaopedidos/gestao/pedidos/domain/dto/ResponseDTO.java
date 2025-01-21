package com.gestaopedidos.gestao.pedidos.domain.dto;

public record ResponseDTO(
        int HttpStatusCode,
        String Message
){}
