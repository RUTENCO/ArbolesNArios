package nodos;

/**
 * Clase que representa un nodo con 3 campos para las listas generalizadas.
 * 
 * @author sneyd
 *
 */
public class NodoLg extends NodoSimple {

	/**
	 * Indica si el campo {@link #dato} es un átomo o es un apuntador hacia una
	 * sublista, <b>0</b> y <b>1</b> respectivamente.
	 */
	private int sw;

	/**
	 * Inicializa el nodo con el campo de dato dado.
	 */
	public NodoLg() {
		super(null);
		setSw(0);
		setLiga(null);
	}

	/**
	 * Contructor. Inicializa el objeto {@link NodoLg} con los argumentos
	 * entregados.
	 * 
	 * @param sw Switch. Determina si este nodo es un átomo o un apuntador hacia una
	 *           sublista.
	 * @param d  Dato a almacenar en el nodo.
	 * @param l  {@link NodoLg} al cual apunta la liga de este nodo.
	 */
	public NodoLg(int sw, Object d, NodoLg l) {
		super(d, l);
		this.sw = sw;
	}

	public int getSw() {
		return sw;
	}

	public void setSw(int sw) {
		this.sw = sw;
	}

	@Override
	public String toString() {
		String r, fields, d, l;
		r = "NLg" + "@" + Integer.toHexString(hashCode());

		if (getDato() == null) {
			d = "null";
		} else {
			d = getDato().toString();
		}
		if (getLiga() == null) {
			l = "null";
		} else {
			l = "@" + Integer.toHexString(getLiga().hashCode());
		}

		fields = String.format("(sw=%d,d=%s,l=%s)", sw, d, l);

		return r.concat(fields);
	}

}
