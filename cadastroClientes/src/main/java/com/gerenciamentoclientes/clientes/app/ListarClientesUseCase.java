package com.gerenciamentoclientes.clientes.app;

import com.gerenciamentoclientes.clientes.domain.dto.response.ListaClientesResponseDTO;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.IClienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarClientesUseCase {

    private final IClienteGateway clienteGateway;

    public ListaClientesResponseDTO listarClientes(int offset, int limit) {
        return new ListaClientesResponseDTO(clienteGateway.listarClientes(offset, limit));
    }
}