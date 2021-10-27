package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;
import org.slf4j.Logger;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetManagerTest {
	@Mock
	private PetTimedCache pets;
	@Mock
	private OwnerRepository ownerRepository;
	@Mock
	private Logger logger;
	@InjectMocks
	private PetManager petManager;

	private Pet dog, cat, parrot;

	@BeforeEach
	public void Setup() {
		this.pets = mock(PetTimedCache.class);
		this.ownerRepository = mock(OwnerRepository.class);
		this.logger = mock(Logger.class);
		this.petManager = new PetManager(pets, ownerRepository, logger);

		cat = new Pet();
		cat.setName("cat");
		PetType catType = new PetType();
		catType.setName("catType");
		cat.setType(catType);

		dog = new Pet();
		dog.setName("dog");
		PetType dogType = new PetType();
		dogType.setName("dogType");
		dog.setType(dogType);

		parrot = new Pet();
		parrot.setName("parrot");
		PetType parrotType = new PetType();
		parrotType.setName("parrotType");
		parrot.setType(parrotType);
	}

	// Double Type: Mock
	// Verification Type: State and Behavior Verification
	// Test Approach: Mockisty
	@Test
	public void TestFindOwnerReturnsOwnerIfIdIsValid() {
		Owner owner = mock(Owner.class);
		owner.setId(10);

		when(ownerRepository.findById(owner.getId())).thenReturn(owner);

		assertEquals(owner, petManager.findOwner(owner.getId()));
		verify(logger).info("find owner {}", owner.getId());
	}

	// Double Type: Mock
	// Verification Type: State and Behavior Verification
	// Test Approach: Mockisty
	@Test
	public void TestFindOwnerReturnsNullIfIdIsInValid() {
		Owner owner = mock(Owner.class);
		owner.setId(10);

		when(ownerRepository.findById(owner.getId())).thenReturn(null);

		assertNull(petManager.findOwner(owner.getId()));
		verify(logger).info("find owner {}", owner.getId());
	}

	// Double Type: Spy
	// Verification Type: Behavior Verification
	// Test Approach: Mockisty
	@Test
	public void TestNewPetAddPetToOwnerIfOwnersIdIsValid() {
		Owner owner = mock(Owner.class);
		owner.setId(10);

		petManager.newPet(owner);

		verify(logger).info("add pet for owner {}", owner.getId());
		verify(owner).addPet(isA(Pet.class));
	}

	// Double Type: Mock
	// Verification Type: State and Behavior Verification
	// Test Approach: Mockisty
	@Test
	public void TestFindPetReturnsPetIfIdIsValid() {
		Pet pet = mock(Pet.class);
		pet.setId(10);

		when(pets.get(pet.getId())).thenReturn(pet);

		assertEquals(pet, petManager.findPet(pet.getId()));
		verify(logger).info("find pet by id {}", pet.getId());
	}

	// Double Type: Mock
	// Verification Type: State and Behavior Verification
	// Test Approach: Mockisty
	@Test
	public void TestFindPetReturnsNullIfIdIsInValid() {
		Pet pet = mock(Pet.class);
		pet.setId(10);

		when(pets.get(pet.getId())).thenReturn(null);

		assertNull(petManager.findPet(pet.getId()));
		verify(logger).info("find pet by id {}", pet.getId());
	}

	// Double Type: Spy
	// Verification Type: Behavior Verification
	// Test Approach: Mockisty
	@Test
	public void TestSavePetSavesPet() {
		Pet pet = mock(Pet.class);
		pet.setId(10);

		Owner owner = mock(Owner.class);
		owner.setId(10);

		petManager.savePet(pet, owner);

		verify(logger).info("save pet {}", pet.getId());
		verify(owner).addPet(pet);
		verify(pets).save(pet);
	}

	// Double Type: Mock
	// Verification Type: State and Behavior Verification
	// Test Approach: Mockisty
	@Test
	public void TestGetOwnerPetsReturnsOwnerPets() {
		Owner owner = mock(Owner.class);
		owner.setId(10);
		List<Pet> ownerPets = Arrays.asList(dog, cat, parrot);

		when(ownerRepository.findById(owner.getId())).thenReturn(owner);
		when(owner.getPets()).thenReturn(ownerPets);

		assertEquals(ownerPets, petManager.getOwnerPets(owner.getId()));
		verify(owner).getPets();
		verify(logger).info("finding the owner's pets by id {}", owner.getId());
	}

	// Double Type: Mock
	// Verification Type: State and Behavior Verification
	// Test Approach: Mockisty
	@Test
	public void TestGetOwnerPetTypesReturnsOwnerPetTypes() {
		Pet pet = mock(Pet.class);
		pet.setId(10);
		LocalDate start = LocalDate.of(2022, 3, 15);
		LocalDate end = LocalDate.of(2022, 4, 15);
		Visit firstVisit = mock(Visit.class);
		Visit secondVisit = mock(Visit.class);
		List<Visit> petVisits = Arrays.asList(firstVisit, secondVisit);

		when(pets.get(pet.getId())).thenReturn(pet);
		when(pet.getVisitsBetween(start, end)).thenReturn(petVisits);

		assertEquals(petVisits, petManager.getVisitsBetween(pet.getId(), start, end));
		verify(pet).getVisitsBetween(start, end);
		verify(logger).info("get visits for pet {} from {} since {}", pet.getId(), start, end);
	}
}
