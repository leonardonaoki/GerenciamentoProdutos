package com.gestaopedidos.gestao.pedidos.domain.mapper;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.UpdatePedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.entity.InsertPedidoDomain;
import com.gestaopedidos.gestao.pedidos.domain.entity.UpdatePedidoDomain;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IClienteGateway;
import com.gestaopedidos.gestao.pedidos.infrastructure.gateway.IProdutoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoMapper implements IPedidoMapper{
    private final IProdutoGateway produtoGateway;
    private final IClienteGateway clienteGateway;
    @Override
    public PedidoDTO toDTO(PedidosEntity produto) {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setIdPedido(produto.getIdPedido());
        pedido.setIdCliente(produto.getIdCliente());
        pedido.setStatus(produto.getStatus());
        pedido.setPrecoFinal(produto.getPrecoFinal());
        pedido.setCep(produto.getCep());
        pedido.setLatitude(produto.getLatitude());
        pedido.setLongitude(produto.getLongitude());
        return pedido;
    }

    @Override
    public PedidosEntity toEntity(InsertPedidoDomain domain) {
        PedidosEntity entity = new PedidosEntity();
        entity.setIdCliente(domain.getIdCliente());
        entity.setCep(domain.getCep());
        entity.setLatitude(domain.getLatitude());
        entity.setLongitude(domain.getLongitude());
        return entity;
    }

    @Override
    public InsertPedidoDomain toInsertDomain(InsertPedidoDTO insertPedidoDTO) {
        InsertPedidoDomain domain = new InsertPedidoDomain(produtoGateway,clienteGateway);
        domain.setIdCliente(insertPedidoDTO.idCliente());
        domain.setListaProdutos(insertPedidoDTO.listaProdutos());
        domain.setCep(insertPedidoDTO.CEP());
        domain.setLatitude(insertPedidoDTO.latitude());
        domain.setLongitude(insertPedidoDTO.longitude());
        return domain;
    }

    @Override
    public UpdatePedidoDomain toUpdateDomain(UpdatePedidoDTO updatePedidoDTO) {
        UpdatePedidoDomain domain = new UpdatePedidoDomain();
        domain.setStatus(updatePedidoDTO.status());
        domain.setCEP(updatePedidoDTO.CEP());
        domain.setLatitude(updatePedidoDTO.Latitude());
        domain.setLongitude(updatePedidoDTO.Longitude());
        return domain;
    }
}
