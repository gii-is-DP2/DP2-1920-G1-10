
package org.springframework.samples.petclinic.web.integration;

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
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Comment;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.CommentService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.web.CommentController;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentControllerIntegrationTests {

	private static final int	TEST_PRODUCT_ID	= 1;

	@MockBean
	private CommentService		commentService;

	@MockBean
	private ProductService		productService;

	@Autowired
	private MockMvc				mockMvc;

	private Product				product;

	private Comment	comment;


	

	@WithMockUser(username = "prueba", password = "prueba", authorities = { "owner" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/comments/new/" + TEST_PRODUCT_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("comment")).andExpect(view().name("comments/editComment"));
	}

	@WithMockUser(username = "prueba", password = "prueba", authorities = { "owner" })
	
	@Test
	void testCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/comments/save?id=" + TEST_PRODUCT_ID).with(csrf())).andExpect(status().isOk()).andExpect(view().name("comments/commentList"));
	}

	
	@WithMockUser(username = "prueba", password = "prueba", authorities = { "owner" })
	@Test
	void testShowBookingListHtml() throws Exception {
		mockMvc.perform(get("/comments")).andExpect(status().isOk()).andExpect(model().attributeExists("comments")).andExpect(view().name("comments/commentList"));
	}

	@WithMockUser(username = "admin", password = "4dm1n", authorities = { "admin" })
	@Test
	void testDeleteProductSuccess() throws Exception {
		mockMvc.perform(get("/comments/delete/" + TEST_PRODUCT_ID)).andExpect(status().isOk()).andExpect(model().attributeExists("message")).andExpect(view().name("comments/commentList"));
	}

}
