package org.springframework.samples.petclinic.web.e2e;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
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
import org.springframework.samples.petclinic.PetclinicApplication;
import org.springframework.samples.petclinic.web.ProductController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link ProductController}
 *
 * @author feljimgon1
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ContextConfiguration(classes = PetclinicApplication.class)
class ProductControllerE2ETests {

	private static final int TEST_PRODUCT_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/products/new")).andExpect(status().isOk()).andExpect(model().attributeExists("product"))
				.andExpect(view().name("products/editProduct"));
	}

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/products/save").param("name", "Comida para perros")
				.param("description", "Nutrientes necesarios para tu mascota").with(csrf())
				.param("urlImage", "https://www.tupienso.com/image/data/satisfaction/satisfaction-adult-medium.jpg"))
				.andExpect(status().isOk()).andExpect(view().name("products/editProduct"));
	}

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(
				post("/products/save").with(csrf()).param("name", "product").param("description", "just some errors"))
				.andExpect(status().isOk()).andExpect(model().attributeHasErrors("product"))
				.andExpect(model().attributeHasFieldErrors("product", "urlImage"))
				.andExpect(model().attributeHasFieldErrors("product", "stock"))
				.andExpect(model().attributeHasFieldErrors("product", "price"))
				.andExpect(view().name("products/editProduct"));
	}

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testShowProduct() throws Exception {
		mockMvc.perform(get("/products/{productId}", TEST_PRODUCT_ID)).andExpect(status().isOk())
				.andExpect(model().attribute("product", hasProperty("name", is("Champu Para Perros"))))
				.andExpect(model().attribute("product",
						hasProperty("description", is("Champu para perros esencia de aloe"))))
				.andExpect(model().attribute("product", hasProperty("price", is(9.6))))
				.andExpect(model().attribute("product", hasProperty("stock", is(30))))
				.andExpect(model().attribute("product", hasProperty("urlImage", is(
						"https://d22ysdvc6gwinl.cloudfront.net/4165-thickbox_default/champu-biotina-para-perros-menforsan.jpg"))))
				.andExpect(view().name("products/productDetails"));
	}

	@WithMockUser(username = "admin1", authorities = { "admin" })
	@Test
	void testShowProductListHtml() throws Exception {
		mockMvc.perform(get("/products")).andExpect(status().isOk()).andExpect(model().attributeExists("products"))
				.andExpect(view().name("products/productList"));
	}

	@WithMockUser(username = "admin", authorities = { "admin" })
	@Test
	void testDeleteProductSuccess() throws Exception {
		mockMvc.perform(get("/products/delete/" + TEST_PRODUCT_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("products")).andExpect(view().name("products/productList"));
	}
}