package org.springframework.samples.petclinic.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Provider.Service;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.MatingOffer;
import org.springframework.security.test.context.support.WithMockUser;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class MatingOfferTests {

	@Autowired
	protected MatingOfferService matingOfferService;


	@Test
	void shouldFindMatingOfferWithCorrectId() {
		MatingOffer m1 = this.matingOfferService.findMatById(1);
		assertEquals(m1.getDescription(), "Chico y peludo");
		assertEquals(m1.getPet().getName(), "Leo");
	}

	@Test
	@WithMockUser(username = "prueba", password = "prueba", authorities = "owner")
	void shouldDeleteMatingOffer() {
		Collection<MatingOffer> offers = (Collection<MatingOffer>) matingOfferService.findAll();
		int numOffersAntes = offers.size() - 1;
		matingOfferService.delete(matingOfferService.findMatById(6));
		Collection<MatingOffer> offersDespues = (Collection<MatingOffer>) matingOfferService.findAll();
		int numOffersDespues = offersDespues.size() - 1;
		assertTrue(numOffersDespues != numOffersAntes);
	}

	@Test
	void shouldNotFindMatingOfferWithCorrectId() {
		MatingOffer m1 = this.matingOfferService.findMatById(1);
		assertTrue(!m1.getDescription().contains("Feo"));
		assertNotEquals(m1.getPet().getName(), "Marta");
	}
}
