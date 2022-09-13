package main.java.com.talenthub.components.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.com.talenthub.components.dictionary.model.Instruction;
import main.java.com.talenthub.components.dictionary.model.Persona;

public class FileManager {

	private List<String> companies;
	private List<Instruction> instructions;
	
	public FileManager() {
		super();
		companies = new ArrayList<>();
		instructions = new ArrayList<>();
	}
	
	public List<String> getCompanies() {
		return companies;
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}
	
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
            readFile(csvFile);
            return true;
            
        } else if (dialog == JFileChooser.CANCEL_OPTION) {
        	System.out.println();
        	System.out.println("** No se ha cargado ningun archivo **");
        	return false;
        }
        return false;
    }
	
	private void readFile(File file) {
		BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while(line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                storeLine(line);
                line = reader.readLine();
            }
            //text.setText(sb.toString());
            System.out.println();
            System.out.println(instructions.size() + " instrucciones leidas");
        } 
        catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
	}
	
	private void storeLine(String line) {
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
}
