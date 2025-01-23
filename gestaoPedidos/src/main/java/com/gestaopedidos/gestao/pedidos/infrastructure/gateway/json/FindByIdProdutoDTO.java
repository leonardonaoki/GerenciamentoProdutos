package com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json;

import java.math.BigDecimal;

public record FindByIdProdutoDTO(
    long Id,
    String Descricao,
    BigDecimal Preco,
    long QuantidadeEstoque
){}
