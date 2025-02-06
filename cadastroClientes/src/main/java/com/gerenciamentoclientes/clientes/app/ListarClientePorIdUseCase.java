package com.gerenciamentoclientes.clientes.app;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.exception.SystemBaseHandleException;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.IClienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarClientePorIdUseCase {

    private final IClienteGateway clienteGateway;

    public ClienteDTO listarClientePorId(long id) throws SystemBaseHandleException {
        return clienteGateway.listarClientePorId(id);
    }
}