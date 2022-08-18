package main.model;

public class Persona {

	private String nombre;
	private String dpi;
	private String fechaNac;
	private String direccion;
	
	public Persona(String nombre, String dpi, String fechaNac, String direccion) {
		super();
		this.nombre = nombre;
		this.dpi = dpi;
		this.fechaNac = fechaNac;
		this.direccion = direccion;
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
	
	public int compareTo(Persona persona2) {
		return this.getNombre().compareTo(persona2.getNombre());
	}
}
