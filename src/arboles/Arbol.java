package arboles;

import java.util.List;

import nodos.Nodo;

public interface Arbol {
    
    /**
     * Busca un dato en el árbol y retorna su {@link Nodo} correspondiente.
     * 
     * @param d Dato a buscar.
     * @return {@link Nodo} que contiene el dato <b>d</b>.
     */
    public Nodo find(Object d);

    /**
     * Busca y retorna el {@link Nodo} padre que tiene como hijo el {@link Nodo}
     * entregado como parámetro.
     * 
     * @param child {@link Nodo} hijo.
     * @return Nodo que tiene como hijo a <b>child</b>.
     */
    public Nodo getParent(Nodo child);

    /**
     * Retorna una lista que contiene los ancestros del {@link Nodo} dado como
     * parámetro.
     * <p>
     * Los ancestros van desde la raíz de este árbol hasta el árbol dado como
     * parámetro.
     * 
     * @param arbol {@link Nodo} árbol a retornar sus ancestros.
     * @return Ancestros de <b>arbol</b>.
     */
    public List<Object> getAncestors(Nodo arbol);

    /**
     * Calcula y retorna la altura de este árbol.
     * 
     * @return Altura de este árbol.
     */
    public int getHeight();

    /**
     * Calcula y retorna el máximo grado de este árbol.
     * 
     * @return Máximo grado de este árbol.
     */
    public int getMaxDegree();

    /**
     * Cuenta y retorna el número de hojas de este árbol.
     * 
     * @return Número de hojas, siendo cada hoja un arbol sin hijos.
     */
    public int countLeafs();

}
