package main.java.com.talenthub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import main.java.com.talenthub.components.dictionary.Diccionario;
import main.java.com.talenthub.components.dictionary.model.Persona;
import main.java.com.talenthub.components.files.FileManager;
import main.java.com.talenthub.components.huffman.Huffman;

public class TalentHub {
	
	private static Diccionario dic;
	private static FileManager fm;
	private static BufferedReader reader;

	public static void main(String[] args) throws IOException {
		
		dic = new Diccionario();
		fm = new FileManager();
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		titleMessage();
		cargarJsonEmpresas();
		cargarCsv();
		mainMenu();
	}
	
	private static void cargarJsonEmpresas() {
		// Select .json file
        fm.loadJsonEmpresas();
	}
	
	private static void cargarCsv() {
		// Select .csv file
        if (fm.selectFile()) {
        	// Store the companies
            dic.insertarInstrucciones(fm.getInstructions());
        }
	}
	
	private static void mainMenu() {
        String option = "";
        
        System.out.println();
    	System.out.println("--- LISTADO DE EMPRESAS INGRESADAS ---");
    	for(int i=0; i < fm.getCompanies().size(); i++) {
    		System.out.println(String.format("(%d) %s", i+1, fm.getCompanies().get(i)));
    	}
        
        while (!option.equalsIgnoreCase("x")) {
        	
        	System.out.println();
        	System.out.println("Ingresa una empresa del proceso de reclutamiento o (x) para salir:");
        	
            // Reading data using readLine
    		try {
    			option = reader.readLine();
    			
    			if(isNumeric(option) && Integer.valueOf(option) > 0 && Integer.valueOf(option) <= fm.getCompanies().size()) {
    				System.out.println();
    	        	System.out.println("Empresa seleccionada: " + fm.getCompanies().get(Integer.valueOf(option) - 1));
    				encodeDecode(Integer.valueOf(option) - 1);
    			} else if (option.equalsIgnoreCase("x")) {
    				System.out.println();
					System.out.println("Programa terminado...");
    			} else {
    				System.out.println();
					System.out.println("** Opcion invalida **");
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
	}
	
	private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9]+");
    }
	
	private static void encodeDecode(int selectedCompanyIndex) {
		String option = "";
        
        while (!option.equalsIgnoreCase("x")) {
        	System.out.println();
        	System.out.println("Ingresa la funcion a realizar:");
        	System.out.println("(1) Codificacion");
			System.out.println("(2) Decodificacion");
        	System.out.println("(x) Salir"); 
        	
            // Reading data using readLine
    		try {
    			option = reader.readLine();
    			
    			if(isNumeric(option) && Integer.valueOf(option) > 0 && Integer.valueOf(option) <= 2) {
    				buscarPersona(selectedCompanyIndex, option);
    			} else if (option.equalsIgnoreCase("x")) {
    				System.out.println();
					System.out.println("Saliendo a menu principal...");
    			} else {
    				System.out.println();
					System.out.println("** Opcion invalida **");
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
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
			
			persona = new Persona(nombre, dpi, fechaNac, direccion, null);
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
			
			persona = new Persona(nombre, dpi, "", "", null);
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
			
			persona = new Persona(nombre, dpi, fechaNac, direccion, null);
			dic.actualizar(persona);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void buscarPersona(int companyIndex, String encodeOption) {
		try {
			System.out.println();
			System.out.print("Ingresa el dpi a buscar: ");
			String dpi = reader.readLine();
			
			Persona found = dic.buscar(dpi);
			
			if (found == null) {
				System.out.println();
				System.out.println(String.format("** El dpi %s no fue encontrado **", dpi));
			} else {
				fm.writeFile(dpi, found);
    	        
				System.out.println();
				System.out.println("Archivo de salida generado con exito");
				System.out.println(found.toString());
				
				switch(encodeOption) {
					case "1": // Encoding
						encodeSelectedCompany(companyIndex, found);
						break;
					case "2": // Decoding
						decodeSelectedCompany(companyIndex, found);
						break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void encodeSelectedCompany(int companyIndex, Persona foundPerson) {
		Huffman huff = new Huffman();
		String company = fm.getCompanies().get(companyIndex);
		String dpiToEncode = foundPerson.getDpi();
		
		if(foundPerson.getMapaEmpresaDpi().get(company) != null) {
			String encodedDpi = huff.encode(company + dpiToEncode);
			System.out.println();
			System.out.println("DPI codificado: " + encodedDpi);
			
			System.out.println();
			System.out.println("DPI codificado en la persona encontrada: " + foundPerson.getMapaEmpresaDpi().get(company));
		} else {
			System.out.println();
			System.out.println("** Esta persona no se encuentra asociada a la empresa seleccionada **");
		}
	}
	
	private static void decodeSelectedCompany(int companyIndex, Persona foundPerson) {
		Huffman huff = new Huffman();
		String company = fm.getCompanies().get(companyIndex);
		String dpiToDecode = foundPerson.getMapaEmpresaDpi()
				.get(company);
		
		if(dpiToDecode != null) {
			String decodedDpi = huff.decode(dpiToDecode);
			System.out.println();
			System.out.println("DPI decodificado: " + decodedDpi);
			
			decodedDpi = decodedDpi.substring(company.length());
			
			System.out.println();
			System.out.println("DPI original: " + decodedDpi);
		} else {
			System.out.println();
			System.out.println("** Esta persona no se encuentra asociada a la empresa seleccionada **");
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
