package com.gerenciamentoclientes.clientes.domain.mapper;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
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
    public ClienteEntity toEntity(InsertAndUpdateClienteDTO dto) {
        ClienteEntity entity = new ClienteEntity();
        entity.setNome(dto.nome());
        entity.setEmail(dto.email());
        return entity;
    }
}