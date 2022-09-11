package listasLigadas.listas;

import nodos.NodoSimple;

public class LSLC extends LSL {
	public LSLC() {
		super();
	}

	@Override
	public boolean finDeRecorrido(NodoSimple p) {
		return p == ultimoNodo;
	}

	@Override
	public NodoSimple anterior(NodoSimple p) {
		if (isEmpty()) {
			return null;
		}
		if (p == primerNodo) {
			return ultimoNodo;
		} else {
			NodoSimple x = primerNodo;
			while (x.getLiga() != p) {
				x = x.getLiga();
			}
			return x;
		}
	}

}
