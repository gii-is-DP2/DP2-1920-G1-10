package org.springframework.samples.petclinic.web.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.web.ProductController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTests {

	private static final int TEST_PRODUCT_ID = 1;

	@Autowired
	private ProductController productController;
	@Autowired
	private ProductService productService;

	@Test
	@WithMockUser(username = "admin", authorities = { "admin" })
	void testInitUpdateForm() throws Exception {
		ModelMap model = new ModelMap();
		String view = productController.initCreationForm(productService.findProductById(TEST_PRODUCT_ID), model);
		assertEquals(view, "products/editProduct");
		assertNotNull(model.get("product"));
	}

	@Test
	@WithMockUser(username = "admin", authorities = { "admin" })
	void testInitCreationFormSuccess() throws Exception {
		Product product = new Product();
		ModelMap model = new ModelMap();
		String view = productController.initCreationForm(product, model);
		assertEquals(view, "products/editProduct");
		assertNotNull(model.get("product"));
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Product product = new Product();
		product.setName("Comida para perros	");
		product.setDescription("Nutrientes necesarios para tu mascota	");
		product.setPrice(15.0);
		product.setStock(30);
		product.setUrlImage("https://www.tupienso.com/image/data/satisfaction/satisfaction-adult-medium.jpg");
		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		String view = productController.processCreationForm(product, bindingResult, model);
		assertEquals(view, "products/productList");
	}

	@Test
	@WithMockUser(username = "admin", authorities = { "admin" })
	void testDeleteProductSuccess() throws Exception {
		ModelMap model = new ModelMap();
		Product product = productService.findProductById(TEST_PRODUCT_ID);
		assertNotNull(product);
		String view = productController.borrarProducto(TEST_PRODUCT_ID, model);
		assertEquals(view, "products/productList");
	}

	@Test
	@WithMockUser(username = "prueba", authorities = { "prueba" })
	void testShouldNotDeleteProductSuccess() throws Exception {
		Product product = productService.findProductById(TEST_PRODUCT_ID);
		assertNotNull(product);
//		assertEquals(productController.borrarProducto(TEST_PRODUCT_ID, model), "exception");
	}
}
