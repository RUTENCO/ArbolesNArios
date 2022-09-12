package arboles;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import listasGeneralizadas.Lg;
import nodos.Nodo;
import nodos.NodoLg;

/**
 * Esta clase representa un árbol n-ario cualquiera en forma de lista
 * generalizada.
 */
public class ArbolLg extends Lg implements Arbol {

    /**
     * Raíz de este árbol.
     */
    private NodoLg root;

    /**
     * Constructor, Contruye el árbol y le asigna su nodo raíz entregado como
     * parámetro, siempre que este no sea {@code null}.
     * 
     * @param root Raíz a asignar a este árbol, debe ser diferente de {@code null},
     *             de lo contrario arrojará una excepción.
     * @throws IllegalArgumentException Si {@code root == null}.
     */
    public ArbolLg(NodoLg root) {

        super();

        if (root == null) {
            throw new IllegalArgumentException("root must not be null");
        }

        this.root = root;
        setPrimerNodo(root);
        setUltimoNodo(root);

    }

    /**
     * Retorna la raíz de este árbol.
     * 
     * @return {@link #root}
     */
    public NodoLg getRoot() {
        return root;
    }

    /**
     * Asigna la raíz de este árbol.
     * 
     * @param root {@link NodoLg} Raíz a asignar.
     */
    public void setRoot(NodoLg root) {

        this.root = root;
        setPrimerNodo(root);
        setUltimoNodo(root);

    }

    /**
     * Construye un árbol y sus subárboles recursivamente a partir del String
     * entregado como parámetro.
     * 
     * @param arbolStr  String que representa el árbol a construir.
     * @param globalIdx Índice global en el String que indica desde donde se
     *                  comienza a construir el árbol o subárbol.
     * @return {@link ArbolLg} que representa el String dado.
     */
    public static ArbolLg consArbolLg(String arbolStr, AtomicInteger globalIdx) {

        ArbolLg A = null;
        ArbolLg subArbol;
        NodoLg nodoX;
        String atomo;

        int charIdx = globalIdx.get();
        atomo = "";

        // a(b(c, d(e)), f, g(h, i(j, k(l)), m, n))
        // loop para recorrer el string
        do {

            switch (arbolStr.charAt(charIdx)) {

                case '(': // lo anterior debió ser un átomo

                    nodoX = new NodoLg();

                    // si el arbol no se ha creado
                    if (A == null) {

                        // se asigna el átomo leído anteriormente, y se crea el árbol con el nodoX
                        nodoX.setSw(0);
                        nodoX.setDato(atomo.trim());
                        A = new ArbolLg(nodoX);

                    } else { // el arbol ya esta creado, y por tanto su raíz está presente

                        nodoX.setSw(1);

                        // se crea el subArbol a partir de la posición del átomo leído anteriormente
                        globalIdx.set(charIdx - atomo.length());
                        subArbol = consArbolLg(arbolStr, globalIdx);

                        // globalIdx es la posición en la cual se deben seguir leyendo los datos, en
                        // otras palabras, es donde el subArbol anterior termino de leer, por tanto no
                        // hay que leer los mismos datos
                        charIdx = globalIdx.get();

                        // se asigna el campo Dato del nodoX al subArbol creado.
                        nodoX.setDato(subArbol);

                        // se conecta ese subArbol al ultimo nodo
                        A.conectar(nodoX, A.getUltimoNodo());

                    }

                    // se reinicia el átomo
                    atomo = "";
                    break;

                case ',': // lo anterior pudo ser un átomo (sin hijos), o un subArbol (un paréntesis de
                          // cierre)

                    // si lo anterior fue un subArbol, ya se debió haber conectado

                    // si fue un átomo, o sea, el string atomo no está vacío
                    if (atomo.trim().length() == 0) {
                        break;
                    }

                    // se crea un nuevo nodo con ese átomo
                    nodoX = new NodoLg(0, atomo.trim(), null);

                    // se conecta
                    A.conectar(nodoX, A.getUltimoNodo());

                    // se reinicia el átomo
                    atomo = "";
                    break;

                case ')': // lo anterior pudo ser un átomo o un subArbol

                    // si fue un subArbol, ya se debió haber conectado

                    // si fue un átomo
                    if (atomo.trim().length() > 0) {

                        // se crea un nuevo nodo con ese átomo y se conecta a este arbol
                        nodoX = new NodoLg(0, atomo.trim(), null);
                        A.conectar(nodoX, A.getUltimoNodo());

                    }

                    globalIdx.set(charIdx);
                    return A;

                default:

                    // se añade el carácter al átomo
                    atomo = atomo.concat(arbolStr.substring(charIdx, charIdx + 1));
                    break;

            }

            // se avanza al siguiente carácter
            charIdx++;

        } while (charIdx < arbolStr.length());
        // mientras charIdx este dentro del rango de arbolStr

        // globalIdx será la continuación en el string del arbol padre que contiene
        // este arbol
        globalIdx.set(charIdx);
        if (A == null && atomo.trim().length() > 0) {

            A = new ArbolLg(new NodoLg(0, atomo.trim(), null));
            
        }

        return A;

    }

    @Override
    public NodoLg find(Object d) {
        return super.find(d);
    }

    @Override
    public Nodo getParent(Nodo child) {

        if (child == null || child == getRoot()) {
            return null;
        }

        ArbolLg subArbol;
        Nodo subResult;
        NodoLg nodoX, root;

        root = getRoot();
        nodoX = (NodoLg) root.getLiga();

        while (nodoX != null) {

            if (nodoX == child) {

                return root;

            }

            if (nodoX.getSw() == 1) {

                subArbol = (ArbolLg) nodoX.getDato();
                if (subArbol.getRoot() == child) {
                    return root;
                }

                subResult = subArbol.getParent(child);

                if (subResult != null) {
                    return subResult;
                }

            }

            nodoX = (NodoLg) nodoX.getLiga();

        }

        return null;

    }

    @Override
    public List<Object> getAncestors(Nodo nodoX) {

        if (nodoX == null) {
            return null;
        }

        List<Object> ancestors, subAncestors;
        ArbolLg subArbol;
        NodoLg root, child;

        ancestors = new ArrayList<Object>();

        root = getRoot();

        if (root == nodoX) {
            ancestors.add(root.getDato());
        }

        child = (NodoLg) root.getLiga();

        while (child != null) {

            if (child.getSw() == 1) {

                subArbol = (ArbolLg) child.getDato();
                subAncestors = subArbol.getAncestors(nodoX);

                if (subAncestors.size() > 0 && subAncestors.get(subAncestors.size() - 1) == nodoX.getDato()) {

                    subAncestors.add(0, root.getDato());
                    return subAncestors;

                }

            } else {

                if (child == nodoX) {

                    ancestors.add(root.getDato());
                    ancestors.add(child.getDato());
                    return ancestors;

                }

            }

            child = (NodoLg) child.getLiga();

        }

        return ancestors;

    }

    @Override
    public int getHeight() {

        ArbolLg subArbol;
        int max, subHeight;

        max = 1;

        NodoLg nodoX = (NodoLg) getPrimerNodo();

        if (nodoX.getLiga() != null) {

            max = 2;

        }

        while (nodoX != null) {

            if (nodoX.getSw() == 1) {

                subArbol = (ArbolLg) nodoX.getDato();
                subHeight = subArbol.getHeight();
                max = Math.max(max, subHeight + 1);

            }

            nodoX = (NodoLg) nodoX.getLiga();

        }

        return max;

    }

    /**
     * Calcula y retorna el máximo grado de este árbol de forma no recursiva.
     * 
     * @return Máximo grado de este árbol.
     */
    public int getDegreeNonRecursive() {

        if (getRoot().getLiga() == null) {
            return 0;
        }

        int maxDegree, count;

        ArbolLg subArbol;
        NodoLg nodoX, last;
        Stack<NodoLg> stack = new Stack<>();

        nodoX = (NodoLg) getPrimerNodo().getLiga();
        last = nodoX;

        maxDegree = 0;
        count = 0;

        while (nodoX != null) {

            count++;
            if (nodoX.getSw() == 1) {

                stack.push(nodoX);

            }

            if (nodoX.getLiga() == null && !stack.isEmpty()) {

                maxDegree = Math.max(maxDegree, count);
                count = 0;

                last = stack.pop();
                subArbol = (ArbolLg) last.getDato();
                nodoX = (NodoLg) subArbol.getPrimerNodo(); // nunca es null

            }

            nodoX = (NodoLg) nodoX.getLiga();

        }

        return maxDegree;

    }

    @Override
    public int getMaxDegree() {

        int degree, subDegree, maxSubDegree;
        ArbolLg subArbol;

        NodoLg nodoX = (NodoLg) getPrimerNodo().getLiga();

        degree = 0;
        maxSubDegree = 0;

        while (nodoX != null) {

            degree++;
            if (nodoX.getSw() == 1) {

                subArbol = (ArbolLg) nodoX.getDato();
                subDegree = subArbol.getMaxDegree();
                maxSubDegree = Math.max(maxSubDegree, subDegree);

            }

            nodoX = (NodoLg) nodoX.getLiga();

        }

        degree = Math.max(degree, maxSubDegree);

        return degree;

    }

    @Override
    public int countLeafs() {

        int count, subCount;

        ArbolLg subArbol;
        NodoLg nodoX = (NodoLg) getRoot().getLiga(); // root == primerNodo

        count = 0;

        while (nodoX != null) {

            if (nodoX.getSw() == 1) {

                subArbol = (ArbolLg) nodoX.getDato();
                subCount = subArbol.countLeafs();
                count += subCount;

            } else {

                count++;

            }

            nodoX = (NodoLg) nodoX.getLiga();

        }

        return count;

    }

    /**
     * Construye una representación de este árbol en el {@link StringBuilder}
     * <b>sb</b> de forma recursiva.
     * 
     * @param sb          {@link StringBuilder} en el cual se contruye la
     *                    representación.
     * @param prefix      Prefijo que se añadirá a cada hijo de este árbol o
     *                    subárbol.
     * @param branchWidth Ancho de las ramas del árbol.
     */
    protected void consTreeRepr(StringBuilder sb, String prefix, int branchWidth) {

        if (sb == null) {

            throw new IllegalArgumentException("sb cannot be null");

        }

        ArbolLg subArbol;
        NodoLg nodoX;

        String newPrefix;

        sb.append(getRoot().getDato().toString());
        sb.append("\n");

        nodoX = (NodoLg) getRoot().getLiga();

        newPrefix = prefix.concat("├").concat("─".repeat(branchWidth));

        // └ ┘ ┌ ┐ ─ │ ┼ ┴ ┬ ┤ ├
        while (nodoX != null) {

            if (nodoX == getUltimoNodo()) {

                newPrefix = prefix.concat("└").concat("─".repeat(branchWidth));

            }

            sb.append(newPrefix);

            if (nodoX.getSw() == 1) {

                newPrefix = prefix.concat((nodoX == getUltimoNodo()) ? " " : "│");
                newPrefix = newPrefix.concat(" ".repeat(branchWidth));

                subArbol = (ArbolLg) nodoX.getDato();
                subArbol.consTreeRepr(sb, newPrefix, branchWidth);

                newPrefix = prefix.concat("├").concat("─".repeat(branchWidth));

            } else {

                sb.append(nodoX.getDato().toString());
                sb.append("\n");

            }

            nodoX = (NodoLg) nodoX.getLiga();

        }

    }

    /**
     * {@inheritDoc}
     * 
     * @return {@inheritDoc} (String con átomos, parentesis y comas)
     */
    @Override
    public String toString() {

        String s = "";

        NodoLg nodoX = (NodoLg) getPrimerNodo();
        ArbolLg subArbol;

        while (!finDeRecorrido(nodoX)) {

            if (getRoot().getLiga() == nodoX) {

                s = s.concat("(");

            } else if (nodoX != getRoot()) {

                s = s.concat(", ");

            }

            if (nodoX.getSw() == 0) {

                s = s.concat(nodoX.getDato().toString());

            } else {

                subArbol = (ArbolLg) nodoX.getDato();
                s = s.concat(subArbol.toString());

            }

            if (nodoX == getUltimoNodo() && nodoX != getRoot()) {

                s = s.concat(")");

            }
            nodoX = (NodoLg) nodoX.getLiga();

        }

        return s;

    }

    /**
     * Construye la representación gráfica en String de este árbol.
     * 
     * @param branchWidth Ancho de
     * @return Representación gráfica de este árbol como String.
     * @see #consTreeRepr(StringBuilder, String, int)
     */
    public String treeRepr(int branchWidth) {

        StringBuilder repr = new StringBuilder();

        consTreeRepr(repr, "", branchWidth);

        return repr.toString();

    }

    /**
     * Imprime la representación de este árbol en el {@link PrintStream} dado como
     * parámetro.
     * 
     * @param branchWidth Ancho de las ramas del árbol.
     */
    public void showAsTreeRepr(int branchWidth) {

        System.out.println(treeRepr(branchWidth));

    }

    /**
     * Cuenta las hojas del árbol de forma no recursiva.
     * 
     * @return Número de hojas del árbol.
     * @see #countLeafs
     */
    public int countLeafs2() { // contar hojas no recursivo

        if (root == null) {
            return 0;
        }

        if (root.getLiga() == null) {
            return 1;
        }

        int count = 0;
        Stack<ArbolLg> stack = new Stack<>();
        ArbolLg subArbol;
        NodoLg nodoX = (NodoLg) getRoot().getLiga(); // root == primerNodo

        while (nodoX != null) {

            if (nodoX.getSw() == 0) {
                count = count + 1;

            } else {
                subArbol = (ArbolLg) nodoX.getDato();
                stack.push(subArbol);

            }

            if (nodoX.getLiga() == null) {
                if (!stack.isEmpty()) {
                    subArbol = (ArbolLg) stack.pop();
                    nodoX = (NodoLg) subArbol.getRoot();
                }

            }

            nodoX = (NodoLg) nodoX.getLiga();
        }

        return count;
    }

    /**
     * Busca el nodo que contiene el dato <b>d</b> y retorna su grado.
     * <p>
     * Si no se encuentra el nodo, se lanzará una exception.
     * 
     * @param d Dato a buscar.
     * @return Grado del nodo con dato <b>d</b>.
     * @throws Exception Si no se encuentra el nodo con dato <b>d</b>.
     */
    public int degreeOf(Object d) throws Exception {

        if (find(d) == null) {
            throw new Exception(String.format("Nodo con dato '%s' no encontrado.", d.toString()));
        }

        int count = 0;
        ArbolLg subArbol = null;
        NodoLg nodoX = (NodoLg) getRoot(); // root == primerNodo

        if (nodoX.getDato().equals(d)) {

            nodoX = (NodoLg) nodoX.getLiga();

            while (nodoX != null) {
                count = count + 1;
                nodoX = (NodoLg) nodoX.getLiga();

            }
            return count;
        }

        nodoX = (NodoLg) nodoX.getLiga();

        while (nodoX != null) {

            if (nodoX.getSw() == 1) {

                subArbol = (ArbolLg) nodoX.getDato();
                try {
                    count = count + subArbol.degreeOf(d);
                } catch (Exception e) {
                }

            }
            nodoX = (NodoLg) nodoX.getLiga();

        }

        return count;
    }

    /**
     * Calcula el nivel relativo en este árbol del nodo con dato <b>d</b>.
     * 
     * @param d Dato a buscar.
     * @return Nivel relativo a este árbol del nodo con dato <b>d</b>.
     * @throws Exception Si no se encuenta el nodo con dato <b>d</b>.
     */
    public int levelOf(Object d) throws Exception {

        if (find(d) == null) {
            throw new Exception(String.format("Nodo con dato '%s' no encontrado.", d.toString()));
        }

        int count = 1;
        ArbolLg subArbol = null;
        NodoLg nodoX = (NodoLg) getRoot(); // root == primerNodo

        if (root.getDato().equals(d)) {

            return 1;
        }

        nodoX = (NodoLg) nodoX.getLiga();

        while (nodoX != null) {

            if (nodoX.getSw() == 0 && nodoX.getDato().equals(d)) {
                return count + 1;
            }

            if (nodoX.getSw() == 1) {
                subArbol = (ArbolLg) nodoX.getDato();

                if (subArbol.getRoot().equals(d)) {
                    return count + 1;

                }
                try {
                    count = count + subArbol.levelOf(d);
                } catch (Exception e) {
                }

            }
            nodoX = (NodoLg) nodoX.getLiga();

        }

        return count;

    }

    public static void main(String[] args) {

        String arbolStr = "a(b(c, d(e)), f, g(h, i(j, k(l(x(z, o(p))))), m, n(1, 2, 3, 4, 5)))";

        ArbolLg A;
        try {
            A = Arboles.consArbolLg(arbolStr);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        A.show();

        A.showAsTreeRepr(2);

        A.showAsLgRepr(1);

        NodoLg B = A.find("a");

        System.out.println("ancestors: " + A.getAncestors(B));
        System.out.println("leafs: " + A.countLeafs2());

        try {
            System.out.println("degree: " + A.degreeOf("g"));
            System.out.println("level: " + A.levelOf("gf"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
