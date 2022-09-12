package nodos;

import java.util.Arrays;
import java.util.List;

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

	public List<StringBuilder> nodeRepr(boolean showLiga, int fieldWidth) {

		StringBuilder topLine, line, bottomLine;

		String sw, d, l;

		sw = Integer.toString(getSw());
		d = (getSw() == 1) ? " " : (getDato() == null) ? " " : getDato().toString();
		l = (getLiga() == null) ? "¬" : (showLiga ? "@" + Integer.toHexString(getLiga().hashCode()) : " ");

		if (fieldWidth == 0) {

			fieldWidth = getMinFieldWidth(showLiga);

		}

		topLine = new StringBuilder(("┬" + "─".repeat(fieldWidth)).repeat(3));
		topLine.replace(0, 1, "┌").append("┐");

		line = new StringBuilder(("│%" + fieldWidth + "s").repeat(3));
		line.append("│");
		line = new StringBuilder(String.format(line.toString(), sw, d, l));

		bottomLine = new StringBuilder(("┴" + "─".repeat(fieldWidth)).repeat(3));
		bottomLine.replace(0, 1, "└").append("┘");

		return Arrays.asList(topLine, line, bottomLine);

	}

	public int getMinFieldWidth(boolean withLiga) {

		return Math.max(1,
				Math.max(
						(getSw() == 1) ? 0 : (getDato() == null) ? 0 : getDato().toString().length(),
						(withLiga ? (getLiga() == null ? 1 : Integer.toHexString(getLiga().hashCode()).length() + 1)
								: 0)));

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
