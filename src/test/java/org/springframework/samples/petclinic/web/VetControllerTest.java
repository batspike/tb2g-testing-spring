package org.springframework.samples.petclinic.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// for Spring MVC Testing
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class VetControllerTest {

	@Mock
	ClinicService clinicService;
	
	@Mock
	Map<String,Object> model;
	
	@InjectMocks
	VetController controller;
	
	List<Vet> vetsList = new ArrayList<>();
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		//Given
		vetsList.add(new Vet());
		given(clinicService.findVets()).willReturn(vetsList);
		
		//setup mockMvc for VetController
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	void testControllerShowVetList() throws Exception {
		mockMvc.perform(get("/vets.html"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("vets"))
			.andExpect(view().name("vets/vetList"));
	}

	@Test
	void testShowVetList() {
		//When
		String view = controller.showVetList(model);
		
		//Then
		then(clinicService).should().findVets();
		then(model).should().put(anyString(),any());
		assertThat("vets/VetList").isEqualToIgnoringCase(view);
	}

	@Test
	void testShowResourcesVetList() {
		//When
		Vets vets = controller.showResourcesVetList();
		
		//Then
		then(clinicService).should().findVets();
		assertThat(vets.getVetList()).hasSize(1);
	}

}
