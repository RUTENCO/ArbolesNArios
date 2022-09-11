package nodos;

public class NodoDobleE extends NodoDoble {

	/**
	 * 0 - <b>Li</b> es una hebra. <br>
	 * 1 - <b>Li</b> apunta hacia el hijo izquierdo.
	 */
	private int Bi;

	/**
	 * 0 - <b>Ld</b> es una hebra. <br>
	 * 1 - <b>Ld</b> apunta hacia el hijo derecho.
	 */
	private int Bd;

	public NodoDobleE() {

		super();

		this.Bd = 1;
		this.Bi = 1;

	}

	/**
	 * Constructor. Inicializa el objeto instanciado asignando el campo {@link #d}
	 * con el parámetro entregado.
	 * 
	 * @param d -> Valor a asignar al campo {@link #d}.
	 */
	public NodoDobleE(Object d) {
		super(d);

		this.Bi = 1;
		this.Bd = 1;
	}

	/**
	 * Retorna el {@link NodoDobleE} conectado a la liga izquierda.
	 * 
	 * @return {@link NodoDobleE} conectado por el campo {@link #Li}.
	 */
	public NodoDobleE getLi() {
		return (NodoDobleE) super.getLi();
	}

	/**
	 * Conecta el {@link NodoDobleE} entregado como parámetro a la liga izquierda de
	 * este nodo.
	 * 
	 * @param li -> {@link NodoDobleE} a conectar.
	 */
	public void setLi(NodoDobleE li) {
		super.setLi(li);
	}

	/**
	 * Retorna el {@link NodoDobleE} conectado a la liga derecha.
	 * 
	 * @return {@link NodoDobleE} conectado por el campo {@link #Ld}.
	 */
	public NodoDobleE getLd() {
		return (NodoDobleE) super.getLd();
	}

	/**
	 * Conecta el {@link NodoDobleE} entregado como parámetro a la liga derecha de
	 * este nodo.
	 * 
	 * @param ld -> {@link NodoDobleE} a conectar.
	 */
	public void setLd(NodoDobleE ld) {
		super.setLd(ld);
	}

	public int getBi() {
		return this.Bi;
	}

	public int getBd() {
		return this.Bd;
	}

	public void setBi(int bi) {
		this.Bi = bi;
	}

	public void setBd(int bd) {
		this.Bd = bd;
	}

	@Override
	public String toString() {
		String r, fields, dd, li, ld;
		r = "NDE" + "@" + Integer.toHexString(hashCode());

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
		fields = String.format("(d=%s,li=%s,ld=%s,bi=%d,bd=%d)", dd, li, ld, Bi, Bd);

		return r.concat(fields);
	}

}
