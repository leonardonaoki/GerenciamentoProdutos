package com.gestaoclientes.clientes.domain.mapper;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO;
import com.gerenciamentoclientes.clientes.domain.entity.ClienteDomain;
import com.gerenciamentoclientes.clientes.domain.mapper.ClienteMapper;
import com.gerenciamentoclientes.clientes.domain.mapper.IClienteMapper;
import com.gerenciamentoclientes.clientes.infrastructure.entity.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteMapperTest {

    private IClienteMapper clienteMapper;

    @BeforeEach
    void setup() {
        clienteMapper = new ClienteMapper(); // Supondo que ClienteMapper seja uma implementação de IClienteMapper
    }

    @Test
    void deveTransformarCorretamenteParaDTO() {
        // Arrange
        long idTeste = 1;
        String nomeTeste = "Nome Cliente";
        String emailTeste = "cliente@example.com";

        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(idTeste);
        cliente.setNome(nomeTeste);
        cliente.setEmail(emailTeste);

        // Act
        ClienteDTO dto = clienteMapper.toDTO(cliente);

        // Assert
        assertEquals(dto.id(), cliente.getId());
        assertEquals(dto.nome(), cliente.getNome());
        assertEquals(dto.email(), cliente.getEmail());
    }

    @Test
    void deveTransformarCorretamenteParaEntity() {
        // Arrange
        String nomeTeste = "Nome Cliente";
        String emailTeste = "cliente@example.com";

        ClienteDomain clienteDomain = new ClienteDomain();
        clienteDomain.setNome(nomeTeste);
        clienteDomain.setEmail(emailTeste);
        // Act
        ClienteEntity entity = clienteMapper.toEntity(clienteDomain);

        // Assert
        assertEquals(nomeTeste, entity.getNome());
        assertEquals(emailTeste, entity.getEmail());
    }
}