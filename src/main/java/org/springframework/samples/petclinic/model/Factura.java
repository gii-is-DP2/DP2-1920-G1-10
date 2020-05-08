package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Factura extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product product;
	private Double precio;
	private Integer cantidad;
}
