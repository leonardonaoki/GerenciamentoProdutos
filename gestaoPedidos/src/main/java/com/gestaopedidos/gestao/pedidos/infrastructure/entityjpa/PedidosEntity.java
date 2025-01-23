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
    private String Status;
    private String CEP;
    private Double Latitude;
    private Double Longitude;
    private BigDecimal PrecoFinal;

    public PedidosEntity(){
        this.Status = StatusEnum.EM_CURSO.name();
    }
}