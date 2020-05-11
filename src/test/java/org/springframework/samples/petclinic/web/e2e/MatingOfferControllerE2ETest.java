package org.springframework.samples.petclinic.web.e2e;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
  webEnvironment=SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class MatingOfferControllerE2ETest {

	private static final int TEST_MATING_OFFER_ID = 1;
	
	@Autowired
	private MockMvc mockMvc;	

	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
	void testInitCreationForm() throws Exception {
    mockMvc.perform(get("/matingOffers/new"))
	.andExpect(status().isOk())
	.andExpect(model().attributeExists("matingOffer"))
	.andExpect(view().name("matingOffers/createMatingOfferForm"));
	}

	@WithMockUser(username="admin1",authorities= {"admin"})
    @Test
    void testProcessNewMatingOfferFormSuccess() throws Exception {
		mockMvc.perform(get("/matingOffers", TEST_MATING_OFFER_ID).with(csrf())
				.param("p1.id", "15")
				.param("description", "New Offer Success"))
		        .andExpect(status().isOk())
				.andExpect(view().name("matingOffers/matingOfferList"));
	}
}
