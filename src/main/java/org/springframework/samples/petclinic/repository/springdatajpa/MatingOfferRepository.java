package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.MatingOffer;

public interface MatingOfferRepository extends CrudRepository<MatingOffer, Integer> {

	@Query("SELECT mt FROM MatingOffer mt where mt.id =?1")
	MatingOffer findByMatId(int matId) throws DataAccessException;
	
}