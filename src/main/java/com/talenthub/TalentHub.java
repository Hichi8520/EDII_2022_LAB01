package main.java.com.talenthub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import main.java.com.talenthub.components.dictionary.Diccionario;
import main.java.com.talenthub.components.dictionary.model.Persona;
import main.java.com.talenthub.components.files.FileManager;
import main.java.com.talenthub.components.huffman.Huffman;
import main.java.com.talenthub.components.rsa.RSA2;

public class TalentHub {
	
	private static Diccionario dic;
	private static FileManager fm;
	private static BufferedReader reader;
	private static String recluiterName;

	public static void main(String[] args) throws IOException, Exception {
		
		dic = new Diccionario();
		fm = new FileManager();
		reader = new BufferedReader(new InputStreamReader(System.in));	
		
		titleMessage();
		cargarJsonEmpresas();
		cargarJsonReclutadores();
		//generarLlavesRSA();
		cargarCsv();
		//mainMenu();
		startTalentHub();
	}
	
	private static void cargarJsonEmpresas() {
		// Select .json file
        fm.loadJsonEmpresas();
	}
	
	private static void cargarJsonReclutadores() {
		// Select .json file
        fm.loadJsonReclutadores();
	}
	
	private static void generarLlavesRSA() throws Exception {
		fm.generateRecluiterCompanyKeys();
	}
	
	private static void cargarCsv() {
		// Select .csv file
        if (fm.selectFile()) {
        	// Store the companies
            dic.insertarInstrucciones(fm.getInstructions());
        }
	}
	
	private static void startTalentHub() {
        String option = "";
        
        while (!option.equalsIgnoreCase("x")) {
        	
        	recluiterName = "";
        	System.out.println();
        	System.out.println("Bienvenido/a a TalentHub, ingresa una opcion:");
        	System.out.println("(1) Login");
        	System.out.println("(x) Salir");
        	
            // Reading data using readLine
    		try {
    			option = reader.readLine();
    			
    			if(isNumeric(option) && Integer.valueOf(option) == 1) {
    				System.out.println();
    	        	System.out.print("Nombre del reclutador: ");
    	        	
    	        	recluiterName = reader.readLine();
    				mainMenuTalentHub();
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
	
	private static void mainMenuTalentHub() {
		String option = "";
        
        while (!option.equalsIgnoreCase("x")) {
        	System.out.println();
        	System.out.println("Hola " + recluiterName + ", ingresa la funcion a realizar:");
        	System.out.println("(1) Listado de candidatos");
        	System.out.println("(2) Ingresar nuevo candidato");
			System.out.println("(3) Abrir conversacion");
			System.out.println("(4) Actualizar registro de candidato");
			System.out.println("(5) Iniciar proceso de reclutamiento");
        	System.out.println("(x) Salir"); 
        	
            // Reading data using readLine
    		try {
    			option = reader.readLine();
    			
    			if(option.equalsIgnoreCase("1")) {
    				dic.listarPersonas();
    			} else if (option.equalsIgnoreCase("2")) {
    				insertarPersona();
    			} else if (option.equalsIgnoreCase("3")) {
    				buscarPersonaTalentHub();
    			} else if (option.equalsIgnoreCase("4")) {
    				actualizarPersona();
    			} else if (option.equalsIgnoreCase("5")) {
    				procesoReclutamiento();
    			} else if (option.equalsIgnoreCase("x")) {
    				System.out.println();
					System.out.println("Cerrando sesion...");
    			} else {
    				System.out.println();
					System.out.println("** Opcion invalida **");
    			}
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
	}
	
	private static void procesoReclutamiento() {
		try {
			System.out.println();
			System.out.println("Ingresa el dpi del candidato");
			String dpi;
			
			System.out.print("DPI: ");
			dpi = reader.readLine();
			
			Persona found = dic.buscar(dpi);
			if (found == null) {
				System.out.println();
				System.out.println(String.format("** El candidato con dpi %s no fue encontrado **", dpi));
			} else {
				System.out.println();
				System.out.println(String.format("Candidato seleccionado: %s", found.getNombre()));
				
				String option = "";
		        
		        System.out.println();
		    	System.out.println("--- LISTADO DE EMPRESAS INGRESADAS ---");
		    	for(int i=0; i < fm.getCompanies().size(); i++) {
		    		System.out.println(String.format("(%d) %s", i+1, fm.getCompanies().get(i)));
		    	}
		        
		    	System.out.println();
	        	System.out.println("Ingresa una empresa del proceso de reclutamiento o (x) para salir:");
	        	
	            // Reading data using readLine
	        	option = reader.readLine();
				
				if(isNumeric(option) && Integer.valueOf(option) > 0 && Integer.valueOf(option) <= fm.getCompanies().size()) {
					String selectedCompany = fm.getCompanies().get(Integer.valueOf(option) - 1);
					System.out.println();
		        	System.out.println(String.format("Se iniciara un proceso de reclutamiento para %s en %s", found.getNombre(), selectedCompany));
					actualizarCandidatoConEmpresa(found, selectedCompany);
				} else if (option.equalsIgnoreCase("x")) {
					System.out.println();
					System.out.println("Programa terminado...");
				} else {
					System.out.println();
					System.out.println("** Opcion invalida **");
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
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
			System.out.println("(3) Validar identidad");
        	System.out.println("(x) Salir"); 
        	
            // Reading data using readLine
    		try {
    			option = reader.readLine();
    			
    			if(isNumeric(option) && Integer.valueOf(option) > 0 && Integer.valueOf(option) <= 2) {
    				buscarPersona(selectedCompanyIndex, option);
    			} else if (option.equalsIgnoreCase("3")) {
    				String empresa = fm.getCompanies().get(selectedCompanyIndex);
    				menuValidarIdentidad(empresa);
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
	
	private static void menuValidarIdentidad(String company) {
		String option = "";
        
        System.out.println();
    	System.out.println("--- LISTADO DE RECLUTADORES ---");
    	for(int i=0; i < fm.getRecluiters().size(); i++) {
    		System.out.println(String.format("(%d) %s", i+1, fm.getRecluiters().get(i)));
    	}
        
        while (!option.equalsIgnoreCase("x")) {
        	
        	System.out.println();
        	System.out.println("Ingresa un reclutador del proceso de reclutamiento o (x) para salir:");
        	
            // Reading data using readLine
    		try {
    			option = reader.readLine();
    			
    			if(isNumeric(option) && Integer.valueOf(option) > 0 && Integer.valueOf(option) <= fm.getRecluiters().size()) {
    				String recluiter = fm.getRecluiters().get(Integer.valueOf(option) - 1);
    				System.out.println();
    	        	System.out.println("Reclutador seleccionado: " + recluiter);
    				validarIdentidad(recluiter, company, null);
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
	
	private static void validarIdentidad(String reclutador, String empresa, Persona persona) {
		try {
			fm.generateRecluiterCompanyKeys(reclutador, empresa);
			
			RSA2 rsa = new RSA2();
	        rsa.openFromDiskPrivateKey(String.format("rsa/%s - %s/private.rsa", reclutador, empresa));    
	        rsa.openFromDiskPublicKey(String.format("rsa/%s - %s/public.rsa", reclutador, empresa));
			
			System.out.println();
			System.out.println("Ingresa un mensaje a enviar como RECLUTADOR:");
			String mensajeOriginal, mensajeCifrado, mensajeDescifrado;
			
			mensajeOriginal = reader.readLine();

	        mensajeCifrado = rsa.Encrypt(mensajeOriginal);
	        System.out.println();
			System.out.println("Mensaje cifrado:");
			System.out.println(mensajeCifrado);
			
			mensajeDescifrado = rsa.Decrypt(mensajeCifrado);
			
			System.out.println();
			System.out.println("Mensaje descifrado:");
			System.out.println(mensajeDescifrado);
			
			if(mensajeOriginal.equals(mensajeDescifrado)) {
				System.out.println();
				System.out.println("¡Validacion de identidad exitosa!");
				System.out.println();
				System.out.println(String.format("Se compartira con la empresa %s la informacion de:", empresa));
				System.out.println();
				System.out.println("NOMBRE | DPI | RECLUTADOR | FECHA NAC | DIRECCION");
				System.out.println(persona.toSimpleString());
			} else {
				System.out.println();
				System.out.println("** Validación de identidad fallida **");
			}
	        
			/*System.out.println();
			System.out.println("Ingresa un mensaje a enviar como EMPRESA:");
			mensajeOriginal = "";
			mensajeCifrado = "";
			mensajeDescifrado = "";
			
			mensajeOriginal = reader.readLine();

	        mensajeCifrado = rsa.Encrypt(mensajeOriginal);
	        System.out.println();
			System.out.println("Mensaje cifrado:");
			System.out.println(mensajeCifrado);
			
			mensajeDescifrado = rsa.Decrypt(mensajeCifrado);
			
			System.out.println();
			System.out.println("Mensaje descifrado:");
			System.out.println(mensajeDescifrado);
			
			if(mensajeOriginal.equals(mensajeDescifrado)) {
				System.out.println();
				System.out.println("¡Validacion de identidad exitosa!");
			} else {
				System.out.println();
				System.out.println("** Validación de identidad fallida **");
			}
			*/
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			
			persona = new Persona(nombre, dpi, recluiterName);
			dic.insertar(persona, true);
			
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
			
			persona = new Persona(nombre, dpi, "", "", null, null);
			dic.eliminar(persona, true);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void actualizarPersona() {
		try {
			System.out.println();
			System.out.println("Ingresa el dpi del candidato");
			String nombre, dpi, fechaNac, direccion;
			Persona persona;
			
			System.out.print("DPI: ");
			dpi = reader.readLine();
			
			Persona found = dic.buscar(dpi);
			if (found == null) {
				System.out.println();
				System.out.println(String.format("** El candidato con dpi %s no fue encontrado **", dpi));
			} else {
				System.out.println();
				System.out.println(String.format("Actualizar los siguientes datos de %s:", found.getNombre()));
				System.out.println();
				System.out.print("Fecha de nacimiento: ");
				fechaNac = reader.readLine();
				System.out.print("Direccion: ");
				direccion = reader.readLine();
				
				persona = new Persona(found.getNombre(), dpi, fechaNac, direccion, found.getEmpresas(), found.getReclutador());
				dic.actualizar(persona, true);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void actualizarCandidatoConEmpresa(Persona persona, String company) {
		List<String> empresas = persona.getEmpresas();
		empresas.add(company);
		persona.setEmpresas(empresas);
		persona.encodeCompanies();
		//persona.setReclutador(recluiterName);
		
		dic.actualizar(persona, true);
		
		reviewCandidato(persona, company);
	}
	
	private static void reviewCandidato(Persona persona, String company) {
		String option = "";
        
		System.out.println();
    	System.out.println("Es un candidato ideal?");
    	System.out.println("(1) Si");
		System.out.println("(2) No");
    	
        // Reading data using readLine
		try {
			option = reader.readLine();
			
			if (option.equalsIgnoreCase("1")) {
				validarIdentidad(recluiterName, company, persona);
			} else if (option.equalsIgnoreCase("2")) {
				dic.eliminar(persona, true);
			} else {
				System.out.println();
				System.out.println("** Opcion invalida **");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void buscarPersonaTalentHub() {
		try {
			System.out.println();
			System.out.print("Ingresa el dpi del candidato a buscar: ");
			String dpi = reader.readLine();
			
			Persona found = dic.buscar(dpi);
			
			if (found == null) {
				System.out.println();
				System.out.println(String.format("** El dpi %s no fue encontrado **", dpi));
			} else {
				//fm.writeFile(dpi, found);
    	        
				//System.out.println();
				//System.out.println("Archivo de salida generado con exito");
				System.out.println();
				System.out.println("Candidato encontrado:");
				System.out.println();
				System.out.println("NOMBRE | DPI | RECLUTADOR | FECHA NAC | DIRECCION");
				System.out.println(found.toSimpleString());
				
				fm.cipherFilesByDpi(found.getDpi());
				descifrarConv(found.getDpi(), fm.getCipheredCount());
				
				System.out.println();
				System.out.println("Presiona Enter para continuar");
				System.in.read();
				
				fm.compressFilesByDpi(found.getDpi());
				descomprimirCarta(found.getDpi(), fm.getCompressedCount());
				
				System.out.println();
				System.out.println("Presiona Enter para continuar");
				System.in.read();
			}
			
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
