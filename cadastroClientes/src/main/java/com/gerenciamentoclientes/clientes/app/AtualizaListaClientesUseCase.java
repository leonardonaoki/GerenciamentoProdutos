package com.gerenciamentoclientes.clientes.app;

import com.gerenciamentoclientes.clientes.domain.dto.consumer.AtualizacaoClientesDTO;
import com.gerenciamentoclientes.clientes.infrastructure.gateway.IClienteGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AtualizaListaClientesUseCase {

    private final IClienteGateway produtoGateway;

    public void atualizaListaProdutos(AtualizacaoClientesDTO dto){

        produtoGateway.atualizarListaClientes(dto);
    }
}
