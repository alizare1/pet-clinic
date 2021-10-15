package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assume;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(Theories.class)
public class OwnerTheoryTests {
	private Owner owner;

	@Before
	public void setup() {
		owner = new Owner();
		owner.setId(1);
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("6085551023");
	}

	@DataPoints
	public static String[] petNames = {"A", "B", "C", "D"};

	@DataPoints
	public static List<List<Pet>> petsCollections() {
		List<List<Pet>> petsCollections = new ArrayList<>();
		ArrayList<Pet> pets = new ArrayList<>();
		for (String name : petNames) {
			Pet pet = new Pet();
			pet.setName(name);
			pets.add(pet);
		}

		petsCollections.add(Arrays.asList(pets.get(0), pets.get(1), pets.get(2)));
		petsCollections.add(Arrays.asList(pets.get(1), pets.get(2)));
		petsCollections.add(Collections.singletonList(pets.get(1)));

		return petsCollections;
	}

	@Theory
	public void getPetShouldReturnPetWhenExists(String name, List<Pet> pets) {
		Assume.assumeTrue(!pets.isEmpty());
		Pet expectedPet =  pets.stream().filter(p -> p.getName().equals(name)).findAny().orElse(null);
		Assume.assumeNotNull(expectedPet);
		for (Pet pet : pets) {
			owner.addPet(pet);
		}

		Pet pet = owner.getPet(name);
		assertNotNull(pet);
		assertEquals(expectedPet.getName(), pet.getName());
		assertEquals(expectedPet.getId(), pet.getId());
	}

	@Theory
	public void getPetShouldReturnNullWhenNotExists(String name, List<Pet> pets) {
		Pet expectedPet =  pets.stream().filter(p -> p.getName().equals(name)).findAny().orElse(null);
		Assume.assumeTrue(expectedPet == null);
		for (Pet pet : pets) {
			owner.addPet(pet);
		}

		assertNull(owner.getPet(name), "Pet doesn't exist but returned object is not null");
	}

	@Theory
	public void getPetIgnoreNewDoesNotReturnNewPets(String name, List<Pet> pets) {
		Assume.assumeTrue(!pets.isEmpty());
		Pet expectedPet =  pets.stream().filter(p -> p.getName().equals(name)).findAny().orElse(null);
		Assume.assumeNotNull(expectedPet);
		for (Pet pet : pets) {
			owner.addPet(pet);
		}

		assertNull(owner.getPet(name, true), "Pet is new but is not ignored by getPet()");
	}

	@Theory
	public void getPetIgnoreNewReturnsPetIfHasID(String name, List<Pet> pets) {
		Assume.assumeTrue(!pets.isEmpty());
		Pet expectedPet =  pets.stream().filter(p -> p.getName().equals(name)).findAny().orElse(null);
		Assume.assumeNotNull(expectedPet);
		Assume.assumeTrue(expectedPet.isNew());
		for (Pet pet : pets) {
			owner.addPet(pet);
		}
		expectedPet.setId(1);


		Pet pet = owner.getPet(name, true);
		assertNotNull(pet, "with ignoreNew returns null when pet with ID exists");
		assertEquals(pet.getId(), expectedPet.getId(), "wrong pet is returned");
	}


}
