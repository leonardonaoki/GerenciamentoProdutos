package com.gerenciamentoclientes.clientes.domain.dto.response;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;

public record ClienteAndMessagesResponseDTO(
        ClienteDTO Cliente,
        int HttpStatusCode,
        String Message
) {}