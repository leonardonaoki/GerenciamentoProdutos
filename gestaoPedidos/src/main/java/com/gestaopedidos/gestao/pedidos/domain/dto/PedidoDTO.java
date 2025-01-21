package com.gestaopedidos.gestao.pedidos.domain.dto;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PedidoDTO{
    public long IdPedido;
    public long IdCliente;
    public List<ProdutoDTO> listaProdutos;
    public BigDecimal PrecoFinal;
    public String Status;
}
