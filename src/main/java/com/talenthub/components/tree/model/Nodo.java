package main.java.com.talenthub.components.tree.model;

import main.java.com.talenthub.components.dictionary.model.Persona;

public class Nodo {

	private int numClaves;
	private Persona[] claves;
	private int numHijos;
	private Nodo[] hijos;
	private boolean esHoja;
	private int clavesUsadas;	
	
	public Nodo(int t) {
		super();
		// in progress
		this.numClaves = 0;
		this.claves = new Persona[(2*t)-1];
		this.numHijos = 0;
		this.hijos = new Nodo[2*t];
		this.esHoja = true;
		this.clavesUsadas = 0;
	}
	
	/*
	 * Getters y setters
	 */
	public int getNumClaves() {
		return numClaves;
	}
	public void setNumClaves(int numClaves) {
		this.numClaves = numClaves;
	}
	public Persona[] getClaves() {
		return claves;
	}
	public void setClaves(Persona[] claves) {
		this.claves = claves;
	}
	public int getNumHijos() {
		return numHijos;
	}
	public void setNumHijos(int numHijos) {
		this.numHijos = numHijos;
	}
	public Nodo[] getHijos() {
		return hijos;
	}
	public void setHijos(Nodo[] hijos) {
		this.hijos = hijos;
	}
	public boolean isEsHoja() {
		return esHoja;
	}
	public void setEsHoja(boolean esHoja) {
		this.esHoja = esHoja;
	}
	public int getClavesUsadas() {
		return clavesUsadas;
	}
	public void setClavesUsadas(int clavesUsadas) {
		this.clavesUsadas = clavesUsadas;
	}
	
	/*
	 * Métodos particulares
	 */
	public void addClavesUsadas(int cantidad) {
		this.clavesUsadas = this.clavesUsadas + cantidad;
	}
	
	public void correrClave(int posicionNueva, int posicionAnterior) {
		this.claves[posicionNueva] = this.claves[posicionAnterior];
	}
	
	public void setHijo(int posicion, Nodo hijoNuevo) {
		this.hijos[posicion] = hijoNuevo;
	}
	
	public void setClave(int posicion, Persona personaNueva) {
		this.claves[posicion] = personaNueva;
	}
}
