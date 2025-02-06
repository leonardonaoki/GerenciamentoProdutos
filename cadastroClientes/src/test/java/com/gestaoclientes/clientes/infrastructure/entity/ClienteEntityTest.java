package com.gestaoclientes.clientes.infrastructure.entity;

import com.gerenciamentoclientes.clientes.infrastructure.entity.ClienteEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClienteEntityTest {
    @Test
    void deveCriarESetarAsPropriedadesCorretamente() {
        // Dados para o teste
        long idTeste = 1;
        String nomeTeste = "Nome Teste";
        String emailTeste = "email@teste.com";

        // Criando o objeto ClienteEntity
        ClienteEntity cliente = new ClienteEntity();
        cliente.setId(idTeste);
        cliente.setNome(nomeTeste);
        cliente.setEmail(emailTeste);

        // Verificando se as propriedades foram corretamente definidas
        assertEquals(idTeste, cliente.getId());
        assertEquals(nomeTeste, cliente.getNome());
        assertEquals(emailTeste, cliente.getEmail());
    }
}
