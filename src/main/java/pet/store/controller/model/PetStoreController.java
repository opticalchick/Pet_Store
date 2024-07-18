package pet.store.controller.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData.PetStoreCustomer;
import pet.store.controller.model.PetStoreData.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

	@Autowired
	private PetStoreService petStoreService;

	@PostMapping("")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
		log.info("Creating store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, 
			@RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId);
		log.info("Updating pet store {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PostMapping("/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreEmployee insertEmployee(@PathVariable Long petStoreId, 			
			@RequestBody PetStoreEmployee petStoreEmployee) {
		log.info("Creating employee {} for pet store with ID={}", petStoreEmployee, petStoreId);
		return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
	}
	
	@PostMapping("/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreCustomer insertCustomer(@PathVariable Long petStoreId,
			@RequestBody PetStoreCustomer petStoreCustomer) {
		log.info("Creating customer {} for pet store with ID={}", petStoreCustomer, petStoreId);
		return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
	}
	
	@GetMapping()
	public List<PetStoreData> retriveAllPetStores() {
		log.info("Retrieve all pet stores.");
		return petStoreService.retrieveAllPetStores();
	}
	
	@GetMapping("/{petStoreId}")
	public PetStoreData retrivePetStoreById(@PathVariable Long petStoreId) {
		log.info("Retrieving pet store by ID={}", petStoreId);
		return petStoreService.retrivePetStoreById(petStoreId);
	}
}


