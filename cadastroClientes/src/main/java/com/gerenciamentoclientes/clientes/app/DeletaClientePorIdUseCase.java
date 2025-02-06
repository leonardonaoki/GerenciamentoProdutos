package com.gerenciamentoclientes.clientes.app;

import com.gerenciamentoclientes.clientes.exception.SystemBaseHandleException;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.IClienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletaClientePorIdUseCase {

    private final IClienteGateway clienteGateway;

    public void deletaClientePorId(long id) throws SystemBaseHandleException {
        clienteGateway.deletarClientePorId(id);
    }
}
