package com.gerenciamentoclientes.clientes.infrastructure.gateway;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.entity.ClienteDomain;
import com.gerenciamentoclientes.clientes.domain.mapper.IClienteMapper;
import com.gerenciamentoclientes.clientes.infrastructure.entity.ClienteEntity;
import com.gerenciamentoclientes.clientes.infrastructure.repository.IClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteGateway implements IClienteGateway {

    private static final String ERROR_MESSAGE = "Não foi possível identificar o cliente com o ID ";
    private final IClienteRepository clienteRepository;
    private final IClienteMapper clienteMapper;

    @Override
    public Page<ClienteDTO> listarClientes(int offset, int limit) {
        // Paginação
        return clienteRepository.findAll(PageRequest.of(offset, limit))
                .map(clienteMapper::toDTO); // Convertendo entidade para DTO
    }

    @Override
    public ClienteDTO listarClientePorId(long id) {
        // Busca por ID e mapeamento da entidade para DTO
        return clienteRepository.findById(id)
                .map(clienteMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE + id));
    }

    @Override
    public ClienteDTO criarCliente(ClienteDomain domain) {
        // Criando novo cliente a partir do DTO e mapeando para DTO de resposta
        ClienteEntity clienteSalvo = clienteRepository.save(clienteMapper.toEntity(domain));
        return clienteMapper.toDTO(clienteSalvo);
    }

    @Override
    public ClienteDTO atualizarClientePorId(long id, ClienteDomain domain) {
        // Atualizando cliente existente
        ClienteEntity clienteEncontrado = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE + id));

        // Atualizando as informações do cliente com os dados do DTO
        clienteEncontrado.setNome(domain.getNome());
        clienteEncontrado.setEmail(domain.getEmail());

        // Salvando e retornando o cliente atualizado
        return clienteMapper.toDTO(clienteRepository.save(clienteEncontrado));
    }

    @Override
    public void deletarClientePorId(long id) {
        // Deletando o cliente pelo ID
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(ERROR_MESSAGE + id);
        }
    }
}
