package com.gestaopedidos.gestao.pedidos.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class ResponseDTOTest {

    @Test
    void testResponseDTOCreation() {
        ResponseDTO response = new ResponseDTO(200, "OK");
        assertEquals(200, response.HttpStatusCode());
        assertEquals("OK", response.Message());
    }

    @Test
    void testResponseDTOWithDifferentValues() {
        ResponseDTO response = new ResponseDTO(404, "Not Found");
        assertEquals(404, response.HttpStatusCode());
        assertEquals("Not Found", response.Message());
    }
}
