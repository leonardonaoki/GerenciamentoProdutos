package com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "itens_pedidos")
@Entity
@Getter
@Setter
public class ItensPedidosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long idPedido;
    private long idProduto;
    private String quantidade;
}