package com.gestaopedidos.gestao.pedidos.domain.dto.request;

import com.gestaopedidos.gestao.pedidos.domain.validator.IValidaCep;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record InsertPedidoDTO(
    @PositiveOrZero
    long idCliente,
    @NotEmpty
    List<ProdutoDTO> listaProdutos,
    @NotEmpty
    @NotBlank
    @IValidaCep
    String CEP,
    Double latitude,
    Double longitude
){}

