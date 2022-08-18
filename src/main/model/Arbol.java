package main.model;

public class Arbol {

	private int grado;
	private Nodo raiz;
	private int t;
	
	public Arbol(int grado) {
		super();
		this.grado = grado;
		this.t = (int)(Math.ceil(this.grado / 2));
	}
	
	/*
	 * Getters y setters
	 */
	public int getGrado() {
		return grado;
	}

	public Nodo getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}
	
	/*
	 * Métodos particulares
	 */
	public Nodo busqueda(Nodo nodo, Persona persona) {
		
		int i = 0;
		
		while(i < nodo.getNumClaves() && nodo.getClaves()[i].compareTo(persona) < 0) {
			i++;
		}
		
		if(i < nodo.getNumClaves() && nodo.getClaves()[i].compareTo(persona) == 0) {
			return nodo;
		}
		if(nodo.isEsHoja()) {
			return null;
		}
		else {
			return busqueda(nodo, persona);
		}
	}
	
	public void insercionEnNodo(Nodo nodo, Persona persona) {
		
		if (nodo.isEsHoja()) {
			
			int i = nodo.getClavesUsadas();
			
			while (i >= 1 && persona.compareTo(nodo.getClaves()[i-1]) < 0) {
				nodo.correrClave(i, i-1);
				i--;
			}
			
			nodo.getClaves()[i] = persona;
			nodo.addClavesUsadas(1);
		}
		else { // el nodo no es hoja
			
			int j = 0;
			
			while (j < nodo.getClavesUsadas() && persona.compareTo(nodo.getClaves()[j]) > 0) {
				j++;
			}
			
			if (nodo.getHijos()[j].getClavesUsadas() == (2*(this.t-1))) {// ( U - maximo de claves)
				
				dividir(nodo, j, nodo.getHijos()[j]);
				
				if (persona.compareTo(nodo.getClaves()[j]) > 0) {
					j++;
				}
				
				insercionEnNodo(nodo.getHijos()[j], persona);
			}
		}
	}
	
	public void insertar(Persona persona) {
		
		Nodo r = this.raiz;
		
		if (r.getClavesUsadas() == (2*(this.t-1))) {// 2*t -1 ( U - maximo de claves)
			
			Nodo s = new Nodo();
			
			this.raiz = s;
			s.setEsHoja(false);
			s.setClavesUsadas(0);
			s.setHijo(0, r);
			dividir(s, 0, r); // es lo mismo decir dividir(s,0,s.hijo[0]);
			insercionEnNodo(s, persona);
		}
		else {
			Nodo s = new Nodo();
			insercionEnNodo(s, persona);
		}
	}
	
	public void dividir(Nodo npadre, int posicion, Nodo nhijo) {
		
		Nodo newNodo = new Nodo();
		
		newNodo.setEsHoja(nhijo.isEsHoja());
		newNodo.setClavesUsadas(this.t-1); // L  - minimo de claves 
		
		for(int j=0; j<(this.t-1); j++) {// copia ultimas claves del nodo hijo al newNodo
			
			newNodo.setClave(j, nhijo.getClaves()[j+this.t]);
		}
		if (!nhijo.isEsHoja()) {
			
			for(int k=0; k < this.t; k++) {
				newNodo.setHijo(k, nhijo.getHijos()[k+this.t]);
			}
		}
		
		nhijo.setClavesUsadas(this.t-1);
		for(int j=npadre.getClavesUsadas(); j < posicion; j++) {
			npadre.setHijo(j+1, npadre.getHijos()[j]);
		}
		npadre.setHijo(posicion+1, newNodo);
		
		for(int j=npadre.getClavesUsadas(); j < posicion; j--) {
			npadre.setClave(j+1, npadre.getClaves()[j]);
		}
		
		npadre.setClave(posicion, nhijo.getClaves()[this.t-1]);
		npadre.addClavesUsadas(1);
		
	}
}
