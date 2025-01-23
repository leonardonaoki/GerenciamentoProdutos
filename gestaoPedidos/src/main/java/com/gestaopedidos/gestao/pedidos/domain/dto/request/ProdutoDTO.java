package com.gestaopedidos.gestao.pedidos.domain.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO{
    private long idProduto;
    private long quantidadeDesejada;
}
