package main.java.com.talenthub.components.dictionary;

import java.util.ArrayList;
import java.util.Collections;
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
	
	public void insertar(Persona persona, boolean printMessage) {
		// Insert only if it isn't already in the map
		if (!mapaExterno.containsKey(persona.getDpi())) {
			mapaExterno.put(persona.getDpi(), persona);
			if (printMessage) {
				System.out.println();
				System.out.println("Candidato ingresado exitosamente");
			}
		} else {
			if (printMessage) {
				System.out.println();
				System.out.println("** Este candidato ya existe en el sistema");
			}
		}
	}
	
	public void eliminar(Persona persona, boolean printMessage) {
		mapaExterno.remove(persona.getDpi());
		if (printMessage) {
			System.out.println();
			System.out.println("Candidato eliminado exitosamente");
		}
	}
	
	public void actualizar(Persona persona, boolean printMessage) {
		mapaExterno.replace(persona.getDpi(), persona);
		if (printMessage) {
			System.out.println();
			System.out.println("Candidato actualizado exitosamente");
		}
	}
	
	public Persona buscar(String dpi) {
		return mapaExterno.get(dpi);
	}
	
	public void insertarInstrucciones(List<Instruction> instructions) {
		if (instructions != null && !instructions.isEmpty()) {
			for	(Instruction instruction: instructions) {
				
				switch (instruction.getAction()) {
					case "INSERT":
						insertar(instruction.getPersona(), false);
						break;
					case "DELETE":
						eliminar(instruction.getPersona(), false);
						break;
					case "PATCH":
						actualizar(instruction.getPersona(), false);
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
	
	public void listarPersonas() {
		List<Persona> employeeById = new ArrayList<>(mapaExterno.values());
		Collections.sort(employeeById);
		
		System.out.println();
		System.out.println("NOMBRE | DPI | RECLUTADOR | FECHA NAC | DIRECCION");
		for (Persona item : employeeById) {
	        System.out.println(item.toSimpleString());
	    }
	}
}
