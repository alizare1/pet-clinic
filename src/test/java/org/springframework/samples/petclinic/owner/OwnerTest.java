package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OwnerTest {

	private Owner owner;

	@BeforeEach
	public void setup() {
		owner = new Owner();
		owner.setId(1);
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("6085551023");
	}


	// State verification
	@Test
	public void getPetShouldReturnPetWhenExists() {
		String PET_NAME = "p";
		Pet pet = new Pet();
		pet.setName(PET_NAME);
		owner.addPet(pet);

		Pet returnedPet = owner.getPet(PET_NAME);
		assertNotNull(returnedPet);
		assertEquals(returnedPet.getName(), pet.getName());
	}

	// State verification
	@Test
	public void getPetShouldReturnNullWhenPetDoesNotExist() {
		String PET_NAME = "p";
		String ALT_PET_NAME = "a";
		Pet pet = new Pet();
		pet.setName(PET_NAME);
		owner.addPet(pet);

		Pet returnedPet = owner.getPet(ALT_PET_NAME);
		assertNull(returnedPet);
	}

	// Behavior verification
	@Test
	public void getPetShouldNotReturnNewPetWithIgnoreNew() {
		String PET_NAME = "p";
		Pet pet = mock(Pet.class);
		when(pet.isNew()).thenReturn(true);
		owner.addPet(pet);

		Pet returnedPet = owner.getPet(PET_NAME, true);
		assertNull(returnedPet);
		verify(pet, times(0)).getName(); // if ignored, it shouldn't compare names
		verify(pet, times(2)).isNew();
	}

	// Behavior verification
	@Test
	public void getPetShouldReturnPetWithWithIgnoreNew() {
		String PET_NAME = "p";
		Pet pet = mock(Pet.class);
		when(pet.isNew()).thenReturn(true, false);
		when(pet.getName()).thenReturn(PET_NAME);
		owner.addPet(pet);

		Pet returnedPet = owner.getPet(PET_NAME, true);
		assertNotNull(returnedPet);
		assertEquals(returnedPet, pet);
		verify(pet, times(1)).getName();
		verify(pet, times(2)).isNew();
	}

}
