package com.gestaopedidos.gestao.pedidos.domain.dto.request;

import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
import com.gestaopedidos.gestao.pedidos.domain.validator.IValidaCep;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record UpdatePedidoDTO(
        StatusEnum status,
        @NotBlank
        @NotEmpty
        @IValidaCep
        String CEP,
        Double Latitude,
        Double Longitude
) {
}
