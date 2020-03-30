package org.springframework.samples.petclinic.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import lombok.Data;

@Entity
@Data
public class Product extends NamedEntity {

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	@NotBlank
	@URL
	private String urlImage;

	@NotNull
	@Min(0)
	private Integer stock;

	@NotNull
	@Min(0)
	private Double price;

}
