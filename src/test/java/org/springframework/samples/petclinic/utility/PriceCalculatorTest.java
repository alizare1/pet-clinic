package org.springframework.samples.petclinic.utility;

import org.junit.Before;
import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PriceCalculatorTest {
	private static int INFANT_YEARS = 2;
	private static double RARE_INFANCY_COEF = 1.4;
	private static double BASE_RARE_COEF = 1.2;
	private static int DISCOUNT_MIN_SCORE = 10;
	private static int DISCOUNT_PRE_VISIT = 2;

	private List<Pet> pets;
	private Pet pet;

	@Before
	public void setup() {
		pets = new ArrayList<>();
		pet = new Pet();
	}

	@Test
	public void Infant_pet_with_no_visit_price_is_correct() {
		pet.setBirthDate(LocalDate.now().minusYears(1));
		pets.add(pet);
		assertEquals(10 * BASE_RARE_COEF * RARE_INFANCY_COEF, PriceCalculator.calcPrice(pets, 10, 10));
	}

	@Test
	public void Old_pet_with_no_visit_price_is_correct() {
		pet.setBirthDate(LocalDate.now().minusYears(3));
		pets.add(pet);
		assertEquals(10 * BASE_RARE_COEF, PriceCalculator.calcPrice(pets, 10, 10));
	}

	@Test
	public void Infant_pets_with_visit_within_100_days_is_correct() {
		for (int i = 0; i < 5; i++) {
			Pet p = new Pet();
			p.setBirthDate(LocalDate.now().minusYears(2));
			Visit visit = new Visit();
			visit.setDate(LocalDate.now().minusDays(10));
			p.addVisit(visit);
			pets.add(p);
		}
		assertEquals(161.2, PriceCalculator.calcPrice(pets, 10, 10));
	}

	@Test
	public void Old_pets_with_visit_within_100_days_is_correct() {
		for (int i = 0; i < 10; i++) {
			Pet p = new Pet();
			p.setBirthDate(LocalDate.now().minusYears(3));
			Visit visit = new Visit();
			visit.setDate(LocalDate.now().minusDays(10));
			p.addVisit(visit);
			pets.add(p);
		}
		assertEquals(238, PriceCalculator.calcPrice(pets, 10, 10));
	}

	@Test
	public void Infant_pets_with_visit_more_than_100_days_is_correct() {
		for (int i = 0; i < 5; i++) {
			Pet p = new Pet();
			p.setBirthDate(LocalDate.now().minusYears(1));
			Visit visit = new Visit();
			visit.setDate(LocalDate.now().minusDays(101));
			p.addVisit(visit);
			pets.add(p);
		}
		assertEquals(171.2, PriceCalculator.calcPrice(pets, 10, 10));
	}

	@Test
	public void Mixed_pets_with_mixed_visits_is_correct() {
		for (int i = 0; i < 4; i++) {
			Pet p = new Pet();
			p.setBirthDate(LocalDate.now().minusYears(1));
			Visit visit = new Visit();
			visit.setDate(LocalDate.now().minusDays(99+i));
			p.addVisit(visit);
			pets.add(p);
		}
		for (int i = 0; i < 4; i++) {
			Pet p = new Pet();
			p.setBirthDate(LocalDate.now().minusYears(3));
			Visit visit = new Visit();
			visit.setDate(LocalDate.now().minusDays(98+i));
			p.addVisit(visit);
			pets.add(p);
		}
		assertEquals(817.5999999999999, PriceCalculator.calcPrice(pets, 10, 10));
	}

	@Test
	public void CalcPrice_returns_zero_when_pet_list_is_empty() {
		assertEquals(0, PriceCalculator.calcPrice(pets, 10, 10));
	}
}
