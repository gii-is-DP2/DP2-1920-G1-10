
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
class BookingControllerE2ETests {

	private static final int	TEST_PRODUCT_ID	= 1;

	@Autowired
	private MockMvc				mockMvc;


	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/bookings/new/" + TEST_PRODUCT_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("booking")).andExpect(view().name("bookings/editBooking"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/bookings/save?id=" + TEST_PRODUCT_ID).param("numProductos", "4").with(csrf())).andExpect(status().isOk()).andExpect(view().name("bookings/bookingList"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/bookings/save?id=" + TEST_PRODUCT_ID).param("numProductos", "-1").with(csrf())).andExpect(status().isOk()).andExpect(view().name("exception"));
	}

	@WithMockUser(username = "admin1", authorities = {
		"admin"
	})
	@Test
	void testShowBookingListHtml() throws Exception {
		mockMvc.perform(get("/bookings")).andExpect(status().isOk()).andExpect(model().attributeExists("reservas")).andExpect(view().name("bookings/bookingList"));
	}

	@WithMockUser(username = "admin", authorities = {
		"admin"
	})
	@Test
	void testDeleteProductSuccess() throws Exception {
		mockMvc.perform(get("/bookings/delete/" + TEST_PRODUCT_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("message")).andExpect(view().name("bookings/bookingList"));
	}

}
