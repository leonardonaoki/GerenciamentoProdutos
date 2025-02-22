package com.gestaopedidos.gestao.pedidos.infrastructure.repository;

import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.ItensPedidosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface IItensPedidoRepository extends JpaRepository<ItensPedidosEntity,Long>, JpaSpecificationExecutor<ItensPedidosEntity> {
    List<ItensPedidosEntity> findByIdPedido(long idPedido);
    
}
