package com.gestaopedidos.gestao.pedidos.domain.entity;

import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IClienteGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IProdutoGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByClienteIdResponseDTO;
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
    private String cep;
    private Double latitude;
    private Double longitude;

    private final IProdutoGateway produtoGateway;
    private final IClienteGateway clienteGateway;

    public BigDecimal getValorTotalProdutos() throws SystemBaseHandleException {
        BigDecimal valorTotal = new BigDecimal(0);
        for(ProdutoDTO produto : this.listaProdutos){
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

    public void verificaClienteExiste() throws SystemBaseHandleException {
        FindByClienteIdResponseDTO response = clienteGateway.findById(this.idCliente);
        if(response.HttpStatusCode() != HttpStatus.SC_OK)
            throw new SystemBaseHandleException("Cliente com o id " + idCliente + " não encontrado");
    }
}
