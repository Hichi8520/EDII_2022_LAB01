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
        System.out.println("* Ingresa el nombre a buscar: ");
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
 
        // Reading data using readLine
        String name;
		try {
			name = reader.readLine();
			
			List<Persona> found = dic.buscar(name);
	        
	        for(Persona persona: found) {
	        	System.out.println(persona.toString());
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
