package org.springframework.samples.petclinic.sfg;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

@ActiveProfiles("yanny")
@RunWith(SpringRunner.class) //from Spring freamework
@ContextConfiguration(classes={BaseConfig.class, YannyConfig.class})
public class HearingInterpreterYannyTest {
	@Autowired
	HearingInterpreter hearingInterpreter;
	
	@Test
	public void testWhatIheard() {
		String word = hearingInterpreter.whatIheard();
		assertEquals("Yanny!", word);
	}

}
