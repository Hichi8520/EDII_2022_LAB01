package main.model.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public void eliminar(String nombre, String dpi) {
		if (mapaExterno.containsKey(nombre)) {
			
			Map<String, Persona> mapaInterno = mapaExterno.get(nombre);
			mapaInterno.remove(dpi);
			
			if (mapaInterno.isEmpty()) {
				mapaExterno.remove(nombre);
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
}
