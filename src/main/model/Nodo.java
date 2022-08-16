package main.model;

public class Nodo {

	private int numClaves;
	private Persona[] claves;
	private int numHijos;
	private Persona[] hijos;
	private boolean esHoja;
	private int clavesUsadas;	
	
	public Nodo() {
		super();
	}
	
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
	public Persona[] getHijos() {
		return hijos;
	}
	public void setHijos(Persona[] hijos) {
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
}
