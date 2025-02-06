package com.gerenciamentoclientes.clientes.infrastructure.repository;

import com.gerenciamentoclientes.clientes.infrastructure.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClienteRepository extends JpaRepository<ClienteEntity,Long> {
}
