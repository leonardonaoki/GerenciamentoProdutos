package com.gerenciamentoclientes.clientes.infrastructure.gateway;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.consumer.AtualizacaoClientesDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
import com.gerenciamentoclientes.clientes.exception.SystemBaseHandleException;
import org.springframework.data.domain.Page;

public interface IClienteGateway {

    // Listar clientes com paginação
    Page<ClienteDTO> listarClientes(int offset, int limit);

    // Listar um cliente pelo ID
    ClienteDTO listarClientePorId(long id) throws SystemBaseHandleException;

    // Criar um novo cliente
    ClienteDTO criarCliente(InsertAndUpdateClienteDTO dto);

    // Atualizar cliente por ID
    ClienteDTO atualizarClientePorId(long id, InsertAndUpdateClienteDTO dto) throws SystemBaseHandleException;

    // Atualizar a lista de clientes (por exemplo, com um DTO contendo múltiplos clientes)
    void atualizarListaClientes(AtualizacaoClientesDTO dto);

    // Deletar cliente por ID
    void deletarClientePorId(long id) throws SystemBaseHandleException;
}
