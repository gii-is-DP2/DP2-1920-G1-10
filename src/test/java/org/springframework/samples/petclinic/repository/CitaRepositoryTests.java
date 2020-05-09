package org.springframework.samples.petclinic.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.repository.springdatajpa.CitaRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CitaRepositoryTests {

	@Autowired
	private CitaRepository citaRepository;

	@Test
	void shouldFindbyPetId() {
		int id = 1;
		List<Cita> c = this.citaRepository.findByPet(id);
		assertTrue(!c.isEmpty());
	}
	@Test
	void shouldFindById() {
		int id = 1;
		Cita c = this.citaRepository.findCitaById(id);
		
		assertEquals(c.getPet1().getName(),"Leo");
	}

}