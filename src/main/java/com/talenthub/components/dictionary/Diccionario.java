package main.java.com.talenthub.components.dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.java.com.talenthub.components.dictionary.model.Instruction;
import main.java.com.talenthub.components.dictionary.model.Persona;

public class Diccionario {

	private final Map<String, Persona> mapaExterno;

	public Diccionario() {
		super();
		this.mapaExterno = new HashMap<>();
	}
	
	public void insertar(Persona persona) {
		// Insert only if it isn't already in the map
		if (!mapaExterno.containsKey(persona.getDpi())) {
			mapaExterno.put(persona.getDpi(), persona);
		}
	}
	
	public void eliminar(Persona persona) {
		mapaExterno.remove(persona.getDpi());
	}
	
	public void actualizar(Persona persona) {
		mapaExterno.replace(persona.getDpi(), persona);
	}
	
	public Persona buscar(String dpi) {
		return mapaExterno.get(dpi);
	}
	
	public void insertarInstrucciones(List<Instruction> instructions) {
		if (instructions != null && !instructions.isEmpty()) {
			for	(Instruction instruction: instructions) {
				
				switch (instruction.getAction()) {
					case "INSERT":
						insertar(instruction.getPersona());
						break;
					case "DELETE":
						eliminar(instruction.getPersona());
						break;
					case "PATCH":
						actualizar(instruction.getPersona());
						break;
				}
			}
			System.out.println();
			System.out.println("Instrucciones ingresadas exitosamente");
			
		} else {
			System.out.println();
			System.out.println("** No hay instrucciones para procesar **");
		}
	}
}
