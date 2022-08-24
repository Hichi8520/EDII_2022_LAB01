package main.java.com.talenthub.components.dictionary.model;

public class Instruction {

	private final String action;
	private final Persona persona;
	
	public Instruction(String action, Persona persona) {
		super();
		this.action = action;
		this.persona = persona;
	}

	public String getAction() {
		return action;
	}

	public Persona getPersona() {
		return persona;
	}
}
