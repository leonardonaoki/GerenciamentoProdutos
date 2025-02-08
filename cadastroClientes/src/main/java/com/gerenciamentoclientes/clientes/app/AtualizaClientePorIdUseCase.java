package com.gerenciamentoclientes.clientes.app;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.entity.ClienteDomain;
import com.gerenciamentoclientes.clientes.exception.SystemBaseHandleException;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.IClienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizaClientePorIdUseCase {

    private final IClienteGateway clienteGateway;

    public ClienteDTO atualizaClientePorId(long id, ClienteDomain domain) throws SystemBaseHandleException {
        return clienteGateway.atualizarClientePorId(id, domain);
    }
}