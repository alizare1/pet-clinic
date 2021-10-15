package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Assume;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.List;

@RunWith(Theories.class)
public class PetTest {
	private Pet pet;
	private static final int PET_ID = 1;

	@Before
	public void setup() {
		pet = new Pet();
		pet.setName("hami");
		pet.setId(PET_ID);
	}

	@DataPoints
	public static Visit[][] visits = {
		{
			new Visit().setDate(LocalDate.parse("2021-06-20")),
			new Visit().setDate(LocalDate.parse("2021-04-10"))
		},
		{
			new Visit().setDate(LocalDate.parse("2021-06-20")),
			new Visit().setDate(LocalDate.parse("2021-06-25")),
			new Visit().setDate(LocalDate.parse("2021-01-04"))
		},
		{
			new Visit().setDate(LocalDate.parse("2021-01-04"))
		}
	};

	@Theory
	public void getVisitsReturnsAllVisits(Visit[] visits) {
		Assume.assumeTrue(visits.length != 0);
		for (Visit visit : visits) {
			pet.addVisit(visit);
		}

		List<Visit> visitsRet = pet.getVisits();
		assertEquals(visitsRet.size(), visits.length, "unexpected visits length");
		for (Visit v : visits) {
			assertTrue(visitsRet.contains(v), "some visits are missing");
		}
	}

	@Theory
	public void returnedVisitsAreSortedByLatest(Visit[] visits) {
		Assume.assumeTrue(visits.length != 0);
		for (Visit visit : visits) {
			pet.addVisit(visit);
		}

		List<Visit> visitsRet = pet.getVisits();
		LocalDate prevDate = LocalDate.parse("2999-01-01");
		for (Visit visit : visitsRet) {
			assertTrue(visit.getDate().isBefore(prevDate), "returned visits are not sorted by date");
			prevDate = visit.getDate();
		}
	}
}
