package com.gestaoclientes.clientes.domain.dto.response;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.response.ClienteAndMessagesResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientAndMessagesResponseDTOTest {

    @Test
    void deveCriarClientAndMessagesResponseDTOCorretamente() {
        // Arrange
        String nomeTeste = "Cliente 1";
        String emailTeste = "cliente@teste.com";
        ClienteDTO clienteDTO = new ClienteDTO(1L, nomeTeste, emailTeste);

        // Criando o DTO de resposta com cliente, situação HTTP e mensagem
        ClienteAndMessagesResponseDTO responseDTO = new ClienteAndMessagesResponseDTO(
                clienteDTO,
                HttpStatus.OK.value(),
                "Sucesso"
        );

        // Assert
        assertEquals(nomeTeste, responseDTO.Cliente().nome());
        assertEquals(emailTeste, responseDTO.Cliente().email());
        assertEquals(HttpStatus.OK.value(), responseDTO.HttpStatusCode());
        assertEquals("Sucesso", responseDTO.Message());
    }
}