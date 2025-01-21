package com.gestaopedidos.gestao.pedidos.infrastructure.repository;

import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPedidoRepository extends JpaRepository<PedidosEntity,Long>, JpaSpecificationExecutor<PedidosEntity> {
}
