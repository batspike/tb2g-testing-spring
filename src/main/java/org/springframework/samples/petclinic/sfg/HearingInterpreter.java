package org.springframework.samples.petclinic.sfg;

public class HearingInterpreter {
	private final WordProducer wordProducer;

	public HearingInterpreter(WordProducer wordProducer) {
		this.wordProducer = wordProducer;
	}
	
	public String whatIheard() {
		String word = wordProducer.getWord();
		System.out.println(word);
		return word;
	}
}
