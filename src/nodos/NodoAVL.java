package nodos;

public class NodoAVL extends NodoDoble {

	private int Fb;

	public NodoAVL(Object d) {
		super(d);
		Fb = 0;
	}

	public int getFb() {
		return Fb;
	}

	public void setFb(int fb) {
		Fb = fb;
	}

	@Override
	public String toString() {
		String r, fields, dd, li, ld;
		r = "ND" + "@" + Integer.toHexString(hashCode());

		if (getDato() == null) {
			dd = "null";
		} else {
			dd = getDato().toString();
		}
		if (getLi() == null) {
			li = "null";
		} else {
			li = "@" + Integer.toHexString(getLi().hashCode());
		}
		if (getLd() == null) {
			ld = "null";
		} else {
			ld = "@" + Integer.toHexString(getLd().hashCode());
		}
		fields = String.format("(d=%s,li=%s,ld=%s,Fb=%d)", dd, li, ld, getFb());

		return r.concat(fields);
	}

}
