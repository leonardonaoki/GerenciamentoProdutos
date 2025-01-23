package com.gestaopedidos.gestao.pedidos.app;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertAndUpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.ProdutoDTO;
import com.gestaopedidos.gestao.pedidos.exception.SystemBaseHandleException;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IPedidoGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IProdutoGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.json.FindByProdutoIdResponseDTO;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CriarPedidoUseCase {
    private final IPedidoGateway pedidoGateway;
    private final IProdutoGateway produtoGateway;

    public PedidoDTO criarPedido(InsertAndUpdatePedidoDTO dto) throws SystemBaseHandleException {
        BigDecimal valorTotal = new BigDecimal(0);
        for(ProdutoDTO produto : dto.listaProdutos()){
            FindByProdutoIdResponseDTO response = produtoGateway.findById(produto.idProduto);
            if(response.HttpStatusCode() != HttpStatus.SC_OK)
                throw new SystemBaseHandleException("Produto com o identificador: " + produto.idProduto + " - Não está disponível");
            if(produto.quantidadeDesejada > response.Produto().QuantidadeEstoque())
                throw new SystemBaseHandleException("Produto com o identificador: " + produto.idProduto + " - Não tem estoque o suficiente. " +
                        "Quantidade disponivel:" + response.Produto().QuantidadeEstoque());
            valorTotal.add(response.Produto().Preco());
        }
        PedidoDTO pedido = pedidoGateway.criarPedido(dto,valorTotal);
        pedido.setPrecoFinal(valorTotal);
        return pedido;
    }
}
