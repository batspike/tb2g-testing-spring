package org.springframework.samples.petclinic.sfg;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HearingInterpreterTest {
	HearingInterpreter hearingInterpreter;
	
	@Before
	public void setUp() throws Exception {
		hearingInterpreter = new HearingInterpreter(new LaurelWordProducer());
	}

	@Test
	public void testWhatIheard() {
		String word = hearingInterpreter.whatIheard();
		assertEquals("Laurel!", word);
	}

}
