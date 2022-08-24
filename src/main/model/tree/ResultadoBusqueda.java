package main.model.tree;

public class ResultadoBusqueda {

	private final Nodo nodo;
	private final Integer indice;
	
	public ResultadoBusqueda(Nodo nodo, Integer indice) {
		super();
		this.nodo = nodo;
		this.indice = indice;
	}

	public Nodo getNodo() {
		return nodo;
	}

	public int getIndice() {
		return indice;
	}
}
