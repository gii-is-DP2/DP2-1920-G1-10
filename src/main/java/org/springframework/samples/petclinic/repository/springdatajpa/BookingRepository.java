
package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Booking;

public interface BookingRepository extends CrudRepository<Booking, Integer> {

	@Query("SELECT a FROM Booking a WHERE a.user=:usernameid")
	Iterable<Booking> findAllByUserId(@Param("usernameid") String userId);

}
