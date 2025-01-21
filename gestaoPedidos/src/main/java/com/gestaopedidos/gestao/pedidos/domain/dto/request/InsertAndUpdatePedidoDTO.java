package com.gestaopedidos.gestao.pedidos.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

public record InsertAndUpdatePedidoDTO(
    @NotBlank(message = "O id do cliente não pode estar em branco.")
    long idCliente,
    @NotEmpty
    List<ProdutoDTO> listaProdutos
){}

