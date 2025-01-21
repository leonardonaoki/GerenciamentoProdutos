package com.gestaopedidos.gestao.pedidos.domain.dto.responses;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;

public record PedidosAndMessagesResponseDTO(
        PedidoDTO Produto,
        int HttpStatusCode,
        String Message
){}
