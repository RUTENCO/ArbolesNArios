package nodos;

public class NodoDoble extends Nodo {

	private NodoDoble Li, Ld;

	public NodoDoble() {
		super();
	}

	public NodoDoble(Object d) {
		super(d);
		this.Li = null;
		this.Ld = null;
	}

	public NodoDoble getLi() {
		return Li;
	}

	public NodoDoble getLd() {
		return Ld;
	}

	public void setLi(NodoDoble X) {
		Li = X;
	}

	public void setLd(NodoDoble X) {
		Ld = X;
	}

}
