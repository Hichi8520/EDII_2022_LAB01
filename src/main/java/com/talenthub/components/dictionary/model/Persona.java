package main.java.com.talenthub.components.dictionary.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import main.java.com.talenthub.components.huffman.Huffman;

public class Persona implements Comparable{

	@JsonProperty("name")
	private String nombre;
	
	@JsonProperty("dpi")
	private String dpi;
	
	@JsonProperty("datebirth")
	private String fechaNac;
	
	@JsonProperty("address")
	private String direccion;
	
	@JsonProperty("companies")
	private List<String> empresas;
	
	@JsonProperty("recluiter")
	private String reclutador;
	
	private Map<String, String> mapaEmpresaDpi;
	
	public Persona(String nombre, String dpi, String fechaNac, String direccion, List<String> empresas, String reclutador) {
		super();
		this.nombre = nombre;
		this.dpi = dpi;
		this.fechaNac = fechaNac;
		this.direccion = direccion;
		this.empresas = empresas;
		this.mapaEmpresaDpi = new HashMap<String, String>();
		this.reclutador = reclutador;
	}
	
	public Persona(String nombre, String dpi, String reclutador) {
		super();
		this.nombre = nombre;
		this.dpi = dpi;
		this.reclutador = reclutador;
		this.mapaEmpresaDpi = new HashMap<String, String>();
	}
	
	public Persona() {
		this.mapaEmpresaDpi = new HashMap<String, String>();
	}

	public String getNombre() {
		return nombre;
	}

	public String getDpi() {
		return dpi;
	}

	public String getFechaNac() {
		return fechaNac;
	}

	public void setFechaNac(String fechaNac) {
		this.fechaNac = fechaNac;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<String> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(List<String> empresas) {
		this.empresas = empresas;
	}

	public Map<String, String> getMapaEmpresaDpi() {
		return mapaEmpresaDpi;
	}

	public void setMapaEmpresaDpi(Map<String, String> mapaEmpresaDpi) {
		this.mapaEmpresaDpi = mapaEmpresaDpi;
	}

	public String getReclutador() {
		return reclutador;
	}

	public void setReclutador(String reclutador) {
		this.reclutador = reclutador;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof Persona) {
			
			if ( this.getNombre().toLowerCase().compareTo(((Persona)o).getNombre().toLowerCase()) == 0 ) { // nombres iguales
				
				return this.getDpi().compareTo(((Persona)o).getDpi());
				
			} else {
				
				return this.getNombre().toLowerCase().compareTo(((Persona)o).getNombre().toLowerCase());
			}
			
		} else return -10;
	}
	
	@Override
	public String toString() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
		
		/*return "{\"name\":\"" + nombre + "\", \"dpi\":\"" + dpi + "\", "
				+ "\"datebirth\":\"" + fechaNac + "\", \"address\":\"" + direccion + "\"}";*/
	}
	
	public String toSimpleString() {
		return nombre + " | " + dpi + " | " + reclutador + " | " + fechaNac + " | " + direccion;
	}
	
	public void encodeCompanies() {
		this.mapaEmpresaDpi = new HashMap<String, String>();
		
		Huffman huff = new Huffman();
		if(this.empresas != null && !this.empresas.isEmpty()) {
			for(String empresa: this.empresas) {
				mapaEmpresaDpi.put(empresa, huff.encode(empresa + this.dpi));
			}
		}
	}
}
