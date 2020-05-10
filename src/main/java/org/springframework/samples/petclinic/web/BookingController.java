
package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.model.Product;
import org.springframework.samples.petclinic.service.BookingService;
import org.springframework.samples.petclinic.service.ProductService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bookings")
public class BookingController {

	@Autowired
	private BookingService	reservaService;

	@Autowired
	public ProductService	productService;


	@GetMapping()
	private String listadoReserva(final ModelMap modelMap) {
		String vista = "bookings/bookingList";
		Iterable<Booking> reservas = this.reservaService.findAllByUserId();
		modelMap.addAttribute("reservas", reservas);
		return vista;
	}

	@GetMapping(path = "/new/{productId}")
	public String salvarReserva(@PathVariable("productId") final int productId, final ModelMap modelMap) {
		System.out.println(productId);
		String view = "bookings/editBooking";
		LocalDate date = LocalDate.now();

		Product p = this.productService.findProductById(productId);

		Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
		String userId = principal.toString();
		Booking bk = new Booking();

		modelMap.addAttribute("booking", bk);
		modelMap.addAttribute("product", p);
		modelMap.addAttribute("user", userId);
		modelMap.addAttribute("date", date);
		return view;
	}

	@PostMapping(path = "/save")
	public String salvarReserva(@Valid final Booking booking, @RequestParam final int id, final BindingResult res, final ModelMap modelMap) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
		String userId = principal.toString();

		if (res.hasErrors()) {
			modelMap.addAttribute("booking", booking);

			return "bookings/editBooking";
		} else if (booking.getNumProductos() < 0 || booking.getNumProductos() > this.productService.findProductById(id).getStock()) {
			throw new IllegalArgumentException("Fallo");
		} else {
			booking.setProducto(this.productService.findProductById(id));
			booking.setFecha(LocalDate.now());
			booking.setUser(userId);

			Product p = this.productService.findProductById(id);

			Booking prev = this.reservaService.findPreviousBooking(id);
			if (prev != null) {
				p.setStock(p.getStock() + prev.getNumProductos());
				this.reservaService.delete(prev);
			}

			this.reservaService.save(booking);
			modelMap.addAttribute("message", "Saved successfully");
			p.setStock(p.getStock() - booking.getNumProductos());
			this.productService.save(p);
		}
		return this.listadoReserva(modelMap);
	}

	@GetMapping(path = "delete/{bookingId}")
	public String borrarReserva(@PathVariable("bookingId") final int bookingId, final ModelMap modelMap) {
		Optional<Booking> booking = this.reservaService.findBookingById(bookingId);
		if (booking.isPresent()) {
			Product p = booking.get().getProducto();
			p.setStock(p.getStock() + booking.get().getNumProductos());
			this.productService.save(p);
			this.reservaService.delete(booking.get());
			modelMap.addAttribute("message", "Product successfully deleted");
		} else {
			modelMap.addAttribute("message", "Product not found");
		}
		return this.listadoReserva(modelMap);
	}
}
