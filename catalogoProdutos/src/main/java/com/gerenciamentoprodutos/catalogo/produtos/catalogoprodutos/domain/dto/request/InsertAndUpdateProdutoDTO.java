package com.gerenciamentoprodutos.catalogo.produtos.catalogoprodutos.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record InsertAndUpdateProdutoDTO(
    @NotBlank(message = "A descrição não pode estar em branco.")
    String Descricao,
    @NotNull(message = "O preço é obrigatório.")
    @PositiveOrZero(message = "O preço deve ser maior ou igual a zero.")
    BigDecimal Preco,
    @PositiveOrZero(message = "A quantidade em estoque deve ser maior ou igual a zero.")
    long QuantidadeEstoque
){}
