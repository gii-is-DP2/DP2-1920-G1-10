package org.springframework.samples.petclinic.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.MatingOfferService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link MatingOfferController}
 *
 * @author Luis
 */

@WebMvcTest(controllers=MatingOfferController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class MatingOfferControllerTests {

	private static final int TEST_MATINGOFFER_ID = 1;
	private static final int TEST_PET1_ID = 1;
	private static final int TEST_PET2_ID = 2;
	private static final int TEST_CITA_ID = 1;
	private static final int TEST_OWNER_ID = 1;

	
	@Autowired
	private MatingOfferController matingOfferController;
	
	@Autowired
	private PetController petController;
	
	@MockBean
	private CitaService citaService;
    
	@MockBean
	private OwnerService ownerService;
    

	@MockBean
	private MatingOfferService offerService;
    
	@MockBean
	private PetService petService;
    
        @MockBean
	private UserService userService;
        
        @MockBean
        private AuthoritiesService authoritiesService; 

	@Autowired
	private MockMvc mockMvc;

	private MatingOffer offer1;
	private Pet p1, p2;
	private Cita c1;	
	private Owner o1;
	private User u1;
	
	@BeforeEach
	void setup() {
		
		p1.setBirthDate(LocalDate.of(2017, 4, 23));
		p1.setGender(new Gender());
		p1.setId(TEST_PET1_ID);
		p1.setMatingOffers(null);
		p1.setName("Mushku");
		p1.setType(new PetType());
		
		p2.setBirthDate(LocalDate.of(2016, 4, 23));
		p2.setGender(new Gender());
		p2.setId(TEST_PET2_ID);
		p2.setMatingOffers(null);
		p2.setName("Mushka");
		p2.setType(new PetType());
		
		c1.setDate(LocalDate.of(2016, 4, 24));
		c1.setDateTime(LocalDate.of(2016, 4, 24));
		c1.setId(TEST_CITA_ID);
		c1.setPet1(p1);
		c1.setPet2(p2);
		c1.setPlace("Casa de Borja");
		c1.setStatus("Pendiente");
		
		offer1 = new MatingOffer();
		offer1.setId(TEST_MATINGOFFER_ID);
		offer1.setPet(p1);
		offer1.setDescription("Grande y manso");
		offer1.setDate(LocalDate.of(2020, 8, 26));
	
		u1.setEnabled(true);
		u1.setUsername("owner1");
		u1.setPassword("0wn3r");
		
		o1.setAddress("Longus");
		o1.setCity("S");
		o1.setId(TEST_OWNER_ID);
		o1.setFirstName("Amalio");
		o1.setLastName("Orteg");
		o1.setTelephone("666666666");
		o1.setUser(u1);
		
		given(this.offerService.findMatById(TEST_MATINGOFFER_ID)).willReturn(offer1);
		given(this.petService.findPetById(TEST_PET1_ID)).willReturn(p1);
		given(this.petService.findPetById(TEST_PET2_ID)).willReturn(p2);
		given(this.citaService.findCitaById(TEST_CITA_ID)).willReturn(c1);
		given(this.ownerService.findOwnerById(TEST_OWNER_ID)).willReturn(o1);

	}

	@WithMockUser(value = "spring")
        @Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/pets/{petId}/matingOffers/new")).andExpect(status().isOk()).andExpect(model().attributeExists("matingOffer"))
				.andExpect(view().name("matingOffers/createMatingOfferForm"));
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/pets/{petId}/matingOffers/new").param("name", "Mushku").param("description", "Grande y manso")
						.with(csrf())
							.param("date", "2020/01/09"))
				.andExpect(status().is3xxRedirection());
	}

	@WithMockUser(value = "spring")
        @Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/pets/{petId}/matingOffers/new")
							.with(csrf())
							.param("name", "Mushku").param("description", "Grande y manso")
							.with(csrf())
							.param("date", "2020/01/09"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("matingOffer"))
				.andExpect(model().attributeHasFieldErrors("matingOffer", "citas"))
				.andExpect(model().attributeHasFieldErrors("matingOffer", "date"))
				.andExpect(view().name("matingOffers/createMatingOfferForm"));
	}

//	@WithMockUser(value = "spring")
//        @Test
//	void testInitFindForm() throws Exception {
//		mockMvc.perform(get("/matingOffers/find")).andExpect(status().isOk()).andExpect(model().attributeExists("matingOffer"))
//				.andExpect(view().name("matingOffers/findMatingOffers"));
//	}
//
//	@WithMockUser(value = "spring")
//        @Test
//	void testProcessFindFormSuccess() throws Exception {
//		given(this.offerService.findMatingOfferById("")).willReturn(Lists.newArrayList(offer1, new MatingOffer()));
//
//		mockMvc.perform(get("/matingOffers")).andExpect(status().isOk()).andExpect(view().name("matingOffers/matingOffersList"));
//	}
//
//	@WithMockUser(value = "spring")
//        @Test
//	void testProcessFindFormByLastName() throws Exception {
//		given(this.offerService.findMatingOfferByLastName(offer1.getLastName())).willReturn(Lists.newArrayList(offer1));
//
//		mockMvc.perform(get("/matingOffers").param("lastName", "Franklin")).andExpect(status().is3xxRedirection())
//				.andExpect(view().name("redirect:/matingOffers/" + TEST_MATINGOFFER_ID));
//	}
//
//        @WithMockUser(value = "spring")
//	@Test
//	void testProcessFindFormNoMatingOffersFound() throws Exception {
//		mockMvc.perform(get("/matingOffers").param("lastName", "Unknown Surname")).andExpect(status().isOk())
//				.andExpect(model().attributeHasFieldErrors("matingOffer", "lastName"))
//				.andExpect(model().attributeHasFieldErrorCode("matingOffer", "lastName", "notFound"))
//				.andExpect(view().name("matingOffers/findMatingOffers"));
//	}
//
//        @WithMockUser(value = "spring")
//	@Test
//	void testInitUpdateMatingOfferForm() throws Exception {
//		mockMvc.perform(get("/matingOffers/{matingOfferId}/edit", TEST_MATINGOFFER_ID)).andExpect(status().isOk())
//				.andExpect(model().attributeExists("matingOffer"))
//				.andExpect(model().attribute("matingOffer", hasProperty("lastName", is("Franklin"))))
//				.andExpect(model().attribute("matingOffer", hasProperty("firstName", is("George"))))
//				.andExpect(model().attribute("matingOffer", hasProperty("address", is("110 W. Liberty St."))))
//				.andExpect(model().attribute("matingOffer", hasProperty("city", is("Madison"))))
//				.andExpect(model().attribute("matingOffer", hasProperty("telephone", is("6085551023"))))
//				.andExpect(view().name("matingOffers/createOrUpdateMatingOfferForm"));
//	}
//
//        @WithMockUser(value = "spring")
//	@Test
//	void testProcessUpdateMatingOfferFormSuccess() throws Exception {
//		mockMvc.perform(post("/matingOffers/{matingOfferId}/edit", TEST_MATINGOFFER_ID)
//							.with(csrf())
//							.param("firstName", "Joe")
//							.param("lastName", "Bloggs")
//							.param("address", "123 Caramel Street")
//							.param("city", "London")
//							.param("telephone", "01616291589"))
//				.andExpect(status().is3xxRedirection())
//				.andExpect(view().name("redirect:/matingOffers/{matingOfferId}"));
//	}
//
//        @WithMockUser(value = "spring")
//	@Test
//	void testProcessUpdateMatingOfferFormHasErrors() throws Exception {
//		mockMvc.perform(post("/matingOffers/{matingOfferId}/edit", TEST_MATINGOFFER_ID)
//							.with(csrf())
//							.param("firstName", "Joe")
//							.param("lastName", "Bloggs")
//							.param("city", "London"))
//				.andExpect(status().isOk())
//				.andExpect(model().attributeHasErrors("matingOffer"))
//				.andExpect(model().attributeHasFieldErrors("matingOffer", "address"))
//				.andExpect(model().attributeHasFieldErrors("matingOffer", "telephone"))
//				.andExpect(view().name("matingOffers/createOrUpdateMatingOfferForm"));
//	}
//
//        @WithMockUser(value = "spring")
//	@Test
//	void testShowMatingOffer() throws Exception {
//		mockMvc.perform(get("/matingOffers/{matingOfferId}", TEST_MATINGOFFER_ID)).andExpect(status().isOk())
//				.andExpect(model().attribute("matingOffer", hasProperty("lastName", is("Franklin"))))
//				.andExpect(model().attribute("matingOffer", hasProperty("firstName", is("George"))))
//				.andExpect(model().attribute("matingOffer", hasProperty("address", is("110 W. Liberty St."))))
//				.andExpect(model().attribute("matingOffer", hasProperty("city", is("Madison"))))
//				.andExpect(model().attribute("matingOffer", hasProperty("telephone", is("6085551023"))))
//				.andExpect(view().name("matingOffers/matingOfferDetails"));
//	}

}
