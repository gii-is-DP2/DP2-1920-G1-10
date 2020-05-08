
package org.springframework.samples.petclinic.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test class for {@link ProductController}
 *
 * @author Colin But
 */

@WebMvcTest(controllers = BookingController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class BookingControllerTests {

	private static final int	TEST_PRODUCT_ID	= 1;

	@MockBean
	private ProductService		productService;

	@Autowired
	private MockMvc				mockMvc;

	private Product				product;


	@BeforeEach
	void setup() {

		this.product = new Product();
		this.product.setId(BookingControllerTests.TEST_PRODUCT_ID);
		this.product.setName("Champú Para Perros");
		this.product.setDescription("Champú para perros esencia de aloe");
		this.product.setPrice(9.6);
		this.product.setStock(30);
		this.product.setUrlImage("https://d22ysdvc6gwinl.cloudfront.net/4165-thickbox_default/champu-biotina-para-perros-menforsan.jpg");
		BDDMockito.given(this.productService.findProductById(BookingControllerTests.TEST_PRODUCT_ID)).willReturn(this.product);

	}

	@WithMockUser(value = "spring")
	@Test
	void testInitCreationForm() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/products/new")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("product"))
			.andExpect(MockMvcResultMatchers.view().name("products/editProduct"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/products/save").param("name", "test1").param("description", "test1").with(SecurityMockMvcRequestPostProcessors.csrf()).param("urlImage", "123 Caramel Street"))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testProcessCreationFormHasErrors() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.post("/products/save").with(SecurityMockMvcRequestPostProcessors.csrf()).param("name", "test1").param("description", "test1").param("urlImage", "test1")).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attributeHasErrors("product")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("product", "stock")).andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("product", "price"))
			.andExpect(MockMvcResultMatchers.view().name("products/editProduct"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowProduct() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/products/{productId}", BookingControllerTests.TEST_PRODUCT_ID)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("name", Matchers.is("Champú Para Perros"))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("description", Matchers.is("Champú para perros esencia de aloe"))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("price", Matchers.is(9.6)))).andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("stock", Matchers.is(30))))
			.andExpect(MockMvcResultMatchers.model().attribute("product", Matchers.hasProperty("urlImage", Matchers.is("https://d22ysdvc6gwinl.cloudfront.net/4165-thickbox_default/champu-biotina-para-perros-menforsan.jpg"))))
			.andExpect(MockMvcResultMatchers.view().name("products/productDetails"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testShowProductListHtml() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/products")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("products"))
			.andExpect(MockMvcResultMatchers.view().name("products/productList"));
	}

	@WithMockUser(value = "spring")
	@Test
	void testDeleteProductSuccess() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/products/delete/" + BookingControllerTests.TEST_PRODUCT_ID)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.model().attributeExists("products"))
			.andExpect(MockMvcResultMatchers.view().name("products/productList"));
	}

}
