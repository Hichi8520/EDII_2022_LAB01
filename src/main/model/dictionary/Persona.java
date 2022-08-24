package main.model.dictionary;

public class Persona implements Comparable{

	private final String nombre;
	private final String dpi;
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
	
	/*public int compareTo(Persona persona2) {
		return this.getNombre().compareTo(persona2.getNombre());
	}*/

	@Override
	public int compareTo(Object o) {
		if (o instanceof Persona) {
			
			if ( this.getNombre().compareTo(((Persona)o).getNombre()) == 0 ) { // nombres iguales
				
				return this.getDpi().compareTo(((Persona)o).getDpi());
				
			} else {
				
				return this.getNombre().compareTo(((Persona)o).getNombre());
			}
			
		} else return -10;
	}
	
	@Override
	public String toString() {
		return "{\"name\":\"" + nombre + "\", \"dpi\":\"" + dpi + "\", "
				+ "\"dateBirth\":\"" + fechaNac + "\", \"address\":\"" + direccion + "\"}";
	}
}
