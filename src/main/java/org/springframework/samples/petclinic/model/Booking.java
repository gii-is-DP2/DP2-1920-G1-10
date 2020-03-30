
package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "bookings")
public class Booking extends NamedEntity {

	@OneToOne
	@JoinColumn(name = "product_id")
	private Product producto;

	@Column(name = "num_productos")
	@Min(1)
	private Integer numProductos;

	@Column(name = "user_id")
	private String user;

	@Column(name = "fecha")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate fecha;
}
