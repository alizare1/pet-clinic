package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SavePetFeatureSteps {
	@Autowired
	PetService petService;

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	@Autowired
	OwnerRepository ownerRepository;

	private Pet pet;
	private Pet actualResult;
	private Owner owner;

	private void setupOwner() {
		owner = new Owner();
		owner.setFirstName("Hamid");
		owner.setLastName("Hamidi");
		owner.setAddress("Valiasr");
		owner.setCity("Tehran");
		owner.setTelephone("09191919223");
		ownerRepository.save(owner);
	}

	@Given("There is an owner")
	public void thereIsAnOwner() {
		setupOwner();

//		owner.addPet(pet);
//		petRepository.save(pet);
	}

	@When("A save action is performed on a new pet named {string} for the owner")
	public void aNewPetIsSavedForTheOwner(String name) {
		PetType petType = new PetType();
		petType.setName("maziar");
		petTypeRepository.save(petType);
		pet = new Pet();
		pet.setName(name);
		pet.setType(petType);
		pet.setBirthDate(LocalDate.now());
		petService.savePet(pet, owner);
	}

	@Then("The pet named {string} is saved correctly")
	public void thePetIsSavedCorrectly(String name) {
		assertNotNull(owner.getPet(name));
	}
}
