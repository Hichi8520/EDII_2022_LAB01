package main.java.com.talenthub.components.tree;

import main.java.com.talenthub.components.dictionary.model.Persona;
import main.java.com.talenthub.components.tree.model.Nodo;
import main.java.com.talenthub.components.tree.model.ResultadoBusqueda;

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
	public Persona busqueda(String nombrePersona) {
		Persona personaBuscada = new Persona(nombrePersona, null, null, null, null);
		ResultadoBusqueda resultado = busquedaEnNodo(this.raiz, personaBuscada);
		
		return resultado.getNodo().getClaves()[resultado.getIndice()];
		
	}
	
	private ResultadoBusqueda busquedaEnNodo(Nodo nodo, Persona persona) {
		
		int i = 0;
		
		while(i < nodo.getNumClaves() && nodo.getClaves()[i].compareTo(persona) < 0) {
			i++;
		}
		
		if(i < nodo.getNumClaves() && nodo.getClaves()[i].compareTo(persona) == 0) {
			return new ResultadoBusqueda(nodo, i);
		}
		if(nodo.isEsHoja()) {
			return new ResultadoBusqueda(nodo, null);
		}
		else {
			return busquedaEnNodo(nodo, persona);
		}
	}
	
	private void insercionEnNodo(Nodo nodo, Persona persona) {
		
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
		
		if (this.raiz == null) { // el árbol está vacío
			this.raiz = new Nodo(this.t);
			this.raiz.setClave(0, persona);
		}
		
		Nodo r = this.raiz;
		
		if (r.getClavesUsadas() == (2*(this.t-1))) {// 2*t -1 ( U - maximo de claves)
			
			Nodo s = new Nodo(this.t);
			
			this.raiz = s;
			s.setEsHoja(false);
			s.setClavesUsadas(0);
			s.setHijo(0, r);
			dividir(s, 0, r); // es lo mismo decir dividir(s,0,s.hijo[0]);
			insercionEnNodo(s, persona);
		}
		else {
			Nodo s = new Nodo(this.t);
			insercionEnNodo(s, persona);
		}
	}
	
	private void dividir(Nodo npadre, int posicion, Nodo nhijo) {
		
		Nodo newNodo = new Nodo(this.t);
		
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
