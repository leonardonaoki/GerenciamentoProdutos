package com.gerenciamentoclientes.clientes.domain.dto;

public record ResponseDTO(
        int HttpStatusCode,
        String Message
){}