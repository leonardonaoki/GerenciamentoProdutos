package com.gestaoclientes.clientes.domain.dto.response;

import com.gerenciamentoclientes.clientes.domain.dto.ClienteDTO;
import com.gerenciamentoclientes.clientes.domain.dto.response.ListaClientesResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ListaClientesResponseDTOTest {

    @Test
    void deveCriarListClientesResponseDTOCorretamente() {
        // Arrange
        List<ClienteDTO> listaClientes = new ArrayList<>();
        ClienteDTO cliente = new ClienteDTO(1L, "Nome Cliente", "email@teste.com");
        listaClientes.add(cliente);

        // Criando uma Page de ClienteDTO
        Page<ClienteDTO> pageDTO = new PageImpl<>(listaClientes);

        // Criando o DTO de resposta com a página de clientes
        ListaClientesResponseDTO responseTeste = new ListaClientesResponseDTO(pageDTO);

        // Assert
        // Verificando se o DTO foi criado corretamente
        assertNotNull(responseTeste);

        // Verificando se o número de clientes no DTO corresponde ao tamanho da lista de clientes
        assertEquals(listaClientes.size(), responseTeste.clientes().getContent().size());
    }
}
