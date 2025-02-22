package com.logisticaEntrega.logistica.entrega.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.logisticaEntrega.logistica.entrega.infrastructure.entityjpa.EntregadorEntity;

public interface IEntregadorRepository extends JpaRepository<EntregadorEntity,Long>, JpaSpecificationExecutor<EntregadorEntity> {

}
