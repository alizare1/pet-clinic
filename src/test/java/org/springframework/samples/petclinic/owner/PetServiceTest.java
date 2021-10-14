package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OwnerTest {
	private Owner owner;
	private String ownerAddress;
	private String ownerCity;
	private String ownerTelephone;
	private String ownerFirstName;
	private String ownerLastName;
	Integer ownerId;
	private Pet cat, dog, rabbit, parrot;

	@BeforeEach
	void Setup() {
		ownerAddress = "915 Smith Rd.Whitehall, PA 18052";
		ownerCity = "Sydney";
		ownerTelephone = "+2142406944";
		ownerFirstName = "Jolie";
		ownerLastName = "Londyn";
		ownerId = 1;

		owner = new Owner();
		owner.setAddress(ownerAddress);
		owner.setCity(ownerCity);
		owner.setTelephone(ownerTelephone);
		owner.setFirstName(ownerFirstName);
		owner.setLastName(ownerLastName);
		owner.setId(ownerId);

		cat = new Pet();
		cat.setName("cat");

		dog = new Pet();
		dog.setName("dog");

		rabbit = new Pet();
		rabbit.setName("rabbit");

		parrot = new Pet();
		parrot.setName("parrot");
	}

	@Test
	void getAddressShouldReturnProperly() {
		String expectedAddress = ownerAddress;
		String actualAddress = owner.getAddress();
		assertEquals(expectedAddress, actualAddress);
	}

	@Test
	void getCityShouldReturnProperly() {
		String expectedCity = ownerCity;
		String actualCity = owner.getCity();
		assertEquals(expectedCity, actualCity);
	}

	@Test
	void getFirstNameShouldReturnProperly() {
		String expectedFirstName = ownerFirstName;
		String actualFirstName = owner.getFirstName();
		assertEquals(expectedFirstName, actualFirstName);
	}

	@Test
	void getLastNameShouldReturnProperly() {
		String expectedLastName = ownerLastName;
		String actualLastName = owner.getLastName();
		assertEquals(expectedLastName, actualLastName);
	}

	@Test
	void getIdShouldReturnProperly() {
		Integer expectedId = ownerId;
		Integer actualId = owner.getId();
		assertEquals(expectedId, actualId);
	}

	@Test
	void getPetsInternalShouldReturnCorrectlyWhenNoPetIsAdded() {
		Set<Pet> expectedPets = new HashSet<>();
		Set<Pet> actualPets = owner.getPetsInternal();
		assertEquals(expectedPets, actualPets);
	}

	@Test
	void getPetsInternalShouldReturnCorrectlyWhenSomePetsAreAdded() {
		Set<Pet> expectedPets = new HashSet<>();
		expectedPets.add(dog);
		expectedPets.add(parrot);
		expectedPets.add(cat);

		owner.addPet(dog);
		owner.addPet(parrot);
		owner.addPet(cat);
		Set<Pet> actualPets = owner.getPetsInternal();

		assertEquals(expectedPets, actualPets);
	}

	@Test
	void getPetsShouldReturnCorrectlyWhenNoPetIsAdded() {
		List<Pet> expectedPets = new ArrayList<>();

		List<Pet> actualPets = owner.getPets();

		assertEquals(expectedPets, actualPets);
	}

	@Test
	void getPetsShouldSortAndReturnCorrectlyWhenSomePetsAreAdded() {
		List<Pet> pets = new ArrayList<>();
		pets.add(dog);
		pets.add(parrot);
		pets.add(cat);
		List<Pet> expectedPets = new ArrayList<>(pets);
		PropertyComparator.sort(expectedPets, new MutableSortDefinition("name", true, true));

		owner.addPet(dog);
		owner.addPet(parrot);
		owner.addPet(cat);
		List<Pet> actualPets = owner.getPets();

		assertEquals(expectedPets, actualPets);
	}

	@Test
	void addPetShouldProperlyAddNewPets() {
		owner.addPet(rabbit);
		owner.addPet(parrot);

		assertEquals(rabbit, owner.getPet(rabbit.getName()));
		assertEquals(parrot, owner.getPet(parrot.getName()));
		assertEquals(2, owner.getPets().size());

		assertEquals(rabbit.getOwner(), owner);
		assertEquals(parrot.getOwner(), owner);
	}

	@Test
	void addPetShouldProperlyAddPetsWithId() {
		owner.addPet(rabbit);
		rabbit.setId(1);

		assertEquals(rabbit, owner.getPet(rabbit.getName()));
		assertEquals(rabbit.getOwner(), owner);
		assertEquals(1, owner.getPets().size());
	}

	@Test
	void removePetShouldProperlyRemoveAddedPets() {
		owner.addPet(cat);
		owner.removePet(cat);

		assertNull(owner.getPet(dog.getName()));
		assertTrue(owner.getPets().isEmpty());
	}

	@Test
	void getPetShouldReturnExistingPetProperly() {
		owner.addPet(cat);

		assertEquals(cat, owner.getPet(cat.getName()));
	}

	@Test
	void getPetShouldReturnNullWhenPetDoesNotExists() {
		assertNull(owner.getPet(dog.getName()));
	}

	@Test
	void getPetShouldReturnExistingPetWithIdProperly() {
		owner.addPet(parrot);
		parrot.setId(1);

		assertEquals(parrot, owner.getPet(parrot.getName(), true));
	}
}
