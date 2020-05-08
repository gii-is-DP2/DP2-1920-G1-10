
package org.springframework.samples.petclinic.repository;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.springdatajpa.BookingRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BookingRepositoryTests {

	@Autowired
	private BookingRepository bookingRepository;


	//Positivo

	@Test
	void shouldFindBookingById() {
		int id = 1;
		Booking b = this.bookingRepository.findById(id).get();
		Assertions.assertEquals(b.getProducto().getName(), "Champu Para Perros");
	}

	//Negativo

	@Test
	void shouldNotFindProductById() {
		int id = 1;
		Booking b = this.bookingRepository.findById(id).get();
		Assertions.assertEquals(b.getProducto().getName(), "Champu Para Perros");
	}

}
