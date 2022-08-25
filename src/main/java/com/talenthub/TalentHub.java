package main.java.com.talenthub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import main.java.com.talenthub.components.dictionary.Diccionario;
import main.java.com.talenthub.components.dictionary.model.Persona;
import main.java.com.talenthub.components.files.FileManager;

public class TalentHub {

	public static void main(String[] args) {
        
        // Select .csv file
        FileManager fm = new FileManager();
        fm.selectFile();
        
        // Pass the instructions to the dictionary
        Diccionario dic = new Diccionario();
        dic.insertarInstrucciones(fm.getInstructions());
        
        // Search by user input
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        String name = "";
        
        while (!name.equalsIgnoreCase("x")) {
        	System.out.println();
        	System.out.println("* Ingresa el nombre a buscar: (o ingresa X para salir)");
        	 
            // Reading data using readLine
    		try {
    			name = reader.readLine();
    			
    			if (!name.equalsIgnoreCase("x")) {
    				
    				List<Persona> found = dic.buscar(name);
    				
    				if (found == null || found.isEmpty()) {
    					System.out.println(String.format("**El nombre %s no fue encontrado", name));
    				} else {
    					fm.writeFile(name, found);
            	        
            	        for(Persona persona: found) {
            	        	System.out.println(persona.toString());
            	        }
    				}
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
        
        System.out.println("Programa terminado...");
	}

}
