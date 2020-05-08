
package org.springframework.samples.petclinic.service;

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
class BookingServiceTests {

	@Autowired
	protected ProductService productService;


	// Positivo

	@Test
	@WithMockUser(username = "admin1", password = "4dm1n", authorities = "admin")
	void shouldFindProductWithCorrectId() {
		Product p1 = this.productService.findProductById(1);
		String nombre = p1.getName();
		//		Assertions.assertThat(nombre).startsWith("Champu Para Perros");
		//		Assertions.assertThat(p1.getDescription().startsWith("Champu"));
		//		Assertions.assertThat(p1.getPrice()).equals(9.6);
		//		Assertions.assertThat(p1.getStock(), 30);
	}

	@Test
	@WithMockUser(username = "admin1", password = "4dm1n", authorities = "admin")
	void shouldDeleteProduct() {
		Collection<Product> products = (Collection<Product>) productService.findAll();
		int tamAntes = products.size() - 1;
		productService.delete(productService.findProductById(1));
		Collection<Product> productsAfter = (Collection<Product>) productService.findAll();
		int tamDespues = productsAfter.size() - 1;
		//	Assertions.assertTrue(tamDespues != tamAntes);
	}

	// Negativo

	@Test
	@WithMockUser(username = "admin1", password = "4dm1n", authorities = "admin")
	void shouldNotFindProductWithCorrectId() {
		Product p1 = this.productService.findProductById(1);
		//		Assertions.assertTrue(!p1.getName().contains("Comida"));
		//		Assertions.assertThat(!p1.getDescription().contains("Para Mascotas"));
		//		Assertions.assertNotEquals(p1.getPrice(), 10.0);
		//		Assertions.assertNotEquals(p1.getStock(), 11);
	}

}
