package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {
	@Mock
    private PetRepository petRepository;
	@Mock
    private VetRepository vetRepository;
	@Mock
    private OwnerRepository ownerRepository;
	@Mock
    private VisitRepository visitRepository;

	@InjectMocks
	ClinicServiceImpl service;
	
	@Test
	void testFindPetTypes() {
		//Given
		List<PetType> petTypeList = new ArrayList<>();
		given(petRepository.findPetTypes()).willReturn(petTypeList);
		
		//When
		Collection<PetType> returnPetTypes = service.findPetTypes();
		
		//Then
		then(petRepository).should().findPetTypes();
		assertThat(returnPetTypes).isNotNull();
	}

}
