package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Gender;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class PetService {

	private static PetRepository petRepository;

	private VisitRepository visitRepository;

	@Autowired
	public PetService(PetRepository petRepository, VisitRepository visitRepository) {
		PetService.petRepository = petRepository;
		this.visitRepository = visitRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return petRepository.findPetTypes();
	}

	@Transactional(readOnly = true)
	public Collection<Gender> findPetgender() throws DataAccessException {

		return petRepository.findPetgender();
	}

	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		visitRepository.save(visit);
	}

	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		return petRepository.findById(id);
	}
	
	@Transactional(readOnly = true)
	public static Pet findPetByIdStatic(int id) throws DataAccessException {
		return petRepository.findById(id);
	}

	@Transactional(rollbackFor = DuplicatedPetNameException.class)
	public void savePet(Pet pet) throws DataAccessException, DuplicatedPetNameException {
		Pet otherPet = pet.getOwner().getPetwithIdDifferent(pet.getName(), pet.getId());
		if (StringUtils.hasLength(pet.getName()) && (otherPet != null && otherPet.getId() != pet.getId())) {
			throw new DuplicatedPetNameException();
		} else
			petRepository.save(pet);
	}

	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findByPetId(petId);
	}

	@Transactional
	public Iterable<Pet> findAll() {
		return petRepository.findAll();
	}

	@Transactional
	public List<Pet> finPetByGender(String gender) {
		List<Pet> pets = petRepository.findPetBygender(gender);

		return pets;

	}

	@Transactional
	public Gender finPetByGenderStr(String gender) {
		Gender gen = petRepository.findGenderByStr(gender);

		return gen;

	}

	@Transactional
	public List<Pet> findPetbyOwnerId(String ownerName) {

		return petRepository.findPetByOwneId(ownerName);

	}
}