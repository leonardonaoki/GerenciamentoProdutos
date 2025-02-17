package com.gerenciamentoclientes.clientes.domain.mapper;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
import com.gerenciamentoclientes.clientes.domain.entity.ClienteDomain;
import com.gerenciamentoclientes.clientes.infrastructure.entity.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper implements IClienteMapper {

    @Override
    public ClienteDTO toDTO(ClienteEntity cliente) {
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail()
        );
    }

    @Override
    public ClienteDomain toDomain(InsertAndUpdateClienteDTO dto) {
        ClienteDomain domain = new ClienteDomain();
        domain.setEmail(dto.email());
        domain.setNome(dto.nome());
        return domain;
    }

    @Override
    public ClienteEntity toEntity(ClienteDomain domain) {
        ClienteEntity entity = new ClienteEntity();
        entity.setNome(domain.getNome());
        entity.setEmail(domain.getEmail());
        return entity;
    }
}