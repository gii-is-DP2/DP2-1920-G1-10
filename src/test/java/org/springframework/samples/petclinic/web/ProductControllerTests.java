package org.springframework.samples.petclinic.web;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = ProductController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class ProductControllerTests {

	private static final int TEST_PRODUCT_ID = 1;

	@MockBean
	private ProductService productService;

	@Autowired
	private MockMvc mockMvc;

	private Product product;

	@BeforeEach
	void setup() {

		product = new Product();
		product.setId(TEST_PRODUCT_ID);
		product.setName("Champu Para Perros");
		product.setDescription("Champu para perros esencia de aloe");
		product.setPrice(9.6);
		product.setStock(30);
		product.setUrlImage(
				"https://d22ysdvc6gwinl.cloudfront.net/4165-thickbox_default/champu-biotina-para-perros-menforsan.jpg");
		given(this.productService.findProductById(TEST_PRODUCT_ID)).willReturn(product);

	}

	@WithMockUser(username = "admin", authorities = {"admin"})
	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/products/new"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("product"))
				.andExpect(view().name("products/editProduct"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/products/save")
				.param("name", "Comida para perros")
				.param("description", "Nutrientes necesarios para tu mascota").with(csrf())
				.param("urlImage", "https://www.tupienso.com/image/data/satisfaction/satisfaction-adult-medium.jpg"))
				.andExpect(status().isOk())
				.andExpect(view().name("products/editProduct"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		mockMvc.perform(post("/products/save").with(csrf())
				.param("name", "product")
				.param("description", "just some errors"))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("product"))
				.andExpect(model().attributeHasFieldErrors("product", "urlImage"))
				.andExpect(model().attributeHasFieldErrors("product", "stock"))
				.andExpect(model().attributeHasFieldErrors("product", "price"))
				.andExpect(view().name("products/editProduct"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowProduct() throws Exception {
		mockMvc.perform(get("/products/{productId}", TEST_PRODUCT_ID))
				.andExpect(status().isOk())
				.andExpect(model().attribute("product", hasProperty("name", is("Champu Para Perros"))))
				.andExpect(model().attribute("product",
						hasProperty("description", is("Champu para perros esencia de aloe"))))
				.andExpect(model().attribute("product", hasProperty("price", is(9.6))))
				.andExpect(model().attribute("product", hasProperty("stock", is(30))))
				.andExpect(model().attribute("product", hasProperty("urlImage", is(
						"https://d22ysdvc6gwinl.cloudfront.net/4165-thickbox_default/champu-biotina-para-perros-menforsan.jpg"))))
				.andExpect(view().name("products/productDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowProductListHtml() throws Exception {
		mockMvc.perform(get("/products")).andExpect(status().isOk())
				.andExpect(model().attributeExists("products"))
				.andExpect(view().name("products/productList"));
	}

	@WithMockUser(username = "admin", authorities = {"admin"})
	@Test
	void testDeleteProductSuccess() throws Exception {
		mockMvc.perform(get("/products/delete/" + TEST_PRODUCT_ID)).andExpect(status().isOk())
				.andExpect(model().attributeExists("products"))
				.andExpect(view().name("products/productList"));
	}

}