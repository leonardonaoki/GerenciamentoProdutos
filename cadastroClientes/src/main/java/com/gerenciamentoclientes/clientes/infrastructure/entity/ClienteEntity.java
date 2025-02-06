package com.gerenciamentoclientes.clientes.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "clientes")
@Entity
@Data
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
}

