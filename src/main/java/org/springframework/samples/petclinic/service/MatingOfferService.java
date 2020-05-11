package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.MatingOffer;
import org.springframework.samples.petclinic.repository.springdatajpa.MatingOfferRepository;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Service;

@Service
public class MatingOfferService {

	@Autowired
	private MatingOfferRepository matingOfferRepo;

	public MatingOfferService(MatingOfferRepository matingOfferRepo) {
		this.matingOfferRepo = matingOfferRepo;
	}

	@Transactional(rollbackOn = DuplicatedPetNameException.class)
	public void saveCita(MatingOffer matingOffer) throws DataAccessException, DuplicatedPetNameException {

		matingOfferRepo.save(matingOffer);
	}

	@Transactional
	public int matingOfferCount() {
		return (int) matingOfferRepo.count();
	}

	@Transactional
	public Iterable<MatingOffer> findAll() {
		return matingOfferRepo.findAll();
	}

	@Transactional
	public void save(MatingOffer matingOffer) {
		matingOfferRepo.save(matingOffer);
	}

	@Transactional
	public void delete(MatingOffer matingOffer) {
		matingOffer.getPet().getMatingOffers().remove(matingOffer);
		matingOfferRepo.deleteById(matingOffer.getId());
	}

	public Optional<MatingOffer> findMatingOfferById(Integer id) {
		return matingOfferRepo.findById(id);
	}

	public MatingOffer findMatById(Integer id) {
		return matingOfferRepo.findByMatId(id);
	}

	public Optional<MatingOffer> findMatingOfferById(int matingOfferId) {
		return matingOfferRepo.findById(matingOfferId);
	}
	
	public  List<MatingOffer> findByMatPetId(int petId) {
		return matingOfferRepo.findByMatPetId(petId);
	}
}