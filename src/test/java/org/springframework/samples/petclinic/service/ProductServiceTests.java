/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class ProductServiceTests {

	@Autowired
	protected ProductService productService;

	@Test
	void shouldFindProductWithCorrectId() {
		Product p1 = this.productService.findProductById(1);
		assertThat(p1.getName()).startsWith("Champú");
		assertThat(p1.getDescription().startsWith("Champú"));
		assertEquals(p1.getPrice(), 9.6);
		assertEquals(p1.getStock(), 30);
	}

	@Test
	@WithMockUser(username = "admin1", password = "4dm1n", authorities = "admin")
	void shouldDeleteProduct() {
		Collection<Product> products = (Collection<Product>) productService.findAll();
		int tamAntes = products.size() - 1;
		productService.delete(productService.findProductById(1));
		Collection<Product> productsAfter = (Collection<Product>) productService.findAll();
		int tamDespues = productsAfter.size() - 1;
		assertTrue(tamDespues != tamAntes);
	}
	
}
