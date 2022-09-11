package nodos;

import java.util.Arrays;
import java.util.List;

public class NodoSimple extends Nodo {

	private NodoSimple liga;

	public NodoSimple() {
		super();
	}

	public NodoSimple(Object d) {
		super(d);
	}

	public NodoSimple(Object d, NodoSimple liga) {
		super(d);
		this.liga = liga;
	}

	public NodoSimple getLiga() {
		return liga;
	}

	public void setLiga(NodoSimple X) {
		liga = X;
	}

	public List<StringBuilder> consNodeRepr(boolean includeLiga, int fieldWidth) {

		StringBuilder topLine, line, bottomLine;
		String d, l;

		d = getDato() == null ? " " : getDato().toString();
		l = getLiga() == null ? "¬" : (includeLiga ? "@" + Integer.toHexString(getLiga().hashCode()) : " ");

		if (fieldWidth == 0) {

			fieldWidth = Math.max(d.length(), l.length());

		}

		// └ ┘ ┌ ┐ ─ │ ┼ ┴ ┬ ┤ ├
		topLine = new StringBuilder(("┬" + "─".repeat(fieldWidth)).repeat(2));
		topLine.replace(0, 1, "┌").append("┐");

		line = new StringBuilder(("│%" + fieldWidth + "s").repeat(2));
		line.append("│");
		line = new StringBuilder(String.format(line.toString(), d, l));

		bottomLine = new StringBuilder(("┴" + "─".repeat(fieldWidth)).repeat(2));
		bottomLine.replace(0, 1, "└").append("┘");

		return Arrays.asList(topLine, line, bottomLine);

	}

	@Override
	public String toString() {

		String r, fields, d, l;
		r = "NS" + "@" + Integer.toHexString(hashCode());

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

		fields = String.format("(d=%s,l=%s)", d, l);

		return r.concat(fields);

	}

}
