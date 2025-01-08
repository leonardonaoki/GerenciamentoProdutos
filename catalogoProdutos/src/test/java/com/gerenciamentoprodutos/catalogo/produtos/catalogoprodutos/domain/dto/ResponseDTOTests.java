package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto;

import com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.ResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ResponseDTOTests {
    @Test
    void deveCriarResponseDTOCorretamente(){
        String mensagemTeste = "RespostaTeste";
        int httpStatusTeste = HttpStatus.OK.value();
        ResponseDTO dto = new ResponseDTO(httpStatusTeste,mensagemTeste);

        assertEquals(mensagemTeste,dto.Message());
    }
}
