package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

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
@WebMvcTest(controllers = CitaController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
class CitaControllerTests {

	private static final int TEST_PET_ID = 1;
	private static final int TEST_MAT_ID = 1;

	@Autowired
	private CitaController citaController;

	@MockBean
	private PetService clinicService;

	@MockBean
	private CitaService citaService;

	@MockBean
	private MatingOfferService matingOfferService;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		PetType cat = new PetType();
		cat.setId(3);
		cat.setName("hamster");
	
		
		
		
		Pet h = new Pet();
		h.setGender(new Gender());
		
		h.setId(1);
		h.setName("prueba");
		h.setType(cat);
		MatingOffer mat = new MatingOffer();
		mat.setDate(LocalDate.now().minusDays(2));
		mat.setDescription("description");
		mat.setId(1);
		mat.setName("name");
		mat.setPet(h);
		

		given(this.clinicService.findPetById(TEST_PET_ID)).willReturn(h);
		given(this.matingOfferService.findMatById(TEST_MAT_ID)).willReturn(mat);

	}

	@WithMockUser(value = "spring")

	@Test
	void testInitNewVisitForm() throws Exception {
		mockMvc.perform(get("/pets/{petId}/matingOffers/{matingOfferId}/citas/new", TEST_PET_ID, TEST_MAT_ID))
				.andExpect(status().isOk()).andExpect(view().name("citas/editCitas"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessNewVisitFormSuccess() throws Exception {
		mockMvc.perform(post("/pets/{petId}/matingOffers/{matingOfferId}/citas/new", TEST_PET_ID, TEST_MAT_ID)
				.param("Pet2.id", "1")
				.param("status", "pending")
				.param("place", "hola")
				.param("dateTime", "2020/04/30"))
		        .andExpect(status().isOk())
				.andExpect(view().name("matingOffers/matingOffersList"));
	}

	@WithMockUser(value = "spring")

	@Test
	void testProcessNewVisitFormHasErrors() throws Exception {
		mockMvc.perform(post("/pets/{petId}/matingOffers/{matingOfferId}/citas/new", TEST_PET_ID, TEST_MAT_ID)
				.param("Pet2.id", "1")
				.param("status", "pending")
				.param("place", "hola")
				.param("dateTime", "2020/04/30"))
		        
	
				.andExpect(status().isOk()).andExpect(view().name("pet/createOrUpdateVisitForm"));
	}

	

}
