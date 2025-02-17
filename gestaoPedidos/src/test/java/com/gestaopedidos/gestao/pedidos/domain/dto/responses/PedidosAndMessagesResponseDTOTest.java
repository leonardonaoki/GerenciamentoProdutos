package com.gestaopedidos.gestao.pedidos.domain.dto.responses;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PedidosAndMessagesResponseDTOTest {

    @Test
    void testCreatePedidosAndMessagesResponseDTO() {
        PedidoDTO produto = new PedidoDTO(); // Assuming a default constructor exists
        int httpStatusCode = 200;
        String message = "Success";

        PedidosAndMessagesResponseDTO responseDTO = new PedidosAndMessagesResponseDTO(produto, httpStatusCode, message);

        assertNotNull(responseDTO);
        assertEquals(produto, responseDTO.Pedido());
        assertEquals(httpStatusCode, responseDTO.HttpStatusCode());
        assertEquals(message, responseDTO.Message());
    }

    @Test
    void testGetProduto() {
        PedidoDTO pedido = new PedidoDTO(); // Assuming a default constructor exists
        PedidosAndMessagesResponseDTO responseDTO = new PedidosAndMessagesResponseDTO(pedido, 200, "Success");

        assertEquals(pedido, responseDTO.Pedido());
    }

    @Test
    void testGetHttpStatusCode() {
        int httpStatusCode = 200;
        PedidosAndMessagesResponseDTO responseDTO = new PedidosAndMessagesResponseDTO(new PedidoDTO(), httpStatusCode, "Success");

        assertEquals(httpStatusCode, responseDTO.HttpStatusCode());
    }

    @Test
    void testGetMessage() {
        String message = "Success";
        PedidosAndMessagesResponseDTO responseDTO = new PedidosAndMessagesResponseDTO(new PedidoDTO(), 200, message);

        assertEquals(message, responseDTO.Message());
    }
}
