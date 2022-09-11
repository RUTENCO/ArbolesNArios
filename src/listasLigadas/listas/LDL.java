package listasLigadas.listas;

import nodos.NodoDoble;

public class LDL {
	public LDL() {
		Primero = null;
		ultimo = null;
	}

	protected NodoDoble Primero, ultimo;

	public NodoDoble PrimerNodo() {
		return Primero;
	}

	public NodoDoble ultimoNodo() {
		return ultimo;
	}

	public NodoDoble UltimoNodo() {
		return ultimo;
	}

	public Boolean esVacia() {
		return Primero == null;
	}

	public Boolean FinDeRecorrido(NodoDoble p) {
		return p == null;
	}

	public NodoDoble Anterior(NodoDoble p) {
		return p.getLi();
	}

	public void RecorrerLista() {
		NodoDoble p = Primero;
		while (!FinDeRecorrido(p)) {
			System.out.println(p.getDato());
			p = p.getLd();
		}
	}

	public NodoDoble BuscarDondeInsertar(Object d) {
		NodoDoble p = Primero;
		while (!FinDeRecorrido(p) && d.hashCode() > p.getDato().hashCode()) {
			p = p.getLd();
		}
		return p.getLi();
	}

	public void Insertar(Object d, NodoDoble anterior) {
		NodoDoble x = new NodoDoble(d);
		Conectar(x, anterior);
	}

	public void Conectar(NodoDoble x, NodoDoble ax) {
		if (ax == null) {
			if (Primero == null) {
				ultimo = x;
			}
			x.setLd(Primero);
			Primero = x;
			return;
		}
		x.setLd(ax.getLd());
		x.setLi(ax);
		ax.setLd(x);
		if (ax == ultimo) {
			ultimo = x;
			return;
		} else {
			x.getLd().setLi(x);
		}
	}

	public NodoDoble BuscarDato(Object d) {
		NodoDoble p = Primero;
		while (!FinDeRecorrido(p) && p.getDato() != d) {
			p = p.getLd();
		}
		if (p == null) {
			System.out.println("ERROR: Dato no encontrado");
		}
		return p;
	}

	public void Borrar(NodoDoble x) {
		if (x == null) {
			System.out.println("ERROR: Par?metro inv?lido");
			return;
		}
		Desconectar(x);
	}

	public void Desconectar(NodoDoble x) {
		if (x == Primero) {
			Primero = x.getLd();
			if (x == ultimo) {
				ultimo = Primero;
				return;
			}
			Primero.setLi(null);
		}
		x.getLi().setLd(x.getLd());
		if (x == ultimo) {
			ultimo = x.getLi();
		} else {
			x.getLd().setLi(x.getLi());
		}
	}
}
