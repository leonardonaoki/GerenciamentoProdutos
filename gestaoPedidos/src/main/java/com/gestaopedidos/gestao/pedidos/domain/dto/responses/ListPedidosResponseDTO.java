package com.gestaopedidos.gestao.pedidos.domain.dto.responses;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import org.springframework.data.domain.Page;

public record ListPedidosResponseDTO(
    Page<PedidoDTO> Pedidos
){}
