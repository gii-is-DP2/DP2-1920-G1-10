package org.springframework.samples.petclinic.web.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.MatingOffer;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.MatingOfferService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.web.CitaController;
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
class CitaControllerIntegrationTests {

	private static final int TEST_MAT_ID = 1;

	private static final int TEST_PET_ID = 16;

	@Autowired
	private CitaController citaController;


	@Autowired
	private CitaService citaService;
        
	@Autowired
	private PetService petService;	
	@Autowired
	private MatingOfferService matingOfferService;	
	
	
	@WithMockUser(username = "prueba", password = "prueba" )
    @Test
	void testInitCreationForm() throws Exception {
		MatingOffer met = matingOfferService.findMatById(TEST_MAT_ID);
		ModelMap model=new ModelMap();
		int petId = 1;
		String view= citaController.initNewCitaForm(1, 1 ,model);
		
		assertEquals(view,"citas/editCitas");
		assertNotNull(model.get("cita"));		
	}
	@WithMockUser(username = "prueba", password = "prueba" )
	 @Test
		void testProcessCreationFormSuccess() throws Exception {
	    	ModelMap model=new ModelMap();
	    	MatingOffer met = matingOfferService.findMatById(TEST_MAT_ID);
	    	Cita n = new Cita();
	    	n.setPlace("hola");
	    	n.setDate(LocalDate.now());
	    	n.setStatus("Pending");
	    	n.setId(99);
	    	 	
			BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
			
			String view=citaController.processNewCitaForm(n, 1, 16 , bindingResult );
	    	
			assertEquals(view,"redirect:/matingOffers/");				
		}
	@WithMockUser(username = "prueba", password = "prueba" )
	
	 @Test
		void testProcessCreationFormHashError() throws Exception {
	    	ModelMap model=new ModelMap();
	    	MatingOffer met = matingOfferService.findMatById(TEST_MAT_ID);
	    	Cita n = new Cita();
	    	n.setPlace("hola");
	    	n.setDate(LocalDate.now());
	    	n.setStatus("Pending");
	    	n.setId(99);
	    	 	
			BindingResult bindingResult=new MapBindingResult(Collections.emptyMap(),"");
			bindingResult.reject("place", "Requied!");
			String view=citaController.processNewCitaForm(n, 1, 16 , bindingResult );
	    	
			assertEquals(view,"citas/editCitas");				
		}
   
}