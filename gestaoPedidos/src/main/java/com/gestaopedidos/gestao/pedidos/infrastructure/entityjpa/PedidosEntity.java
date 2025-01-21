package com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "pedidos")
@Entity
@Getter
@Setter
public class PedidosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idPedido;
    private long idCliente;
    private String status;
    private BigDecimal precoFinal;
}