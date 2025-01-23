package com.gestaopedidos.gestao.pedidos.rabbitmq;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.domain.enums.AcaoEstoqueEnum;

import java.util.List;

public interface IEstoque
{
    void atualizaEstoque(long idProduto, List<ProdutoDTO> listaProdutos, AcaoEstoqueEnum acao);
}
