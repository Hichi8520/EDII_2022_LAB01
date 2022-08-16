package main.model;

public class Arbol {

	private int grado;
	private Nodo raiz;
	
	public Arbol(int grado) {
		super();
		this.grado = grado;
	}
	
	public int getGrado() {
		return grado;
	}

	public Nodo getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}
}
