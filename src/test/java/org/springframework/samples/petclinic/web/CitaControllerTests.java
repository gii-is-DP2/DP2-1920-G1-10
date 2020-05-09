package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Gender;
import org.springframework.samples.petclinic.model.MatingOffer;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.MatingOfferService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link VisitController}
 *
 * @author Colin But
 */
@WebMvcTest(controllers = CitaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class CitaControllerTests {

	private static final int TEST_PET_ID = 1;
	private static final int TEST_MAT_ID = 1;

	@Autowired
	private CitaController citaController;

	@MockBean
	private PetService petService;

	@MockBean
	private CitaService citaService;

	@MockBean
	private MatingOfferService matingOfferService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		User us = new User();
		us.setUsername("username");
		us.setPassword("contraseña");
		us.setEnabled(true);
		
		Owner o = new Owner();
		o.setUser(us);
		o.setAddress("calle");
		o.setCity("sevilla");
		o.setFirstName("pepe");
		o.setLastName("hoola");
		o.setTelephone("654987321");
		o.setId(99);
		
		
		PetType cat = new PetType();
		cat.setId(3);
		cat.setName("dog");
	
		
		
		Gender g = new Gender();
		g.setId(3);
		g.setName("dog");
		Pet h = new Pet();
		h.setGender(g);
		h.setBirthDate(LocalDate.now());
		h.setMatingOffers(new HashSet<MatingOffer>());
		h.setCitas_pet1(new HashSet<Cita>());
		h.setCitas_pet2(new HashSet<Cita>());
		h.setId(1);
		h.setName("prueba");
		h.setType(cat);
		h.setOwner(o);
	Cita c = new Cita();
	c.setId(99);
	c.setPet1(h);
	c.setPet2(h);
	c.setPlace("hola");
	c.setStatus("pending");
	c.setDate(LocalDate.now());

	
		MatingOffer mat = new MatingOffer();
		mat.setDate(LocalDate.now().minusDays(2));
		mat.setDescription("description");
		mat.setId(1);
		mat.setName("name");
		mat.setPet(h);
		mat.setCitas(new HashSet<Cita>());
		Set<Cita> cs = mat.getCitas();
		
		
		
		
		

		given(this.petService.findPetById(TEST_PET_ID)).willReturn(h);
		given(this.matingOfferService.findMatById(TEST_MAT_ID)).willReturn(mat);

	}
	@WithMockUser(username = "prueba", password = "prueba")
	@Test
	void testInitNewVisitForm() throws Exception {
		mockMvc.perform(get("/pets/{petId}/matingOffers/{matingOfferId}/citas/new", TEST_PET_ID, TEST_MAT_ID))
				.andExpect(status().isOk()).andExpect(view().name("citas/editCitas"));
	}


	@WithMockUser(username = "prueba", password = "prueba")
	@Test
	void testProcessNewVisitFormSuccess() throws Exception {
		mockMvc.perform(post("/pets/{petId}/matingOffers/{matingOfferId}/citas/new", TEST_PET_ID, TEST_MAT_ID).with(csrf())
				.param("Pet2.id", "15")
				.param("status", "pending")
				.param("place", "hola")
				.param("id", "22")
				.param("dateTime", "2020/04/30"))
		        .andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/matingOffers/"));
	}

	@WithMockUser(username = "prueba", password = "prueba")
	@Test
	void testProcessNewVisitFormHasErrors() throws Exception {
		mockMvc.perform(post("/pets/{petId}/matingOffers/{matingOfferId}/citas/new", TEST_PET_ID, TEST_MAT_ID).with(csrf())
				
				.param("status", "pending")
				.param("place", "hola")
				.param("dateTime", "2020/04/30"))		        
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/matingOffers/"));
	}

	

}
