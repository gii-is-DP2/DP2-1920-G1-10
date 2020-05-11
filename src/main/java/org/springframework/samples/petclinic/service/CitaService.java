package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.MatingOffer;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.repository.springdatajpa.CitaRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import antlr.debug.Event;

@Service
public class CitaService {
	@Autowired
	private CitaRepository citaRepository;
	@Autowired
	private PetService petService;
	@Autowired
	private MatingOfferService metMatingOfferService;

	public CitaService(CitaRepository citaRepository) {
		this.citaRepository = citaRepository;
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void saveCita(Cita cita) throws DataAccessException, DuplicatedPetNameException {

		citaRepository.save(cita);
	}

	@Transactional
	public Collection<Cita> findCitaByPet(int petId) {
		
		return citaRepository.findByPet(petId);
	}

	@Transactional
	public Iterable<Cita> findAll() {
		return citaRepository.findAll();
	}

	public Cita findCitaById(Integer id) {
		return citaRepository.findCitaById(id);
	}

	@Transactional
	public void delete(Cita cita) throws DataAccessException {
		
		cita.getPet1().getCitas_pet1().remove(cita);
		cita.getPet1().getCitas_pet2().remove(cita);
		
		cita.getPet2().getCitas_pet2().remove(cita);
		cita.getPet2().getCitas_pet1().remove(cita);
		
		
		
		
		List<MatingOffer> mat1 =  metMatingOfferService.findByMatPetId(cita.getPet1().getId());
		for (int i = 0; i < mat1.size(); i++) {
			mat1.get(i).getCitas().remove(cita);
		
		}
		
		List<MatingOffer> mat2 =  metMatingOfferService.findByMatPetId(cita.getPet2().getId());
		for (int j = 0; j < mat2.size(); j++) {
			mat2.get(j).getCitas().remove(cita);
			
			
		}
//		cita.setPet1(null);
//		cita.setPet2(null);
		
		citaRepository.deleteById(cita.getId());
	}
}
