package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddPetFeatureSteps {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	private Owner hamid;
	private Pet newPet;

	@Given("There is an owner named {string}")
	public void thereIsAPetOwnerCalled(String name) {
		hamid = new Owner();
		hamid.setFirstName(name);
		hamid.setLastName(name);
		hamid.setAddress("Najibie - Kooche shahid abbas alavi");
		hamid.setCity("Tehran");
		hamid.setTelephone("09191919223");
		ownerRepository.save(hamid);
	}

	@When("Add a new pet is performed with him")
	public void hePerformsSavePetService() {
		newPet = petService.newPet(hamid);
	}

	@Then("He has the new pet")
	public void petIsSaved() {
		assertEquals(hamid.getId(), newPet.getOwner().getId());
	}

}
