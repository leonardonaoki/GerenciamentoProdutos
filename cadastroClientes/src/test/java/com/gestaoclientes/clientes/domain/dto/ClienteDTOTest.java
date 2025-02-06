package com.gestaoclientes.clientes.domain.dto;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteDTOTest {

    @Test
    void deveCriarClienteDTOCorretamente() {
        // Arrange
        long idTeste = 1;
        String nomeTeste = "Cliente 1";
        String emailTeste = "cliente1@example.com";

        // Criando o DTO com os valores de teste
        ClienteDTO dto = new ClienteDTO(
                idTeste,
                nomeTeste,
                emailTeste
        );

        // Assert
        assertEquals(idTeste, dto.id());
        assertEquals(nomeTeste, dto.nome());
        assertEquals(emailTeste, dto.email());
    }
}