
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BookingServiceTests {

	@Autowired
	protected BookingService bookingService;


	// Positivo

	@Test
	@WithMockUser(username = "prueba1", password = "practica")
	void shouldFindBookingWithCorrectId() {
		Booking b = this.bookingService.findBookingById(1).get();
		String nombre = b.getProducto().getName();
		assertThat(nombre).startsWith("Champu Para Perros");
		assertThat(b.getNumProductos().equals(4));

	}

	@Test
	@WithMockUser(username = "prueba1", password = "practica")
	void shouldDeleteProduct() {
		Collection<Booking> bookings = (Collection<Booking>) bookingService.findAll();
		int tamAntes = bookings.size() - 1;
		bookingService.delete(bookingService.findBookingById(1).get());
		Collection<Booking> bookingsAfter = (Collection<Booking>) bookingService.findAll();
		int tamDespues = bookingsAfter.size() - 1;
		assertTrue(tamDespues != tamAntes);
	}

	// Negativo

	@Test
	@WithMockUser(username = "prueba1", password = "practica")
	void shouldNotFindProductWithCorrectId() {
		Booking b = this.bookingService.findBookingById(1).get();
		assertTrue(!b.getUser().contains("Comida"));
		assertThat(!b.getProducto().getDescription().contains("Para Mascotas"));
		assertNotEquals(b.getNumProductos(), 42);

	}

}
