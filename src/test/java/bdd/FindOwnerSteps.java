package bdd;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FindOwnerSteps {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	private Owner hamid;
	private Owner actualResult;
	private PetType petType;

	@Given("There is an owner with ID")
	public void thereIsAPetOwnerCalled() {
		hamid = new Owner();
		hamid.setFirstName("Hamid");
		hamid.setLastName("Hamidi");
		hamid.setAddress("Valiasr");
		hamid.setCity("Tehran");
		hamid.setTelephone("09191919223");
		ownerRepository.save(hamid);
	}

	@When("He is looked up by his ID")
	public void heIsLookedUpById() {
		actualResult = petService.findOwner(hamid.getId());
	}

	@Then("He is returned correctly")
	public void ownerIsReturned() {
		assertEquals(hamid.getId(), actualResult.getId());
	}
}
