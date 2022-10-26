package main.java.com.talenthub.components.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.talenthub.components.dictionary.model.Instruction;
import main.java.com.talenthub.components.dictionary.model.Persona;
import main.java.com.talenthub.components.lzw.LZW;
import main.java.com.talenthub.components.rsa.RSA2;
import main.java.com.talenthub.components.trans.Trans;

public class FileManager {

	private List<String> companies;
	private List<String> recluiters;
	private List<Instruction> instructions;
	private LZW lzw;
	private RSA2 rsa;
	private Trans trans;
	private Integer compressedCount;
	private Integer cipheredCount;
	
	public FileManager() {
		super();
		companies = new ArrayList<>();
		recluiters = new ArrayList<>();
		instructions = new ArrayList<>();
		lzw = new LZW();
		rsa = new RSA2();
		trans = new Trans();
		compressedCount = 0;
		cipheredCount = 0;
	}
	
	public List<String> getCompanies() {
		return companies;
	}

	public List<String> getRecluiters() {
		return recluiters;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}
	
	public Integer getCompressedCount() {
		return compressedCount;
	}
	
	public Integer getCipheredCount() {
		return cipheredCount;
	}
	
	/*
	 * JSON Companies
	 */
	public boolean loadJsonEmpresas() {
		BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
        	File jsonFile = new File("files/companies.json");
            reader = new BufferedReader(new FileReader(jsonFile));
            String line = reader.readLine();
            while(line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                storeCompaniesLine(line);
                line = reader.readLine();
            }
            //text.setText(sb.toString());
            System.out.println();
            System.out.println(companies.size() + " empresas leidas");
            reader.close();
        } 
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        if(companies.isEmpty()) return false;
        else return true;
	}
	
	private void storeCompaniesLine(String line) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			companies = objectMapper.readValue(line, List.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * JSON Reclutadores
	 */
	public boolean loadJsonReclutadores() {
		BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
        	File jsonFile = new File("files/recluiter.json");
            reader = new BufferedReader(new FileReader(jsonFile));
            String line = reader.readLine();
            while(line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                storeRecluitersLine(line);
                line = reader.readLine();
            }
            //text.setText(sb.toString());
            System.out.println();
            System.out.println(recluiters.size() + " reclutadores leidos");
            reader.close();
        } 
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        if(recluiters.isEmpty()) return false;
        else return true;
	}
	
	private void storeRecluitersLine(String line) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			recluiters = objectMapper.readValue(line, List.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * RSA generate keys
	 */
	public boolean generateRecluiterCompanyKeys() throws Exception {
		for (String reclutador : recluiters) {
			for (String empresa : companies) {
				
				new File(String.format("rsa/%s - %s", reclutador, empresa)).mkdirs();
				rsa.genKeyPair(512);
		        
		        rsa.saveToDiskPrivateKey(String.format("rsa/%s - %s/private.rsa", reclutador, empresa));
		        rsa.saveToDiskPublicKey(String.format("rsa/%s - %s/public.rsa", reclutador, empresa));
			}
		}
		return true;
	}

	/*
	 * Select input file
	 */
	public boolean selectFile() {
        String[] formats = new String[] { "csv" };
        JFileChooser fChooser = new JFileChooser();
        FileNameExtensionFilter fnFilter = new FileNameExtensionFilter("CSV", formats);
        File csvFile;
        fChooser.setFileFilter(fnFilter);
        int dialog = fChooser.showOpenDialog(null);
        if (dialog == JFileChooser.APPROVE_OPTION) {
            csvFile = fChooser.getSelectedFile();
            System.out.println();
            System.out.println(csvFile.getPath());
            readCsvFile(csvFile);
            return true;
            
        } else if (dialog == JFileChooser.CANCEL_OPTION) {
        	System.out.println();
        	System.out.println("** No se ha cargado ningun archivo **");
        	return false;
        }
        return false;
    }
	
	private void readCsvFile(File file) {
		BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                storeCsvLine(line);
                line = reader.readLine();
            }
            //text.setText(sb.toString());
            System.out.println();
            System.out.println(instructions.size() + " instrucciones leidas");
            reader.close();
        } 
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
	}
	
	private void storeCsvLine(String line) {
		String[] info = line.split(";");
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Persona persona = objectMapper.readValue(info[1], Persona.class);
			persona.encodeCompanies();
			instructions.add(new Instruction(info[0], persona));
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Write output file
	 */
	public void writeFile(String name, List<Persona> foundItems) throws IOException {
		File fout = new File(String.format("outputs/%s.output", name));
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		for (Persona persona: foundItems) {
			bw.write(persona.toString());
			bw.newLine();
		}
	 
		bw.close();
	}
	
	public void writeFile(String dpi, Persona foundItem) throws IOException {
		File fout = new File(String.format("outputs/%s.output", foundItem.getNombre()));
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		bw.write(foundItem.toString());
		bw.newLine();
	 
		bw.close();
	}
	
	/*
	 * Compress / decompress
	 */
	public void compressFilesByDpi(String dpi) {
		File file;
		int index = 0;
		boolean didCompress = false;
		
		try {
			do {
				index++;
				file = new File(String.format("files/inputs/REC-%s-%d.txt", dpi, index));
				if(file.exists()) {
					didCompress = true;
					String textToCompress = readRecFile(file);
					List<Integer> compressedText = lzw.compress(textToCompress);
					writeCompressedFile(dpi, compressedText, index);
				}
			} while (file.exists());
			
			System.out.println();
            System.out.println(index - 1 + " cartas de recomendacion comprimidas");
		} catch (IOException e) {
			// Handle file write
			e.printStackTrace();
		}
		
		if(didCompress) compressedCount = index - 1;
        else compressedCount = 0;
	}
	
	private String readRecFile(File file) {
		BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = reader.readLine();
            }
            reader.close();
            return sb.toString();
        } 
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
	}
	
	public void writeCompressedFile(String dpi, List<Integer> compressedText, Integer index) throws IOException {
		new File(String.format("compressed/%s", dpi)).mkdirs();
		File fout = new File(String.format("compressed/%s/compressed-REC-%s-%d.txt", dpi, dpi, index));
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		bw.write(compressedText.toString());
		//bw.newLine();
	 
		bw.close();
	}
	
	public void writeDecompressedFile(String dpi, String decompressedText, Integer index) throws IOException {
		new File(String.format("decompressed/%s", dpi)).mkdirs();
		File fout = new File(String.format("decompressed/%s/REC-%s-%d.txt", dpi, dpi, index));
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		bw.write(decompressedText);
		//bw.newLine();
	 
		bw.close();
	}
	
	public void decompressFileByDpi(String dpi, Integer cardIndex) {
		File file;
		
		try {
			file = new File(String.format("compressed/%s/compressed-REC-%s-%d.txt", dpi, dpi, cardIndex));
			String compressedText = readRecFile(file);
			
			// Format the integer list read from file
			compressedText = compressedText.replace("[", "").replace("]", "");
			List<Integer> compressList = new ArrayList<Integer>();
			for(String element : compressedText.split(",")) {
				compressList.add(Integer.valueOf(element.trim()));
			}
			
			String originalText = lzw.decompress(compressList);
			writeDecompressedFile(dpi, originalText, cardIndex);
			
			System.out.println();
            System.out.println("Carta descomprimida exitosamente");
		} catch (IOException e) {
			// Handle file write
			e.printStackTrace();
		}
	}
	
	/*
	 * Cipher / decipher
	 */
	public void cipherFilesByDpi(String dpi) {
		File file;
		int index = 0;
		boolean didCipher = false;
		
		try {
			do {
				index++;
				file = new File(String.format("files/inputs/CONV-%s-%d.txt", dpi, index));
				if(file.exists()) {
					didCipher = true;
					String textToCipher = readConvFile(file, true);
					String cipheredText = trans.cipher(dpi, textToCipher.replace(System.lineSeparator(), "^"));
					writeCipheredFile(dpi, cipheredText, index);
				}
			} while (file.exists());
			
			System.out.println();
            System.out.println(index - 1 + " conversaciones cifradas");
		} catch (IOException e) {
			// Handle file write
			e.printStackTrace();
		}
		
		if(didCipher) cipheredCount = index - 1;
        else cipheredCount = 0;
	}
	
	private String readConvFile(File file, boolean isEncrypting) {
		BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(line != null) {
                sb.append(line);
                if(isEncrypting)
                	sb.append(System.lineSeparator());
                line = reader.readLine();
            }
            reader.close();
            return sb.toString();
        } 
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
	}
	
	public void writeCipheredFile(String dpi, String cipheredText, Integer index) throws IOException {
		new File(String.format("crypted/%s", dpi)).mkdirs();
		File fout = new File(String.format("crypted/%s/crypted-CONV-%s-%d.txt", dpi, dpi, index));
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		bw.write(cipheredText);
		//bw.newLine();
	 
		bw.close();
	}
	
	public void writeDecipheredFile(String dpi, String decipheredText, Integer index) throws IOException {
		new File(String.format("decrypted/%s", dpi)).mkdirs();
		File fout = new File(String.format("decrypted/%s/CONV-%s-%d.txt", dpi, dpi, index));
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		bw.write(decipheredText);
		//bw.newLine();
	 
		bw.close();
	}
	
	public void decipherFileByDpi(String dpi, Integer convIndex) {
		File file;
		
		try {
			file = new File(String.format("crypted/%s/crypted-CONV-%s-%d.txt", dpi, dpi, convIndex));
			String cipheredText = readConvFile(file, false);
			
			String originalText = trans.decipher(dpi, cipheredText);
			writeDecipheredFile(dpi, originalText, convIndex);
			
			System.out.println();
            System.out.println("Conversacion descifrada exitosamente");
		} catch (IOException e) {
			// Handle file write
			e.printStackTrace();
		}
	}
}
