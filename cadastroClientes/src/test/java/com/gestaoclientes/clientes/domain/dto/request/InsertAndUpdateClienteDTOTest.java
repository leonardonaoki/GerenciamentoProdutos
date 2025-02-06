package com.gestaoclientes.clientes.domain.dto.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InsertAndUpdateClienteDTOTest {

    @Test
    void deveCriarInsertAndUpdateClienteDTOCorretamente() {
        // Arrange
        String nomeTeste = "Cliente 1";
        String emailTeste = "cliente1@teste.com";

        com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO dto = new com.gerenciamentoclientes.clientes.domain.dto.request.InsertAndUpdateClienteDTO(
                nomeTeste,
                emailTeste);

        // Assert
        assertEquals(nomeTeste, dto.nome());
        assertEquals(emailTeste, dto.email());
    }
}