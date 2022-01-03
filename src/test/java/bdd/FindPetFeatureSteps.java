package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindPetFeatureSteps {
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

	@Given("There is a pet with ID")
	public void thereIsAPet() {
		setupOwner();
		PetType petType = new PetType();
		petType.setName("maziar");
		petTypeRepository.save(petType);
		pet = new Pet();
		pet.setName("amin");
		pet.setType(petType);
		pet.setBirthDate(LocalDate.now());
		owner.addPet(pet);
		petRepository.save(pet);
	}

	@When("The pet is looked up by his ID")
	public void heIsLookedUpById() {
		actualResult = petService.findPet(pet.getId());
	}

	@Then("The pet is returned correctly")
	public void ownerIsReturned() {
		assertEquals(pet.getId(), actualResult.getId());
	}
}
