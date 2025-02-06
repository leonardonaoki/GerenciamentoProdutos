package com.gerenciamentoclientes.clientes.domain.dto.response;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import org.springframework.data.domain.Page;

public record ListaClientesResponseDTO(
        Page<ClienteDTO> clientes
) {}