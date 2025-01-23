package com.gestaopedidos.gestao.pedidos.domain.dto;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.domain.enums.AcaoEstoqueEnum;

import java.util.List;

public record AtualizaEstoqueRabbitDTO(
        List<ProdutoDTO> listaProdutos,
        AcaoEstoqueEnum acao
) {
}
