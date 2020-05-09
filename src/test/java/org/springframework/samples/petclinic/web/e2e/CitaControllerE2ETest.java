package org.springframework.samples.petclinic.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.web.VisitController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link VisitController}
 *
 * @author Colin But
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CitaControllerE2ETest {

	private static final int TEST_PET_ID = 1;
	private static final int TEST_MAT_ID = 1;

	

	@Autowired
	private MockMvc mockMvc;

	
	@WithMockUser(username = "prueba", password = "prueba", authorities = { "owner" })
	@Test
	void testInitNewVisitForm() throws Exception {
		mockMvc.perform(get("/pets/{petId}/matingOffers/{matingOfferId}/citas/new", TEST_PET_ID, TEST_MAT_ID))
				.andExpect(status().isOk()).andExpect(view().name("citas/editCitas"));
	}


	@WithMockUser(username = "prueba", password = "prueba" ,authorities = { "owner" })
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

	@WithMockUser(username = "prueba", password = "prueba" , authorities = { "owner" })
	@Test
	void testProcessNewVisitFormHasErrors() throws Exception {
		mockMvc.perform(post("/pets/{petId}/matingOffers/{matingOfferId}/citas/new", TEST_PET_ID, TEST_MAT_ID).with(csrf())
				
				.param("status", "pending")
				.param("place", "hola")
				.param("dateTime", "2020/04/30"))		        
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/matingOffers/"));
	}

	

}
