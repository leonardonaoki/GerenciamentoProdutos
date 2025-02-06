package com.gestaoclientes.clientes.domain.dto;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponseDTOTest {

    @Test
    void deveCriarResponseDTOCorretamente(){
        String mensagemTeste = "RespostaTeste";
        int httpStatusTeste = HttpStatus.OK.value();
        com.gerenciamentoclientes.clientes.domain.dto.ResponseDTO dto = new com.gerenciamentoclientes.clientes.domain.dto.ResponseDTO(httpStatusTeste,mensagemTeste);

        assertEquals(mensagemTeste,dto.Message());
    }
}
