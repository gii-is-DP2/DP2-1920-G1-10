package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductServiceTests {

	@Autowired
	protected ProductService productService;

	// Positivo

	@Test
	void shouldFindProductWithCorrectId() {
		Product p1 = this.productService.findProductById(3);
		assertNotNull(p1);
		assertThat(p1.getName().startsWith("Arnes Challenger Roca"));
	}

	@Test
	@WithMockUser(username = "admin1", password = "4dm1n", authorities = "admin")
	void shouldDeleteProduct() {
		Collection<Product> products = (Collection<Product>) productService.findAll();
		int tamAntes = products.size() - 1;
		productService.delete(productService.findProductById(2));
		Collection<Product> productsAfter = (Collection<Product>) productService.findAll();
		int tamDespues = productsAfter.size() - 1;
		assertTrue(tamDespues != tamAntes);
	}

	// Negativo

	@Test
	void shouldNotFindProductWithCorrectId() {
		Product p1 = this.productService.findProductById(500);
		assertNull(p1);
	}

}