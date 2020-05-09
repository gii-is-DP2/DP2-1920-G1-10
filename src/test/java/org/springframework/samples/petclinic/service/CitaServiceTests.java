package org.springframework.samples.petclinic.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.stereotype.Service;



@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CitaServiceTests {

	@Autowired
	protected CitaService citaService;

	@Test
	void shouldFindCitasWithCorrectPetId() {
		Collection<Cita> c1 = this.citaService.findCitaByPet(1);
		assertNotNull(c1);
		assertTrue(!c1.isEmpty());
	}
	@Test
	void shouldFindCitasWithIncorrectPetId() {
		Collection<Cita> c1 = this.citaService.findCitaByPet(5000000);
		
	assertTrue(c1.isEmpty());
	}
//	@Test
//	void shouldDeleteCitasId() {
//		List<Cita> c1 = (List<Cita>)this.citaService.findCitaByPet(1);
//		int tamañoActual = c1.size();
//		citaService.delete(c1.get(0));
//		assertEquals(c1.size(), tamañoActual-1);
//		
//		
//	
//	}

	
}
