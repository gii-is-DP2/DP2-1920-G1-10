package org.springframework.samples.petclinic.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.springdatajpa.ProductRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductRepositoryTest {

	@Autowired
	ProductRepository productRepository;

	@Test
	void shouldFindProductId() {
		int id = 1;
		Product product = this.productRepository.findById(id);
		assertEquals("Champu Para Perros", product.getName());
	}

	@Test
	void shouldNotFindProductId() {
		int id = 511;
		Product product = this.productRepository.findById(id);
		assertEquals(product, null);
	}

}
