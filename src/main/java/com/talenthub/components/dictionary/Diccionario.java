package main.java.com.talenthub.components.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.com.talenthub.components.dictionary.model.Instruction;
import main.java.com.talenthub.components.dictionary.model.Persona;

public class Diccionario {

	private final Map<String, Map<String, Persona>> mapaExterno;

	public Diccionario() {
		super();
		this.mapaExterno = new HashMap<>();
	}
	
	public void insertar(Persona persona) {
		if (mapaExterno.containsKey(persona.getNombre())) {
			
			Map<String, Persona> mapaInterno = mapaExterno.get(persona.getNombre());
			mapaInterno.put(persona.getDpi(), persona);
			mapaExterno.put(persona.getNombre(), mapaInterno);
			
		} else {
			Map<String, Persona> mapaInterno = new HashMap<>();
			mapaInterno.put(persona.getDpi(), persona);
			mapaExterno.put(persona.getNombre(), mapaInterno);
		}
	}
	
	public void eliminar(Persona persona) {
		if (mapaExterno.containsKey(persona.getNombre())) {
			
			Map<String, Persona> mapaInterno = mapaExterno.get(persona.getNombre());
			mapaInterno.remove(persona.getDpi());
			
			if (mapaInterno.isEmpty()) {
				mapaExterno.remove(persona.getNombre());
			}
		}
	}
	
	public void actualizar(Persona persona) {
		if (mapaExterno.containsKey(persona.getNombre())) {
			
			Map<String, Persona> mapaInterno = mapaExterno.get(persona.getNombre());
			mapaInterno.replace(persona.getDpi(), persona);
			mapaExterno.replace(persona.getNombre(), mapaInterno);
		}
	}
	
	public List<Persona> buscar(String nombre) {
		if (mapaExterno.containsKey(nombre)) {
			
			Map<String, Persona> mapaInterno = mapaExterno.get(nombre);
			List<Persona> returnList = new ArrayList<>();
			
			for(Entry<String, Persona> entry: mapaInterno.entrySet()) {
				returnList.add(entry.getValue());
		    }

			return returnList;
		} else {
			return null;
		}
	}
	
	public void insertarInstrucciones(List<Instruction> instructions) {
		if (instructions != null || !instructions.isEmpty()) {
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
			System.out.println("Instrucciones ingresadas exitosamente");
			
		} else System.out.println("No hay instrucciones para procesar");
	}
}
