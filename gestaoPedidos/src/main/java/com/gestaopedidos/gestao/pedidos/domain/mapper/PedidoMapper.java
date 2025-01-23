package com.gestaopedidos.gestao.pedidos.domain.mapper;

import com.gestaopedidos.gestao.pedidos.domain.dto.PedidoDTO;
import com.gestaopedidos.gestao.pedidos.domain.dto.request.InsertPedidoDTO;
import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper implements IPedidoMapper{

    @Override
    public PedidoDTO toDTO(PedidosEntity produto) {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setIdPedido(produto.getIdPedido());
        pedido.setIdCliente(produto.getIdCliente());
        pedido.setStatus(produto.getStatus());
        pedido.setPrecoFinal(produto.getPrecoFinal());
        pedido.setCep(produto.getCEP());
        pedido.setLatitude(produto.getLatitude());
        pedido.setLongitude(produto.getLongitude());
        return pedido;
    }

    @Override
    public PedidosEntity toEntity(InsertPedidoDTO dto) {
        PedidosEntity entity = new PedidosEntity();
        entity.setIdCliente(dto.idCliente());
        entity.setCEP(dto.CEP());
        entity.setLatitude(dto.latitude());
        entity.setLongitude(dto.longitude());
        return entity;
    }
}
