package org.springframework.samples.petclinic.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


import lombok.Data;


@Entity
@Table(name = "matingoffers")
public class MatingOffer extends NamedEntity{
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	
	private String  description;
	

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "pet1", fetch = FetchType.EAGER)
	private Set<Cita> citas;

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Cita> getCitas() {
		return citas;
	}

	public void setCitas(Set<Cita> cita) {
		this.citas = citas;
	}
}