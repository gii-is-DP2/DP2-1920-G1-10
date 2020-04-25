package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.MatingOffer;

public interface MatingOfferRepository extends CrudRepository<MatingOffer, Integer> {

	@Query("SELECT mt FROM MatingOffer mt where mt.id =?1")
	MatingOffer findByMatId(int matId) throws DataAccessException;
	@Query("SELECT mt FROM MatingOffer mt join mt.pet  p where p.id =?1")
	List<MatingOffer> findByMatPetId(int petId) throws DataAccessException;
}