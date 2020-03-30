package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name = "citas")
public class Cita extends BaseEntity{
	@ManyToOne
	@JoinColumn(name = "pet1_id")
	private Pet pet1;
	@ManyToOne
	@JoinColumn(name = "pet2_id")
	private Pet pet2;
	@Column(name = "cita_date")        
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate dateTime;
	@Column(name = "status")
	private String status;
	@Column(name = "place")
	private String place;
	//--------------------------------------
	
	
	public LocalDate getDateTime() {
		return dateTime;
	}
	
	public void setDate(LocalDate dateTime) {
		this.dateTime = dateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public Pet getPet1() {
		return pet1;
	}
	public Pet getPet2() {
		return pet2;
	}

	public void setPet1(Pet pet1) {
		this.pet1 = pet1;
	}

	public void setPet2(Pet pet2) {
		this.pet2 = pet2;
	}

	public void setDateTime(LocalDate dateTime) {
		this.dateTime = dateTime;
	}
	

}
