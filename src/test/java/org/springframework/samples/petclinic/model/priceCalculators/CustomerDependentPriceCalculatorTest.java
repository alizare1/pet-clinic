package org.springframework.samples.petclinic.model.priceCalculators;

import org.junit.*;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;
import org.springframework.security.core.parameters.P;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerDependentPriceCalculatorTest {
	Pet pet1, pet2, pet3, pet4, pet5, pet6, pet7, pet8, pet9, pet10;
	PetType petType;

	CustomerDependentPriceCalculator customerDependentPriceCalculator;

	private static final double BASE_RARE_COEF = 1.2;
	private static double COMMON_INFANCY_COEF = 1.2;
	private static double RARE_INFANCY_COEF = 1.4;
	final double baseCharge = 5;
	final double basePricePerPet = 10;

	@Before
	public void setup() {
		petType = mock(PetType.class);

		pet1 = new Pet();
		pet1.setType(petType);
		pet1.setBirthDate(Date.from(ZonedDateTime.now().minusYears(1).toInstant()));

		pet2 = new Pet();
		pet2.setType(petType);
		pet2.setBirthDate(Date.from(ZonedDateTime.now().minusYears(5).toInstant()));

		pet3 = new Pet();
		pet3.setType(petType);
		pet3.setBirthDate(Date.from(ZonedDateTime.now().minusYears(5).toInstant()));

		pet4 = new Pet();
		pet4.setType(petType);
		pet4.setBirthDate(Date.from(ZonedDateTime.now().minusYears(5).toInstant()));

		pet5 = new Pet();
		pet5.setType(petType);
		pet5.setBirthDate(Date.from(ZonedDateTime.now().minusYears(5).toInstant()));

		pet6 = new Pet();
		pet6.setType(petType);
		pet6.setBirthDate(Date.from(ZonedDateTime.now().minusYears(5).toInstant()));

		pet7 = new Pet();
		pet7.setType(petType);
		pet7.setBirthDate(Date.from(ZonedDateTime.now().minusYears(5).toInstant()));

		pet8 = new Pet();
		pet8.setType(petType);
		pet8.setBirthDate(Date.from(ZonedDateTime.now().minusYears(5).toInstant()));

		pet9 = new Pet();
		pet9.setType(petType);
		pet9.setBirthDate(Date.from(ZonedDateTime.now().minusYears(5).toInstant()));

		pet10 = new Pet();
		pet10.setType(petType);
		pet10.setBirthDate(Date.from(ZonedDateTime.now().minusYears(5).toInstant()));

		customerDependentPriceCalculator = new CustomerDependentPriceCalculator();
	}

	@Test
	public void testCalcPriceCalculatePriceProperlyForGoldUserAndDiscountScoreLessThanMin() {
		List<Pet> pets = new ArrayList<>();

		double price = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet,UserType.GOLD);

		double expectedPrice = baseCharge;
		assertEquals(expectedPrice, price, 0.01);
	}

	@Test
	public void testCalcPriceCalculatePriceProperlyForNewUserAndDiscountScoreLessThanMin() {
		List<Pet> pets = new ArrayList<>();

		double price = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet,UserType.NEW);

		double expectedPrice = 0;
		assertEquals(expectedPrice, price, 0.01);
	}

	@Test
	public void testCalcPriceCalculatePriceProperlyForNewUserAndDiscountScoreMoreThanMin() {
		List<Pet> pets = new ArrayList<>();
		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		pets.add(pet4);
		pets.add(pet5);
		pets.add(pet6);
		pets.add(pet7);
		pets.add(pet8);
		pets.add(pet9);
		pets.add(pet10);

		when(petType.getRare()).thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false);

		double price = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet,UserType.NEW);

		double expectedPrice = (basePricePerPet * COMMON_INFANCY_COEF + 9 * basePricePerPet) * UserType.NEW.discountRate + baseCharge;
		assertEquals(expectedPrice, price, 0.01);
	}

	@Test
	public void testCalcPriceCalculatePriceProperlyForGoldUserAndDiscountScoreMoreThanMin() {
		List<Pet> pets = new ArrayList<>();
		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		pets.add(pet4);
		pets.add(pet5);
		pets.add(pet6);
		pets.add(pet7);
		pets.add(pet8);
		pets.add(pet9);
		pets.add(pet10);

		when(petType.getRare()).thenReturn(true)
			.thenReturn(true)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false)
			.thenReturn(false);

		double price = customerDependentPriceCalculator.calcPrice(pets, baseCharge, basePricePerPet,UserType.GOLD);

		double expectedPrice = ((basePricePerPet * BASE_RARE_COEF * RARE_INFANCY_COEF + basePricePerPet * BASE_RARE_COEF + 8 * basePricePerPet) + baseCharge) * UserType.GOLD.discountRate;

		assertEquals(expectedPrice, price, 0.01);
	}
}
