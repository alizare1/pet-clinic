package org.springframework.samples.petclinic.owner;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = PetController.class,
	includeFilters = {
		@ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetService.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = LoggerConfig.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetTimedCache.class, type = FilterType.ASSIGNABLE_TYPE),
	}
	)
class PetControllerTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PetRepository petRepository;
	@MockBean
	private OwnerRepository ownerRepository;
//	@MockBean
//	private PetService petService;

	private static final int OWNER_ID = 1;
	private static final int PET_ID = 1;

	@BeforeEach
	void setup() {
		PetType cat = new PetType();
		cat.setId(3);
		cat.setName("cat");
		Pet pet = new Pet();
		pet.setId(10);
		given(petRepository.findPetTypes()).willReturn(Lists.newArrayList(cat));
		given(ownerRepository.findById(OWNER_ID)).willReturn(new Owner());
		given(petRepository.findById(PET_ID)).willReturn(pet);
//		given(petService.newPet(any())).willReturn(pet);
	}

	@Test
	public void initCreationFrom_works_correctly() throws Exception {
		mockMvc.perform(get("/owners/1/pets/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void processCreationForm_works_correctly() throws Exception {
		mockMvc.perform(post("/owners/1/pets/new")
			.param("name", "Hamid")
			.param("type", "cat")
			.param("birthDate", "2000-10-10"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	public void processCreationForm_with_error_returns_update_view() throws Exception {
		mockMvc.perform(post("/owners/1/pets/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void initUpdateForm_works_correctly() throws Exception {
		mockMvc.perform(get("/owners/1/pets/1/edit"))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	public void processUpdateForm_works_correctly() throws Exception {
		mockMvc.perform(post("/owners/1/pets/1/edit")
			.param("name", "Hamid")
			.param("type", "cat")
			.param("birthDate", "2000-10-10"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	public void processUpdateForm_with_error_returns_update_view() throws Exception {
		mockMvc.perform(post("/owners/1/pets/1/edit"))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}
}
