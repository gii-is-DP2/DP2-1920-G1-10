
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Booking;

public interface BookingRepository extends CrudRepository<Booking, Integer> {

	@Query("SELECT a FROM Booking a WHERE a.user=:usernameid")
	Iterable<Booking> findAllByUserId(@Param("usernameid") String userId);

	@Query("SELECT a FROM Booking a WHERE a.user=:usernameid AND a.producto.id=:productid")
	Booking findPreviousBooking(@Param("usernameid") String userId, @Param("productid") int productId);

	@Query("SELECT b FROM Booking b WHERE b.producto.id=:productId")
	List<Booking> findAllByProductId(@Param("productId") Integer productId);

	@Query("SELECT booking FROM Booking booking where booking.id =?1")
	Booking findBookingById(int bookingId) throws DataAccessException;
}
