package org.springframework.samples.petclinic.sfg.junit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles("externalized")
@TestPropertySource("classpath:external.properties")
@SpringJUnitConfig(classes= ExternalPropertiesTest.TestConfig.class)
class ExternalPropertiesTest {
	
	@Configuration
	@ComponentScan("org.springframework.samples.petclinic.sfg")
	static class TestConfig {}

	@Autowired
	HearingInterpreter hearingInterpreter;

	@Test
	void testWhatIheard() {
		String word = hearingInterpreter.whatIheard();
		
		assertEquals("EXTERNAL", word);
	}

}
