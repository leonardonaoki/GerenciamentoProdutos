package com.gerenciamentoclientes.clientes.domain.mapper;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
import com.gerenciamentoclientes.clientes.infrastructure.entity.ClienteEntity;

public interface IClienteMapper {

    // Método para converter a entidade ClienteEntity em DTO ClienteDTO
    ClienteDTO toDTO(ClienteEntity cliente);

    // Método para converter o DTO InsertAndUpdateClienteDTO em uma entidade ClienteEntity
    ClienteEntity toEntity(InsertAndUpdateClienteDTO dto);
}
