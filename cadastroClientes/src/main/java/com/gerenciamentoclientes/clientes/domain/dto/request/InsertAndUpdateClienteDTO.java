package com.gerenciamentoclientes.clientes.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record InsertAndUpdateClienteDTO(
        @NotBlank(message = "O nome não pode estar em branco.")
        String nome,

        @NotBlank(message = "O email não pode estar em branco.")
        @Email(message = "O email fornecido não é válido.")
        String email
) {
}
