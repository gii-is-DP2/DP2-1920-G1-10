package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Gender;
import org.springframework.samples.petclinic.model.MatingOffer;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.MatingOfferService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link MatingOfferController}
 *
 * @author Luis
 */

@WebMvcTest(controllers = MatingOfferController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class MatingOfferControllerTests {

	private static final int TEST_MATING_OFFER_ID = 1;

	@MockBean
	private MatingOfferService matingOfferService;

	@MockBean
	private PetService petService;
	
	@MockBean
	private OwnerService ownerService;
	
	@Autowired
	private MockMvc mockMvc;

	private MatingOffer matingOffer;
	
	private Pet p1;
	
	@BeforeEach
	void setup() {

		p1 = new Pet();
		p1.setId(15);
		p1.setGender(new Gender());
		p1.setName("Perricus");
		p1.setType(new PetType());
		p1.setBirthDate(LocalDate.of(2012, 06, 12));
		matingOffer = new MatingOffer();
		matingOffer.setId(TEST_MATING_OFFER_ID);
		matingOffer.setCitas(new HashSet<Cita>());
		matingOffer.setDescription("prueba");
		matingOffer.setName("Hola");
		matingOffer.setPet(p1);
		
	}
	
	@WithMockUser(username = "admin1", authorities = {"admin"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/matingOffers/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("matingOffer"))
				.andExpect(view().name("matingOffers/createMatingOfferForm"));
	}
	
	@WithMockUser(username = "prueba", password = "prueba", authorities = {"owner"})
	@Test
	void testProcessNewMatingOfferFormSuccess() throws Exception {
		mockMvc.perform(post("/matingOffers", TEST_MATING_OFFER_ID).with(csrf())
				.param("p1.id", "15")
				.param("description", "New Offer Success"))
		        .andExpect(status().isOk())
				.andExpect(view().name("/matingOffers"));
	}
}
