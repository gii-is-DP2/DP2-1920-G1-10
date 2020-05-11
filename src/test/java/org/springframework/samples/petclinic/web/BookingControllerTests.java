
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = BookingController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class BookingControllerTests {

	private static final int	TEST_PRODUCT_ID	= 1;

	@MockBean
	private BookingService		bookingService;

	@MockBean
	private ProductService		productService;

	@Autowired
	private MockMvc				mockMvc;

	private Product				product;

	private Booking				booking;


	@BeforeEach
	void setup() {

		product = new Product();
		product.setId(TEST_PRODUCT_ID);
		product.setName("Champu Para Perros");
		product.setDescription("Champu para perros esencia de aloe");
		product.setPrice(9.6);
		product.setStock(30);
		product.setUrlImage("https://d22ysdvc6gwinl.cloudfront.net/4165-thickbox_default/champu-biotina-para-perros-menforsan.jpg");

		booking = new Booking();
		booking.setId(TEST_PRODUCT_ID);
		booking.setNumProductos(4);
		booking.setProducto(product);
		booking.setFecha(LocalDate.parse("2013-01-01"));
		booking.setUser("prueba1");

		given(this.bookingService.findById(TEST_PRODUCT_ID)).willReturn(booking);

		given(this.productService.findProductById(TEST_PRODUCT_ID)).willReturn(product);
	}

	@WithMockUser(username = "prueba1")
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

	@WithMockUser(value = "prueba1")
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
