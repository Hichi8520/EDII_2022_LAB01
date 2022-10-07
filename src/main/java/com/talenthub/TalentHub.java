package main.java.com.talenthub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import main.java.com.talenthub.components.dictionary.Diccionario;
import main.java.com.talenthub.components.dictionary.model.Persona;
import main.java.com.talenthub.components.files.FileManager;
import main.java.com.talenthub.components.huffman.Huffman;
import main.java.com.talenthub.components.trans.Trans;

public class TalentHub {
	
	private static Diccionario dic;
	private static FileManager fm;
	private static BufferedReader reader;

	public static void main(String[] args) throws IOException {
		
		dic = new Diccionario();
		fm = new FileManager();
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		/*
		String message = "01/06/22, 2:11 AM - +502 5767 9807: Et repellendus sint vero aut.\r\n"
				+ "01/08/22, 11:22 AM - +502 5767 9807: Quod fugiat vel quaerat qui incidunt incidunt odit.\r\n"
				+ "01/16/22, 9:23 PM - +502 5767 9807: Repudiandae doloremque aut iure eaque.\r\n"
				+ "01/22/22, 1:09 AM - +502 5617 7422: Et explicabo inventore quo error tempora quia aut sint.\r\n"
				+ "02/07/22, 5:58 PM - +502 5767 9807: Necessitatibus eaque nulla qui.\r\n"
				+ "02/08/22, 3:25 AM - +502 5617 7422: Dolorum reiciendis inventore voluptate necessitatibus qui ut corrupti.\r\n"
				+ "02/25/22, 6:28 AM - +502 5617 7422: Architecto adipisci qui quos ratione asperiores vel.\r\n"
				+ "03/05/22, 10:51 AM - +502 5617 7422: Labore eligendi nemo ipsa.\r\n"
				+ "03/23/22, 11:42 AM - +502 5617 7422: Maiores consequatur enim et.\r\n"
				+ "03/24/22, 8:22 AM - +502 5617 7422: Numquam voluptates voluptatem.\r\n"
				+ "03/24/22, 7:39 PM - +502 5617 7422: Inventore beatae doloremque qui et perspiciatis.\r\n"
				+ "03/26/22, 4:53 AM - +502 5617 7422: Qui voluptates eius ad architecto quam aut.\r\n"
				+ "04/02/22, 10:08 AM - +502 5767 9807: Quo illum sunt.\r\n"
				+ "04/19/22, 1:38 AM - +502 5767 9807: Nobis harum aliquid voluptas assumenda perferendis fugit.\r\n"
				+ "05/11/22, 11:52 AM - +502 5767 9807: Vel tenetur voluptatum.\r\n"
				+ "05/14/22, 11:38 PM - +502 5767 9807: Aut distinctio quibusdam consequatur tempora.\r\n"
				+ "05/18/22, 6:19 PM - +502 5767 9807: Ea quis ipsum ad ut.\r\n"
				+ "05/29/22, 7:14 PM - +502 5617 7422: Quibusdam et nihil a sed consectetur doloremque.\r\n"
				+ "06/12/22, 9:38 PM - +502 5617 7422: Pariatur aperiam maxime quidem praesentium enim.\r\n"
				+ "06/20/22, 3:22 AM - +502 5617 7422: Molestiae ea tempore.\r\n"
				+ "06/24/22, 11:07 PM - +502 5617 7422: Inventore ullam nesciunt sit.\r\n"
				+ "07/08/22, 9:22 PM - +502 5767 9807: Eos perspiciatis rerum.\r\n"
				+ "08/22/22, 3:04 PM - +502 5617 7422: Cupiditate nesciunt et omnis dolorem enim vel cum.\r\n"
				+ "08/26/22, 9:57 AM - +502 5617 7422: Reprehenderit quo fugiat quam.\r\n"
				+ "08/30/22, 10:47 PM - +502 5617 7422: Omnis velit blanditiis reprehenderit itaque.\r\n"
				+ "09/09/22, 11:04 PM - +502 5767 9807: Perspiciatis rerum nam dolores.\r\n"
				+ "09/13/22, 8:32 AM - +502 5617 7422: Provident deserunt aliquam nesciunt officia qui provident rerum minus facilis.\r\n"
				+ "09/15/22, 12:03 PM - +502 5617 7422: Aspernatur ut suscipit.\r\n"
				+ "10/18/22, 7:17 AM - +502 5767 9807: Mollitia molestias ea quo blanditiis sunt placeat.\r\n"
				+ "10/23/22, 10:53 PM - +502 5767 9807: Nihil distinctio consectetur.\r\n"
				+ "10/29/22, 8:44 PM - +502 5617 7422: Magnam natus veniam voluptatibus doloremque et unde sint.\r\n"
				+ "11/10/22, 7:59 AM - +502 5617 7422: Mollitia sit ipsam dolores inventore voluptate.\r\n"
				+ "11/13/22, 5:50 AM - +502 5617 7422: Facilis quis suscipit.\r\n"
				+ "11/17/22, 8:21 PM - +502 5617 7422: Ab eum nesciunt quis omnis totam et autem.\r\n"
				+ "11/23/22, 2:46 AM - +502 5767 9807: Harum esse ut asperiores quis quaerat repudiandae.\r\n"
				+ "11/27/22, 2:08 AM - +502 5617 7422: Similique incidunt optio repellendus voluptate odit.\r\n"
				+ "12/09/22, 12:12 AM - +502 5767 9807: Corrupti autem rem illum maiores repellat velit culpa.\r\n"
				+ "12/10/22, 4:48 AM - +502 5767 9807: Rerum illo quam qui.";
		
		Trans trans = new Trans();
		
		String ciphered = trans.cipher("8895433843822", message);
		
		System.out.println(ciphered);
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println(trans.decipher("8895433843822", ciphered));
		*/
		
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
				System.out.println();
				System.out.println(found.toString());
				
				switch(encodeOption) {
					case "1": // Encoding
						encodeSelectedCompany(companyIndex, found);
						break;
					case "2": // Decoding
						String decodedDpi = decodeSelectedCompany(companyIndex, found);
						if(decodedDpi != null) {
							compressCipher(decodedDpi);
						}
						break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void compressCipher(String decodedDpi) {
		String option = "";
        
        while (!option.equalsIgnoreCase("x")) {
        	System.out.println();
        	System.out.println("Ingresa la funcion a realizar:");
        	System.out.println("(1) Compresion / Descompresion");
			System.out.println("(2) Cifrado / Descifrado");
        	System.out.println("(x) Salir"); 
        	
            // Reading data using readLine
    		try {
    			option = reader.readLine();
    			
    			if(isNumeric(option) && Integer.valueOf(option) > 0 && Integer.valueOf(option) <= 2) {
    				if(option.equals("1")) {
    					fm.compressFilesByDpi(decodedDpi);
						descomprimirCarta(decodedDpi, fm.getCompressedCount());
    				} else {
    					fm.cipherFilesByDpi(decodedDpi);
    					descifrarConv(decodedDpi, fm.getCipheredCount());
    				}
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
	
	private static void descomprimirCarta(String dpi, Integer cantCartas) {
		if(cantCartas > 0) {
			try {
				System.out.println();
				System.out.println("Cartas disponibles para este usuario: " + cantCartas);
				System.out.println();
				System.out.print("Ingresa la carta que quieres descomprimir: ");
				String numCarta = reader.readLine();
				
				if(isNumeric(numCarta) && Integer.valueOf(numCarta) > 0 && Integer.valueOf(numCarta) <= cantCartas) {
					
					fm.decompressFileByDpi(dpi, Integer.valueOf(numCarta));
					
				} else {
					System.out.println();
					System.out.println("** Opcion invalida");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			System.out.println();
			System.out.println("** El usuario no tiene cartas disponibles");
		}
	}
	
	private static void descifrarConv(String dpi, Integer cantConv) {
		if(cantConv > 0) {
			try {
				System.out.println();
				System.out.println("Conversaciones disponibles para este usuario: " + cantConv);
				System.out.println();
				System.out.print("Ingresa la conversacion que quieres descifrar: ");
				String numConv = reader.readLine();
				
				if(isNumeric(numConv) && Integer.valueOf(numConv) > 0 && Integer.valueOf(numConv) <= cantConv) {
					
					fm.decipherFileByDpi(dpi, Integer.valueOf(numConv));
					
				} else {
					System.out.println();
					System.out.println("** Opcion invalida");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			System.out.println();
			System.out.println("** El usuario no tiene conversaciones disponibles");
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
	
	private static String decodeSelectedCompany(int companyIndex, Persona foundPerson) {
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
			return decodedDpi;
		} else {
			System.out.println();
			System.out.println("** Esta persona no se encuentra asociada a la empresa seleccionada **");
			return null;
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
