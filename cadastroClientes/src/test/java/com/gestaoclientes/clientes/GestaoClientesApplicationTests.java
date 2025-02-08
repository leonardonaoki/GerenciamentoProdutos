package com.gestaoclientes.clientes;

import com.gerenciamentoclientes.clientes.GestaoClientesApplication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class GestaoClientesApplicationTests {

    @Test
    void deveExecutarAplicacaoNormalmente(){
        // Tenta executar o metodo da aplicação do cliente sem lançar exceções
        assertDoesNotThrow(() -> GestaoClientesApplication.main(new String[]{}));
    }
}
