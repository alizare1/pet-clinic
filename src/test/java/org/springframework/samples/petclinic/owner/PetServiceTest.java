package org.springframework.samples.petclinic.owner;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class PetServiceTest {
	private OwnerRepository ownerRepository;
	private final PetService petService;

	private static Pet cat, dog, rabbit, parrot;

	private final int id;
	private final Pet expectedPet;

	public PetServiceTest(int id, Pet pet) {
		this.id = id;
		this.expectedPet = pet;

		PetTimedCache cache = mock(PetTimedCache.class);
		Logger logger = LoggerFactory.getLogger("");
		petService = new PetService(cache, ownerRepository, logger);

		when(cache.get(2)).thenReturn(dog);
		when(cache.get(17)).thenReturn(dog);
		when(cache.get(29)).thenReturn(rabbit);
		when(cache.get(6)).thenReturn(parrot);
	}

	@BeforeEach
	void Setup() {
		cat = new Pet();
		cat.setName("cat");
		cat.setId(1);

		dog = new Pet();
		dog.setName("dog");
		cat.setId(2);

		rabbit = new Pet();
		rabbit.setName("rabbit");
		cat.setId(3);

		parrot = new Pet();
		parrot.setName("parrot");
		cat.setId(4);
	}

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList (new Object [][]{
			{1, cat}, {2, dog}, {3, rabbit}, {4, parrot}
		});
	}

	@Test
	public void findPetShouldReturnPetProperly() {
		assertEquals(expectedPet, petService.findPet(id), "unexpected pet");
	}
}
