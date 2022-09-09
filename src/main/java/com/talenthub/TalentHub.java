package main.java.com.talenthub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import main.java.com.talenthub.components.dictionary.Diccionario;
import main.java.com.talenthub.components.dictionary.model.Persona;
import main.java.com.talenthub.components.files.FileManager;
import main.java.com.talenthub.components.huffman.Huffman;

public class TalentHub {
	
	private static Diccionario dic;
	private static FileManager fm;
	private static BufferedReader reader;

	public static void main(String[] args) throws IOException {
		
		Huffman huff = new Huffman();
		String encodedText = huff.encode("EmpresaPadreSanto3000800");
		System.out.println("Resultado del cifrado: " + encodedText);
		huff.decode(encodedText);
		
		/*dic = new Diccionario();
		fm = new FileManager();
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		titleMessage();
		cargarCsv();
		mainMenu();*/
	}
	
	private static void mainMenu() {
        String option = "";
        
        while (!option.equalsIgnoreCase("x")) {
        	System.out.println();
        	System.out.println("Ingresa una opcion:");
        	System.out.println("(1) Ingresar persona");
        	System.out.println("(2) Eliminar persona");
        	System.out.println("(3) Actualizar persona");
        	System.out.println("(4) Buscar");
        	System.out.println("(x) Salir"); 
        	
            // Reading data using readLine
    		try {
    			option = reader.readLine();
    			
    			switch(option) {
    				case "1":
    					insertarPersona();
    					break;
    				case "2":
    					eliminarPersona();
    					break;
    				case "3":
    					actualizarPersona();
    					break;
    				case "4":
    					buscarPersona();
    					break;
    				case "x": // Salir
    				case "X":
    					System.out.println();
    					System.out.println("Programa terminado...");
    					break;
    				default:
    					System.out.println();
    					System.out.println("** Opcion invalida **");
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
	}
	
	private static void cargarCsv() {
		// Select .csv file
        if (fm.selectFile()) {
        	// Pass the instructions to the dictionary
            dic.insertarInstrucciones(fm.getInstructions());
        }
	}
	
	private static void insertarPersona() {
		try {
			System.out.println();
			System.out.println("Ingresa los siguiente campos");
			String nombre, dpi, fechaNac, direccion;
			Persona persona;
			
			System.out.print("Nombre: ");
			nombre = reader.readLine();
			System.out.print("DPI: ");
			dpi = reader.readLine();
			System.out.print("Fecha de nacimiento: ");
			fechaNac = reader.readLine();
			System.out.print("Direccion: ");
			direccion = reader.readLine();
			
			persona = new Persona(nombre, dpi, fechaNac, direccion);
			dic.insertar(persona);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void eliminarPersona() {
		try {
			System.out.println();
			System.out.println("Ingresa los siguiente campos");
			String nombre, dpi;
			Persona persona;
			
			System.out.print("Nombre: ");
			nombre = reader.readLine();
			System.out.print("DPI: ");
			dpi = reader.readLine();
			
			persona = new Persona(nombre, dpi, "", "");
			dic.eliminar(persona);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void actualizarPersona() {
		try {
			System.out.println();
			System.out.println("Ingresa los siguiente campos");
			String nombre, dpi, fechaNac, direccion;
			Persona persona;
			
			System.out.print("Nombre: ");
			nombre = reader.readLine();
			System.out.print("DPI: ");
			dpi = reader.readLine();
			System.out.print("Fecha de nacimiento: ");
			fechaNac = reader.readLine();
			System.out.print("Direccion: ");
			direccion = reader.readLine();
			
			persona = new Persona(nombre, dpi, fechaNac, direccion);
			dic.actualizar(persona);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void buscarPersona() {
		try {
			System.out.println();
			System.out.print("Ingresa el nombre a buscar: ");
			String nombre = reader.readLine();
			
			List<Persona> found = dic.buscar(nombre);
			
			if (found == null || found.isEmpty()) {
				System.out.println();
				System.out.println(String.format("** El nombre %s no fue encontrado **", nombre));
			} else {
				fm.writeFile(nombre, found);
    	        
				System.out.println();
				System.out.println("Archivo de salida generado con exito");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void titleMessage() {
		System.out.println("  _______    _            _     _    _       _     ");
		System.out.println(" |__   __|  | |          | |   | |  | |     | |    ");
		System.out.println("    | | __ _| | ___ _ __ | |_  | |__| |_   _| |__  ");
		System.out.println("    | |/ _` | |/ _ \\ '_ \\| __| |  __  | | | | '_ \\ ");
		System.out.println("    | | (_| | |  __/ | | | |_  | |  | | |_| | |_) |");
		System.out.println("    |_|\\__,_|_|\\___|_| |_|\\__| |_|  |_|\\__,_|_.__/ ");
		System.out.println();
		System.out.println("¡Bienvenido/a! Selecciona un archivo csv para empezar");
	}
}
