package org.springframework.samples.petclinic.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.h2.security.auth.AuthConfigException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Cita;
import org.springframework.samples.petclinic.model.MatingOffer;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.MatingOfferService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/matingOffers")
public class MatingOfferController {

	@Autowired
	private MatingOfferService matingOfferService;
	@Autowired
	private PetService petService;
	@Autowired
	private OwnerService ownerService;

	@GetMapping(path = "/matingOffers")
	public String matingOfferList(ModelMap modelMap) {

		String view = "matingOffers/matingOfferList";
		Iterable<MatingOffer> matingOffers = matingOfferService.findAll();
		modelMap.addAttribute("matingOffers", matingOffers);
		return view;
	}

	@RequestMapping("matingOffer")
	public MatingOffer loadPetWithMatingOffers(@PathVariable("petId") int petId) {
		Pet pet = this.petService.findPetById(petId);
		MatingOffer matingOffer = new MatingOffer();
		matingOffer.setPet(pet);
		return matingOffer;
	}

	@GetMapping(path = "/matingOffers/new")
	public String createMatingOffer(ModelMap modelMap) {
		String view = "matingOffers/createMatingOfferForm";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
		String userId = principal.toString();
		Iterable<Pet> pets = petService.findPetbyOwnerId(userId);

		modelMap.addAttribute("pets", pets);
		modelMap.addAttribute("matingOffer", new MatingOffer());
		return view;
	}

	@PostMapping(value = "matingOffers/new")
	public String processNewMatingOfferForm(@Valid MatingOffer matingOffer, BindingResult result)
			throws DataAccessException, DuplicatedPetNameException {
//		if(matingOffer.getDate().isBefore(LocalDate.now())){
//			 
//			return "matingOffers/createMatingOfferForm";
//		}
		if (result.hasErrors()) {
			return "matingOffers/createMatingOfferForm";
		} else {

			this.matingOfferService.save(matingOffer);
			return "redirect:/matingOffers";
		}
	}

	@PostMapping(path = "/save")
	private String saveMatingOffer(@Valid MatingOffer matingOffer, BindingResult res, ModelMap modelMap) {
		if (res.hasErrors()) {
			modelMap.addAttribute("matingOffer", matingOffer);
			return "matingOffers/createMatingOfferForm";
		} else if (matingOffer.getName() == null) {
			throw new IllegalArgumentException("Fallo");
		} else {
			matingOfferService.save(matingOffer);
			modelMap.addAttribute("message", "Saved successfully");
		}
		return matingOfferList(modelMap);
	}

	@GetMapping(path = "matingOffers/delete/{matingOfferId}")
	public String deleteMatingOffer(@PathVariable("matingOfferId") int matingOfferId, ModelMap modelMap) {
		Optional<MatingOffer> matingOffer = matingOfferService.findMatingOfferById(matingOfferId);
		
		 MatingOffer met = matingOfferService.findMatById(matingOfferId);
	    String owner = met.getPet().getOwner().getUser().getUsername();
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getName();
	    String userId = principal.toString();

		if (matingOffer.isPresent()) {
			
			if(owner.equals(userId)) {
			matingOfferService.delete(matingOffer.get());
			modelMap.addAttribute("message", "Mating offer successfully deleted");
			}else{
				throw new AuthConfigException("No puede borrar ofertas que no son suyas");
		}}else {
			modelMap.addAttribute("message", "Mating offer not found");
			}
		return matingOfferList(modelMap);
	}

	@GetMapping("/pets/{petId}/matingOffers/{matingOfferId}")
	public ModelAndView showMat(@PathVariable("matingOfferId") int matingOfferId, @PathVariable("petId") int petId) {
		ModelAndView mav = new ModelAndView("matingOffers/matingOfferDetails");
		Pet pet = petService.findPetById(petId);
		MatingOffer mating = matingOfferService.findMatById(matingOfferId);
		Set<Cita> citas = mating.getCitas();
		System.out.println(citas.size());

		mav.addObject("matingOffer", mating);

		mav.addObject("citas", citas);
		return mav;
	}
}