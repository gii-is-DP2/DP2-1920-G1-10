
package org.springframework.samples.petclinic.web.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.samples.petclinic.web.BookingController;
import org.springframework.samples.petclinic.web.PetController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

/**
 * Test class for the {@link PetController}
 *
 * @author Colin But
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingControllerIntegrationTests {

	private static final int	TEST_OWNER_ID	= 1;

	private static final int	TEST_PET_ID		= 1;

	@Autowired
	private BookingController	bookingController;

	@Autowired
	private BookingService		bookingService;

	@Autowired
	private ProductService		productService;


	@Test
	@WithMockUser(username = "prueba1")
	void testInitCreationForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = bookingController.salvarReserva(TEST_OWNER_ID, model);

		assertEquals(view, "bookings/editBooking");
		assertNotNull(model.get("booking"));
	}

	@Test
	@WithMockUser(username = "prueba1")
	void testProcessCreationFormSuccess() throws Exception {
		Product producto = productService.findProductById(TEST_OWNER_ID);
		ModelMap model = new ModelMap();
		Booking newbooking = new Booking();
		newbooking.setFecha(LocalDate.now());
		newbooking.setNumProductos(8);
		newbooking.setProducto(producto);
		newbooking.setUser("prueba1");

		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");

		String view = bookingController.salvarReserva(newbooking, TEST_OWNER_ID, bindingResult, model);

		assertEquals(view, "bookings/bookingList");
	}

	@Test
	@WithMockUser(username = "prueba1")
	void testProcessCreationFormHasErrors() throws Exception {
		Product producto = productService.findProductById(TEST_OWNER_ID);
		ModelMap model = new ModelMap();
		Booking newbooking = new Booking();
		newbooking.setFecha(LocalDate.now());
		newbooking.setProducto(producto);
		newbooking.setUser("prueba1");

		BindingResult bindingResult = new MapBindingResult(Collections.emptyMap(), "");
		bindingResult.reject("numProductos", "Requied!");

		String view = bookingController.salvarReserva(newbooking, TEST_OWNER_ID, bindingResult, model);

		assertEquals(view, "bookings/editBooking");
	}
	//
	@Test
	@WithMockUser(username = "prueba1")
	void testInitDeleteForm() throws Exception {
		ModelMap model = new ModelMap();

		String view = bookingController.borrarReserva(TEST_OWNER_ID, model);

		assertEquals(view, "bookings/bookingList");
		assertNotNull(model.get("message"));
	}

}
