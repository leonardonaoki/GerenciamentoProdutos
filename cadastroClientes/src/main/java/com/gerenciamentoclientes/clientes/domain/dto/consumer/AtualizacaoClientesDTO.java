package com.gerenciamentoclientes.clientes.domain.dto.consumer;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;

import java.util.List;

public record AtualizacaoClientesDTO(
        List<ClienteDTO> listaClientes,
        String acao
) {
}
