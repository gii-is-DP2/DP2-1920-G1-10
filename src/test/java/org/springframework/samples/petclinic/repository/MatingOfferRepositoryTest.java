package org.springframework.samples.petclinic.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.MatingOffer;
import org.springframework.samples.petclinic.repository.springdatajpa.MatingOfferRepository;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class MatingOfferRepositoryTest {
	
	@Autowired
	MatingOfferRepository matingOfferRepository;

	@Test
	void testFindByMatId() {
		int id=1;
		MatingOffer matingOffer = this.matingOfferRepository.findByMatId(id);
		assertEquals("Chico y peludo", matingOffer.getDescription());
	}
	
	@Test
	void testFindByMatPetId() {
		int id=1;
		List<MatingOffer> matingOffers = this.matingOfferRepository.findByMatPetId(id);
		assertEquals("Chico y peludo", matingOffers.get(0).getDescription());
	}
	
	@Test
	void testFindMatByDescription() {
		String description = "Chico y peludo";
		MatingOffer matingOffer = this.matingOfferRepository.findMatByDescription(description);
		assertEquals("Leo", matingOffer.getPet().getName());
	}
}
