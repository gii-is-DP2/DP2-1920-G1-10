package org.springframework.samples.petclinic.service;

import org.h2.security.auth.AuthConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.repository.springdatajpa.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	@Transactional
	public void save(Product product) {
		productRepository.save(product);
	}

	@Transactional(readOnly = true)
	public Product findProductById(int id) throws DataAccessException {
		return productRepository.findById(id);
	}

	@Transactional
	public void delete(Product product) {
		if (AuthoritiesService.checkAdmin() != true) {
			throw new AuthConfigException("Debe ser administrador");
		}
		productRepository.delete(product);
	}

}