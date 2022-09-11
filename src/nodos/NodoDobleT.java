package nodos;

import matricesDispersas.Tripleta;

/**
 * Clase que representa la estrutura de un Nodo Doble.
 * 
 * @author sneyd
 *
 */
public class NodoDobleT extends NodoDoble {

	/**
	 * Constructor. Inicializa el objeto instanciado asignando el campo {@link #d}
	 * con el parámetro entregado.
	 * 
	 * @param d -> Valor a asignar al campo {@link #d}.
	 */
	public NodoDobleT(Tripleta t) {
		super(t);
	}

	/**
	 * Retorna el {@link NodoDobleT} conectado a la liga izquierda.
	 * 
	 * @return {@link NodoDobleT} conectado por el campo {@link #Li}.
	 */
	public NodoDobleT getLi() {
		return (NodoDobleT) super.getLi();
	}

	/**
	 * Conecta el {@link NodoDobleT} entregado como parámetro a la liga izquierda de
	 * este nodo.
	 * 
	 * @param li -> {@link NodoDobleT} a conectar.
	 */
	public void setLi(NodoDobleT li) {
		super.setLi(li);
	}

	/**
	 * Retorna el {@link NodoDobleT} conectado a la liga derecha.
	 * 
	 * @return {@link NodoDobleT} conectado por el campo {@link #Ld}.
	 */
	public NodoDobleT getLd() {
		return (NodoDobleT) super.getLd();
	}

	/**
	 * Conecta el {@link NodoDobleT} entregado como parámetro a la liga derecha de
	 * este nodo.
	 * 
	 * @param ld -> {@link NodoDobleT} a conectar.
	 */
	public void setLd(NodoDobleT ld) {
		super.setLd(ld);
	}

	/**
	 * Retorna el objeto {@link #d}.
	 * 
	 * @return El objeto del campo {@link #d}, sea nulo o no.
	 */
	public Tripleta getTripleta() {
		return (Tripleta) super.getDato();
	}

	/**
	 * Asigna al campo {@link #d} el objeto entregado en el parámetro.
	 * 
	 * @param d -> Objeto a asignar al campo {@link #d}.
	 */
	public void setTripleta(Tripleta t) {
		super.setDato(t);
	}

	public int getFila() {

		return getTripleta().getFila();

	}

	public void setFila(int f) {

		getTripleta().setFila(f);

	}

	public int getColumna() {

		return getTripleta().getColumna();

	}

	public void setColumna(int c) {

		getTripleta().setColumna(c);

	}

	public Object getValor() {

		return getTripleta().getValor();

	}

	public void setValor(Object val) {

		getTripleta().setValor(val);

	}

	@Override
	public String toString() {
		String r, fields, dd, li, ld;
		r = "NDT" + "@" + Integer.toHexString(hashCode());

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

		fields = String.format("(d=%s,li=%s,ld=%s)", dd, li, ld);

		return r.concat(fields);
	}

}
