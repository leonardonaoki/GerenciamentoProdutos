package com.gerenciamentoclientes.clientes.infrastructure.gateway;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.consumer.AtualizacaoClientesDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
import com.gerenciamentoclientes.clientes.domain.mapper.IClienteMapper;
import com.gerenciamentoclientes.clientes.infrastructure.entity.ClienteEntity;
import com.gerenciamentoclientes.clientes.infrastructure.repository.IClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public ClienteDTO criarCliente(InsertAndUpdateClienteDTO dto) {
        // Criando novo cliente a partir do DTO e mapeando para DTO de resposta
        ClienteEntity clienteSalvo = clienteRepository.save(clienteMapper.toEntity(dto));
        return clienteMapper.toDTO(clienteSalvo);
    }

    @Override
    public ClienteDTO atualizarClientePorId(long id, InsertAndUpdateClienteDTO dto) {
        // Atualizando cliente existente
        ClienteEntity clienteEncontrado = clienteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_MESSAGE + id));

        // Atualizando as informações do cliente com os dados do DTO
        clienteEncontrado.setNome(dto.nome());
        clienteEncontrado.setEmail(dto.email());

        // Salvando e retornando o cliente atualizado
        return clienteMapper.toDTO(clienteRepository.save(clienteEncontrado));
    }

    @Override
    public void atualizarListaClientes(AtualizacaoClientesDTO dto) {
        // Ação de atualização em lote
        boolean acaoAtualizar = dto.acao().toUpperCase().contains("ATUALIZAR");

        // Lista de IDs dos clientes
        List<Long> listaIds = dto.listaClientes().stream()
                .map(ClienteDTO::id) // Extraindo IDs dos clientes
                .toList();

        // Buscando as entidades dos clientes no repositório
        List<ClienteEntity> listaClientes = clienteRepository.findAllById(listaIds);

        // Atualizando informações dos clientes
        dto.listaClientes().forEach(clienteDTO ->
                listaClientes.stream()
                        .filter(clienteEntity -> clienteEntity.getId().equals(clienteDTO.id())) // Comparando IDs
                        .findFirst()
                        .ifPresent(clienteEntity -> {
                            // Atualizando o nome e email dos clientes, por exemplo
                            if (acaoAtualizar) {
                                clienteEntity.setNome(clienteDTO.nome());
                                clienteEntity.setEmail(clienteDTO.email());
                            }
                        })
        );

        // Salvando todos os clientes atualizados no repositório
        clienteRepository.saveAll(listaClientes);
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
