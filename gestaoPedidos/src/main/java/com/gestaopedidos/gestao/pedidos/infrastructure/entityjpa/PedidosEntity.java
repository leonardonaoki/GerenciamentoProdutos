package com.gestaopedidos.gestao.pedidos.infrastructure.entityjpa;

import com.gestaopedidos.gestao.pedidos.domain.enums.StatusEnum;
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
    private String cep;
    private Double latitude;
    private Double longitude;
    private BigDecimal precoFinal;
    private long idEntregador;

    public PedidosEntity(){
        this.status = StatusEnum.CONFIRMADO.name();
    }
}