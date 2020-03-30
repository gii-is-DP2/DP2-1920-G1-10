package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Gender;
import org.springframework.samples.petclinic.model.MatingOffer;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.CitaService;
import org.springframework.samples.petclinic.service.MatingOfferService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class CitaController {
	private static final String VIEWS_CITA_CREATE_OR_UPDATE_FORM = "citas/editCitas";
	@Autowired
	private CitaService citaservice;
	@Autowired
	private PetService petService;
	@Autowired
	private MatingOfferService matingOfferService;

//	@GetMapping(value = "/citas")
//	private String ListadoCitas(ModelMap modelmap) {
//		String vista = "citas/listaCitas";
//		Iterable<Cita> citas = citaservice.findAll();
//		modelmap.addAttribute("citas", citas);
//		return vista;
//
//	}
	@RequestMapping("cita")
	public Cita loadPetWithita(@PathVariable("petId") int petId) {
		Pet pet = this.petService.findPetById(petId);
		Cita cita = new Cita();
		cita.setPet1(pet);
		return cita;
	}
	@GetMapping(value = "/pets/{petId}/matingOffers/{matingOfferId}/citas/new")
	public String initNewVisitForm(@PathVariable("petId") int petId,@PathVariable("matingOfferId") int matingOfferId, ModelMap modelMap) {
//		int gender_id = 0;
		//Pet pet1 = petService.findPetById(petId);
//		if (pet1.getGender().getName().equals("Masculino")) {
//			gender_id = 2;
//		}else {
//			gender_id = 1;
//		}
//		List<Pet> pets = petService.finPetByGender(0);
		Cita cita =  new Cita();
	
		
		Iterable<Pet> pets = petService.findAll();
		modelMap.addAttribute("cita",cita);
		
     	modelMap.put("pets", pets);
		return "citas/editCitas";
	}

	@PostMapping(value = "/pets/{petId}/matingOffers/{matingOfferId}/citas/new")
	public String processNewVisitForm(@Valid Cita cita, @PathVariable("petId") int petId,@PathVariable("matingOfferId") int matingOfferId,BindingResult result) throws DataAccessException, DuplicatedPetNameException {
		if (result.hasErrors()) {
			return "citas/editCitas";
		}
		else {
			MatingOffer met = matingOfferService.findMatById(petId);
			Pet p = petService.findPetById(matingOfferId);
			cita.setPet1(p);
			this.citaservice.saveCita(cita);
			met.getCitas().add(cita);
			
			
			int i = met.getId();
			Set<Cita> citas = met.getCitas();
			this.matingOfferService.save(met);
			
			
			
			return "redirect:/matingOffers/";
		}
	}


//	@PostMapping(path = "citas/save")
//	public String saveCita(@Valid Cita cita,BindingResult result,ModelMap modelMap,@RequestParam("Pet1.id") int pet1_id,@RequestParam("Pet2.id") int pet2_id) throws DataAccessException, DuplicatedPetNameException {
//		String view = "/listacitas";
//		if(result.hasErrors()) {
//			modelMap.addAttribute("cita", cita);
//			
//			return "citas/editCitas";
//		}else {
//			
//			
//		
//		cita.setPet1(petService.findPetById(pet1_id));
//		cita.setPet2(petService.findPetById(pet2_id));
//		
//		modelMap.addAttribute("message", "Cita successfully save");
//		citaservice.saveCita(cita);
//		}
//		return view;
//	}
	@GetMapping(path = "/matingOffers/{matingOfferId}/citas/delete/{citaId}")
    private String borrarcita(@PathVariable("citaId") int citaId,@PathVariable("matingOfferId") int matingOfferId, ModelMap modelMap) {
       Cita cita = citaservice.findCitaById(citaId);
       if(cita != null) {
    	   MatingOffer mat = matingOfferService.findMatById(matingOfferId);
    	   mat.getCitas().remove(cita);
			citaservice.delete(cita);
			modelMap.addAttribute("message", "cita successfully delete");
			
		}else {
			modelMap.addAttribute("message", "cita not found");
		}

       return "redirect:/matingOffers/";
    }
	@GetMapping(value = "/pets/{petId}/citas/edit/{citaId}")
	public String initUpdateForm(@PathVariable("citaId") int citaId, ModelMap model) {
		Cita cita = this.citaservice.findCitaById(citaId);
		model.put("cita", cita);
		return "citas/editCitas";
	}
	  @PostMapping(value = "/pets/{petId}/citas/edit/{citaId}")
		public String processUpdateForm(@Valid Cita cita, BindingResult result, Owner owner,@PathVariable("petId") int petId, @PathVariable("citaId") int citaId,ModelMap model) {
		
			
	        	if (result.hasErrors()) {
	        		return "citas/editCitas";
			}
			else {
	                        Cita citaUpdate =this.citaservice.findCitaById(citaId);
	                       
				BeanUtils.copyProperties(cita, citaUpdate, "id","pet1","pet2","dateTime","place");  
				
	                    try {       
	                  	    
	                        this.citaservice.saveCita(citaUpdate);                    
	                    } catch (DuplicatedPetNameException ex) {
	                        result.rejectValue("name", "duplicate", "already exists");
	                        return "redirect:/matingOffers/";
	                    }
				return "redirect:/matingOffers/";
			}
		}
}