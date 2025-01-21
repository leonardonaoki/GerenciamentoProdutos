package com.gestaopedidos.gestao.pedidos.infrastructure.gateway.specification;

import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;
import org.springframework.data.jpa.domain.Specification;

public class PedidosSpecification {
    public static Specification<PedidosEntity> filterTop10Pedidos;
}
