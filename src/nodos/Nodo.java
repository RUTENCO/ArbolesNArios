package nodos;

public abstract class Nodo {

    private Object dato;

    public Nodo() {
        this.dato = null;
    }

    public Nodo(Object dato) {
        this.dato = dato;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

}
