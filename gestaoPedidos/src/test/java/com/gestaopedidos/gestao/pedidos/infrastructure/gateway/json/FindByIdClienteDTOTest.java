package com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FindByIdClienteDTOTest {
    @Test
    void testFindByIdProdutoDTOCreation() {
        long id = 1L;
        String nome = "Produto Teste";
        String email = "email@teste.com.br";

        FindByIdClienteDTO clienteDTO = new FindByIdClienteDTO(id, nome,email);

        assertEquals(id, clienteDTO.Id());
        assertEquals(nome, clienteDTO.Nome());
        assertEquals(email, clienteDTO.Email());
    }
}
