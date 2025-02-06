package com.gerenciamentoclientes.clientes.app;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.IClienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarClienteUseCase {

    private final IClienteGateway clienteGateway;

    public ClienteDTO criarCliente(InsertAndUpdateClienteDTO dto) {
        return clienteGateway.criarCliente(dto);
    }
}