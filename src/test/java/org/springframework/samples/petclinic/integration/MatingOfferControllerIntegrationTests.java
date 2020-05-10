package org.springframework.samples.petclinic.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Gender;
import org.springframework.samples.petclinic.model.MatingOffer;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.MatingOfferService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.web.MatingOfferController;
import org.springframework.samples.petclinic.web.PetController;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

/**
 * Test class for the {@link PetController}
 *
 * @author Colin But
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MatingOfferControllerIntegrationTests {
	
	private static final int TEST_OWNER_ID = 1;

	private static final int TEST_PET_ID = 1;

	@Autowired
	private MatingOfferController matingOfferController;


	@Autowired
	private MatingOfferService matingOfferService;
	
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private OwnerService ownerService;
	
	@WithMockUser(username = "prueba", password = "prueba", authorities = "owner")
    @Test
	void testInitCreationForm() throws Exception {
		ModelMap model=new ModelMap();
		
		String view=matingOfferController.createMatingOffer(model);
		
		assertEquals(view,"matingOffers/createMatingOfferForm");
		assertNotNull(model.get("matingOffer"));		
	}

	@WithMockUser(username = "prueba", password = "prueba", authorities = "owner")
    @Test
	void testProcessCreationFormSuccess() throws Exception {
    	ModelMap model=new ModelMap();
    	MatingOffer newOffer=new MatingOffer();
    	Owner ownerTest = ownerService.findOwnerById(TEST_OWNER_ID);
    	Pet petTest=petService.findPetById(TEST_PET_ID);
		newOffer.setPet(petTest);
		newOffer.setDescription("Testaco");
		model.addAttribute(newOffer);
		String view=matingOfferController.createMatingOffer(model);
		assertEquals(view,"matingOffers/createMatingOfferForm");				
	}

	@WithMockUser(username = "prueba", password = "prueba", authorities = "owner")
    @Test
	void testProcessCreationFormHasErrors() throws Exception {
		ModelMap model=new ModelMap();
    	Owner owner=ownerService.findOwnerById(TEST_OWNER_ID);
    	Pet petTest=petService.findPetById(TEST_PET_ID);
    	MatingOffer newOffer = new MatingOffer();
    	newOffer.setPet(petTest);
    	newOffer.setDescription("");
    	model.addAttribute(newOffer);
		BindingResult bindingResult=new MapBindingResult(new HashMap(),"");
		bindingResult.reject("description", "Requied!");
		
		String view=matingOfferController.createMatingOffer(model);
		
		assertEquals(view,"matingOffers/createMatingOfferForm");		
	}
	
	@WithMockUser(username = "prueba", password = "prueba", authorities = "owner")
    @Test
	void testDeleteMat() throws Exception {
		ModelMap model = new ModelMap();

        String view = matingOfferController.deleteMatingOffer(6, model);

        assertEquals(view, "matingOffers/matingOfferList");
        assertNotNull(model.get("message"));
	}
}
