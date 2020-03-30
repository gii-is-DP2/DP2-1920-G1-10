
package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Booking;
import org.springframework.samples.petclinic.repository.springdatajpa.BookingRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

	@Autowired
	private BookingRepository reservaRepo;


	@Transactional
	public int reservaCount() {
		return (int) this.reservaRepo.count();
	}

	@Transactional
	public Iterable<Booking> findAll() {

		return this.reservaRepo.findAll();
	}
	@Transactional
	public Iterable<Booking> findAllByUserId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getName();

		String userId = principal.toString();

		return this.reservaRepo.findAllByUserId(userId);
	}

	@Transactional
	public void save(final Booking booking) {
		this.reservaRepo.save(booking);
	}

	@Transactional
	public Optional<Booking> findBookingById(final int bookingId) {
		// TODO Auto-generated method stub
		return this.reservaRepo.findById(bookingId);
	}
	@Transactional
	public void delete(final Booking booking) {
		this.reservaRepo.delete(booking);

	}
}
