package org.springframework.samples.petclinic.model.priceCalculators;

import org.junit.*;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimplePriceCalculatorTest {
	Pet normalPet;
	Pet rarePet;

	PetType normalPetType;
	PetType rarePetType;

	SimplePriceCalculator simplePriceCalculator;

	private static final double BASE_RARE_COEF = 1.2;
	final double baseCharge = 5;
	final double basePricePerPet = 10;

	@Before
	public void setup() {
		normalPet = new Pet();
		normalPetType = mock(PetType.class);
		normalPet.setType(normalPetType);

		rarePet = new Pet();
		rarePetType = mock(PetType.class);
		rarePet.setType(rarePetType);

		simplePriceCalculator = new SimplePriceCalculator();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testCalcPriceCalculatePriceProperlyForEmptyPetsAndOldUser() {
		List<Pet> pets = new ArrayList<>();

		double price = new SimplePriceCalculator().calcPrice(pets, baseCharge, basePricePerPet, UserType.GOLD);

		double expectedPrice = baseCharge;
		assertEquals(expectedPrice, price, 0.01);
	}

	@Test
	public void testCalcPriceCalculatePriceProperlyForEmptyPetsAndNewUser() {
		List<Pet> pets = new ArrayList<>();

		double price = new SimplePriceCalculator().calcPrice(pets, baseCharge, basePricePerPet, UserType.NEW);

		double expectedPrice = baseCharge * UserType.NEW.discountRate;
		assertEquals(expectedPrice, price, 0.01);
	}

	@Test
	public void testCalcPriceCalculatePriceProperlyForNotRarePetsAndOldUser() {
		List<Pet> pets = new ArrayList<>();
		pets.add(normalPet);

		when(normalPetType.getRare()).thenReturn(false);

		double price = new SimplePriceCalculator().calcPrice(pets, baseCharge, basePricePerPet, UserType.GOLD);

		double expectedPrice = baseCharge + basePricePerPet;
		assertEquals(expectedPrice, price, 0.01);
	}

	@Test
	public void testCalcPriceCalculatePriceProperlyForNotRarePetsAndNewUser() {
		List<Pet> pets = new ArrayList<>();
		pets.add(normalPet);

		when(normalPetType.getRare()).thenReturn(false);

		double price = new SimplePriceCalculator().calcPrice(pets, baseCharge, basePricePerPet, UserType.NEW);

		double expectedPrice = baseCharge + basePricePerPet;
		expectedPrice *= UserType.NEW.discountRate;
		assertEquals(expectedPrice, price, 0.01);
	}

	@Test
	public void testCalcPriceCalculatePriceProperlyForRarePetsAndOldUser() {
		List<Pet> pets = new ArrayList<>();
		pets.add(normalPet);

		when(normalPetType.getRare()).thenReturn(true);

		double price = new SimplePriceCalculator().calcPrice(pets, baseCharge, basePricePerPet, UserType.GOLD);

		double expectedPrice = baseCharge + basePricePerPet * BASE_RARE_COEF;
		assertEquals(expectedPrice, price, 0.01);
	}

	@Test
	public void testCalcPriceCalculatePriceProperlyForRarePetsAndNewUser() {
		List<Pet> pets = new ArrayList<>();
		pets.add(rarePet);

		when(rarePetType.getRare()).thenReturn(true);

		double price = new SimplePriceCalculator().calcPrice(pets, baseCharge, basePricePerPet, UserType.NEW);

		double expectedPrice = baseCharge + basePricePerPet * BASE_RARE_COEF;
		expectedPrice *= UserType.NEW.discountRate;
		assertEquals(expectedPrice, price, 0.01);
	}

	@Test
	public void testCalcPriceCalculatePriceProperlyForMultiplePets() {
		List<Pet> pets = new ArrayList<>();
		pets.add(normalPet);
		pets.add(rarePet);

		when(rarePetType.getRare()).thenReturn(false);
		when(rarePetType.getRare()).thenReturn(true);

		double price = new SimplePriceCalculator().calcPrice(pets, baseCharge, basePricePerPet, UserType.GOLD);

		double expectedPrice = baseCharge + basePricePerPet + basePricePerPet * BASE_RARE_COEF;
		assertEquals(expectedPrice, price, 0.01);
	}
}
