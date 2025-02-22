package com.gestaopedidos.gestao.pedidos.infrastructure.repository;

import com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa.PedidosEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IPedidoRepository extends JpaRepository<PedidosEntity,Long>, JpaSpecificationExecutor<PedidosEntity> {
	List<PedidosEntity> findByCep(String cep);
	List<PedidosEntity> findByIdEntregador(long idEntregador);
}
