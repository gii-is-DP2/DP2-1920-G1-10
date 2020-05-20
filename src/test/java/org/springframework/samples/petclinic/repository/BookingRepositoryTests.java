
package org.springframework.samples.petclinic.repository;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.Collection;
import java.util.Optional;

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
		Booking b = this.bookingRepository.findBookingById(id);
		Assertions.assertEquals(b.getProducto().getName(), "Champu Para Perros");
	}

	//Negativo

	@Test
	void shouldNotFindBookingById() {
		int id = 3;
		Optional<Booking> b = this.bookingRepository.findById(id);
		Assertions.assertNotEquals(b.get().getProducto().getName(), "Champú para perros");
	}

	@Test
	void shouldFindBookingsByUserId() {
		String id = "prueba1";
		Collection<Booking> b = (Collection<Booking>) this.bookingRepository.findAllByUserId(id);
		Assertions.assertEquals(b.size(), 1);
	}
	@Test
	void shouldNotFindBookingsByUserId() {
		String id = "prueba";
		Collection<Booking> b = (Collection<Booking>) this.bookingRepository.findAllByUserId(id);
		Assertions.assertTrue(b.isEmpty());
	}
	@Test
	void shouldFindPreviousBooking() {
		String user = "prueba1";
		int id = 1;
		Booking b = this.bookingRepository.findPreviousBooking(user, id);
		Assertions.assertEquals(b.getProducto().getId(), 1);
	}
	@Test
	void shouldNotFindPreviousBooking() {
		String user = "prueba1";
		int id = 4;
		Booking b = this.bookingRepository.findPreviousBooking(user, id);
		Assertions.assertEquals(b, null);
	}

}
