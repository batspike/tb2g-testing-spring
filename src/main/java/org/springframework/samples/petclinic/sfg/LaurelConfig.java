package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile({"laurel", "default"})
@Configuration
public class LaurelConfig {
	@Bean
	@Primary
	LaurelWordProducer laurelWordProducer() {
		return new LaurelWordProducer();
	}
}
