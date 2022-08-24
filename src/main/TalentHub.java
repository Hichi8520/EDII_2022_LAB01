package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.model.dictionary.Diccionario;
import main.model.dictionary.Key;
import main.model.dictionary.Persona;
import main.model.tree.Arbol;

public class TalentHub {

	public static void main(String[] args) {
		
		Persona persona1 = new Persona("Luis", "30006565", "15/05/1999", "Villa Nueva");
		Persona persona2 = new Persona("Alejandro", "30006565", "15/05/1997", "Villa Nueva 1");
		Persona persona3 = new Persona("Alejandro", "30006566", "15/05/1998", "Villa Nueva 2");
		Persona persona4 = new Persona("Alejandro", "30006567", "15/05/1999", "Villa Nueva 3");
		
		Diccionario dicc = new Diccionario();
		dicc.insertar(persona1);
		dicc.insertar(persona2);
		dicc.insertar(persona3);
		dicc.insertar(persona4);
		dicc.eliminar("Alejandro", "30006566");
		dicc.actualizar(new Persona("Alejandro", "30006567", "15/05/1999000", "Villa Nueva 3000"));
        
        List<Persona> found = dicc.buscar("Alejandro");
        
        for(Persona persona: found) {
        	System.out.println(persona.toString());
        }
	}

}
