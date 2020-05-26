
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@Transactional
class BookingServiceTests {

	@Autowired
	protected BookingService bookingService;

	// Positivo

	@Test
	@WithMockUser(username = "prueba1", password = "practica")
	@Order(1) 
	void shouldFindBookingWithCorrectId() {
		Booking b = this.bookingService.findById(1);
		String nombre = b.getProducto().getName();
		assertThat(nombre).startsWith("Champu Para Perros");
		assertThat(b.getNumProductos().equals(4));

	}

	@Test
	@WithMockUser(username = "prueba1", password = "practica")
	@Order(3)
	void shouldDeleteBooking() {
		Collection<Booking> bookings = (Collection<Booking>) bookingService.findAll();
		int tamAntes = bookings.size() - 1;
		bookingService.delete(bookingService.findById(1));
		Collection<Booking> bookingsAfter = (Collection<Booking>) bookingService.findAll();
		int tamDespues = bookingsAfter.size() - 1;
		assertTrue(tamDespues != tamAntes);
	}

	// Negativo

	@Test
	@WithMockUser(username = "prueba1", password = "practica")
	@Order(2)
	void shouldNotFindProductWithCorrectId() {
		Booking b = this.bookingService.findById(1);
		assertTrue(!b.getUser().contains("Comida"));
		assertThat(!b.getProducto().getDescription().contains("Para Mascotas"));
		assertNotEquals(b.getNumProductos(), 42);

	}

}
