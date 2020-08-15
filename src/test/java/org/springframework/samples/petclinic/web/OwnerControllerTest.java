package org.springframework.samples.petclinic.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Map;

import org.assertj.core.util.Lists;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class) //needed for @Mock and @Captor
@SpringJUnitWebConfig(locations= {"classpath:spring/mvc-test-config.xml","classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {
	
	@Autowired
	OwnerController controller;
	
	@Autowired
	ClinicService clinicService;
	
	@Mock
	Map<String,Object> model;
	
	MockMvc mockMvc;
	
	@Captor
	ArgumentCaptor<String> stringArgumentCaptor;
	
	@BeforeEach
	void setUp() throws Exception {
		//setup mockMvc for OwnerController
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@AfterEach
	void tearDown() {
		//we need to reset clinicService as this is a autowired object from Spring context.
		//which means every call with clinicService is accumulated across all the test cases.
		reset(clinicService);
	}
	
	@Test
	void processCreationFormInValidTest() throws Exception {
		//Given
		
		//When-Then
		mockMvc.perform(post("/owners/new")
							.param("firstname", "Carlos") //field-name is not case-sensitive
							.param("lastName", "Santan")
							.param("city", "Miami"))
				//.andDo(print()) // for more detail information 
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("owner"))
				.andExpect(model().attributeHasFieldErrors("owner", "address"))
				.andExpect(model().attributeHasFieldErrors("owner", "telephone"))
				.andExpect(view().name("owners/createOrUpdateOwnerForm"));
		
		then(clinicService).should(times(0)).saveOwner(any());
	}
	
	@Test
	void processCreationFormValidTest() throws Exception {
		//Given
		
		//When-Then
		mockMvc.perform(post("/owners/new")
				.param("firstName", "Carlos")
				.param("lastName", "Santan")
				.param("address", "11 Maria St")
				.param("city", "Miami")
				.param("telephone", "123456789"))
		//.andDo(print()) // for more detail information 
		.andExpect(status().is3xxRedirection())
		.andExpect(model().attributeHasNoErrors("owner"))
		.andReturn().toString().startsWith("redirect:/owners");
		
		then(clinicService).should().saveOwner(any());
	}
	
	@Test
	void processFindFormFoundManyTest() throws Exception {
		//Given
		given(clinicService.findOwnerByLastName(anyString())).willReturn(Lists.newArrayList(new Owner(), new Owner()));
		
		//When-Then
		mockMvc.perform(get("/owners")) //no param mean owner.lastName is null
				.andExpect(status().isOk())
				.andExpect(view().name("owners/ownersList"));
		
		then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());
		assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase(""); //test for parameterless GET
	}
	
	@Test
	void processFindFormFoundOneTest() throws Exception {
		//Given
		Owner owner = new Owner();
		owner.setId(1);
		given(clinicService.findOwnerByLastName(anyString())).willReturn(Lists.newArrayList(owner));
		
		//When-Then
		mockMvc.perform(get("/owners").param("lastName", "AnyName"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/" + owner.getId()));
		
		then(clinicService).should().findOwnerByLastName("AnyName");
	}
	
	@Test
	void processFindFormNotFoundTest() throws Exception {
		mockMvc.perform(get("/owners").param("lastName", "Lau")) //param will bind attribute to owner object
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
