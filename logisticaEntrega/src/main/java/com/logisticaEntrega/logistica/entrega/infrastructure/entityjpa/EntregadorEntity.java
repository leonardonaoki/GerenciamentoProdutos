package com.logisticaEntrega.logistica.entrega.infrastructure.entityjpa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "entregador")
@Entity 
@Getter
@Setter
public class EntregadorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idEntregador;
	private String nomeEntregador;
	private String docEntregador;
}
