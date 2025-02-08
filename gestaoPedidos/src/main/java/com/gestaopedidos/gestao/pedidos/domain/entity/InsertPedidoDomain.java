package com.gestaopedidos.gestao.pedidos.domain.entity;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IProdutoGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByProdutoIdResponseDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class InsertPedidoDomain {
    private long idCliente;
    private List<ProdutoDTO> listaProdutos;
    private String CEP;
    private Double latitude;
    private Double longitude;

    private final IProdutoGateway produtoGateway;

    public BigDecimal getValorTotalProdutos(List<ProdutoDTO> listaProdutos) throws SystemBaseHandleException {
        BigDecimal valorTotal = new BigDecimal(0);
        for(ProdutoDTO produto : listaProdutos){
            FindByProdutoIdResponseDTO response = produtoGateway.findById(produto.getIdProduto());
            if(response.HttpStatusCode() != HttpStatus.SC_OK)
                throw new SystemBaseHandleException("Produto com o identificador: " + produto.getIdProduto() + " - Não está disponível");
            if(produto.getQuantidadeDesejada() > response.Produto().QuantidadeEstoque())
                throw new SystemBaseHandleException("Produto com o identificador: " + produto.getIdProduto() + " - Não tem estoque o suficiente. " +
                        "Quantidade disponivel:" + response.Produto().QuantidadeEstoque());
            valorTotal = valorTotal.add(response.Produto().Preco());
        }
        return valorTotal;
    }
}
