package org.springframework.samples.petclinic.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations= {"classpath:spring/mvc-test-config.xml","classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {
	
	@Autowired
	OwnerController controller;
	
	@Autowired
	ClinicService clinicService;
	
	@Mock
	Map<String,Object> model;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		//setup mockMvc for OwnerController
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	void processFindFormTest() throws Exception {
		mockMvc.perform(get("/owners").param("firstName", "Sam").param("lastName", "Lau")) //param will bind attribute to owner object
				.andExpect(status().isOk())
				.andExpect(view().name("owners/findOwners"));
	}
	
	@Test
	void initCreationFormTest() throws Exception {
		mockMvc.perform(get("/owners/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("owner"))
		.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}
	
	@Test
	void initCreationFormUnitTest() {
		//When
		String view = controller.initCreationForm(model);
		
		//Then
		then(model).should().put(anyString(),any());
		assertThat("owners/createOrUpdateOwnerForm").isEqualToIgnoringCase(view);
	}

}
