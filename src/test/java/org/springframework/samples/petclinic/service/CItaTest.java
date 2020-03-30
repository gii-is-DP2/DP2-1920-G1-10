package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CItaTest {
	 @Autowired
		protected CitaService citaService;
	
	@Test
	void shouldFindAllPetTypes() {
		Iterable<Cita> cit = this.citaService.findAll();
System.out.println(cit);
	}

}
