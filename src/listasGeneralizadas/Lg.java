package listasGeneralizadas;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

import listasLigadas.LSL;
import nodos.NodoLg;

public class Lg extends LSL {

    /**
     * Construye una lista generalizada vacía.
     */
    public Lg() {
        super();
    }

    /**
     * Contruye una lista generalizada a partir del String dado.
     * 
     * @param strLg     Lista generalizada en forma de String.
     * @param globalIdx Índice global con el cual se recorre el String de forma
     *                  recursiva.
     * @return {@link Lg} construida a partir del String dado.
     */
    private static Lg consLg(String strLg, AtomicInteger globalIdx) {

        if (strLg.charAt(globalIdx.get()) != '(') {

            throw new IllegalArgumentException("Malformed lgStr");

        }

        Lg A = new Lg();
        Lg subLg;
        NodoLg nodoX;
        String atomo;

        int charIdx = globalIdx.get() + 1;
        atomo = "";

        while (charIdx < strLg.length() && strLg.charAt(charIdx - 1) != ')') {

            switch (strLg.charAt(charIdx)) {

                case '(':

                    globalIdx.set(charIdx);
                    subLg = consLg(strLg, globalIdx);
                    charIdx = globalIdx.get();
                    nodoX = new NodoLg(1, subLg, null);
                    A.conectar(nodoX, A.getUltimoNodo());
                    atomo = "";
                    break;

                case ',':

                    if (atomo.trim().length() == 0) {
                        break;
                    }
                    nodoX = new NodoLg(0, atomo.trim(), null);
                    A.conectar(nodoX, A.getUltimoNodo());
                    atomo = "";
                    break;

                case ')':

                    if (atomo.trim().length() == 0) {
                        break;
                    }
                    nodoX = new NodoLg(0, atomo.trim(), null);
                    A.conectar(nodoX, A.getUltimoNodo());
                    atomo = "";
                    break;

                default:

                    atomo = atomo.concat(strLg.substring(charIdx, charIdx + 1));
                    break;

            }

            charIdx++;

        }

        globalIdx.set(charIdx);
        return A;

    }

    /**
     * Contruye una lista generalizada llamando al método
     * {@link #consLg(String, AtomicInteger)}.
     * 
     * @param strLg Representación en String de la lista generalizada.
     * @return {@link Lg} contruida a partir de <b>strLg</b>.
     */
    public static Lg consLg(String strLg) {

        return consLg(strLg, new AtomicInteger(0));

    }

    /**
     * Busca el {@link NodoLg} que contiene el dato <b>d</b>.
     * 
     * @param d Dato a buscar.
     * @return {@link NodoLg} con el dato <b>d</b>. Si no se encuentra el dato, se
     *         retornará {@code null}.
     */
    public NodoLg find(Object d) {

        Lg subLg;
        NodoLg nodoX, subResult;

        nodoX = (NodoLg) getPrimerNodo();

        while (nodoX != null) {

            if (nodoX.getSw() == 1) {

                subLg = (Lg) nodoX.getDato();
                subResult = subLg.find(d);

                if (subResult != null) {
                    return subResult;
                }

            } else {

                if (nodoX.getDato().equals(d)) {
                    return nodoX;
                }

            }

            nodoX = (NodoLg) nodoX.getLiga();

        }

        return null;

    }

    /**
     * Calcula y retorna el número de sublistas generalizadas de esta lista
     * generalizada.
     * <p>
     * Es lo mismo que contar el número total de nodos con switch igual a 1.
     * 
     * @return Número total de sublistas generalizadas.
     */
    public int countNumSubLg() {

        int count = 0;

        NodoLg nodoX = (NodoLg) getPrimerNodo();
        Lg subLg;

        while (nodoX != null) {

            if (nodoX.getSw() == 1) {

                subLg = (Lg) nodoX.getDato();
                count = count + 1 + subLg.countNumSubLg();

            }

            nodoX = (NodoLg) nodoX.getLiga();

        }

        return count;

    }

    /**
     * Construye la representación gráfica de esta lista generalizada en la lista
     * dada como parámetro.
     * <p>
     * Si <b>lines</b> es {@code null} se cancelará la contrucción.
     * 
     * @param lines      Lista de {@link StringBuilder} en la cual se construye la
     *                   representación.
     * @param spacing    Espaciado inicial que se aplicará a cada línea.
     * @param parentLine Índice de la línea la linea de la lista que debe apuntar
     *                   hacía está sublista. (Esto se usa para llamar al método de
     *                   forma recursiva)
     * @param fieldWidth Ancho de los campos de los nodos. Si
     *                   {@code fieldWidth == 0} cada nodo calculará su propio
     *                   tamaño según su contenido.
     */
    protected void consLgRepr(List<StringBuilder> lines, int spacing, int parentLine, int fieldWidth) {

        if (lines == null)
            return;

        List<StringBuilder> nodoXRepr;
        StringBuilder topLine, line, bottomLine;
        topLine = new StringBuilder(" ".repeat(spacing));
        line = new StringBuilder(" ".repeat(spacing));
        bottomLine = new StringBuilder(" ".repeat(spacing));

        Stack<NodoLg> nodosStack = new Stack<NodoLg>();
        Stack<Integer> nodoSpacing = new Stack<Integer>();
        NodoLg nodoX = (NodoLg) getPrimerNodo();

        Lg subLg;

        int spacingX = spacing;

        // └ ┘ ┌ ┐ ─ │ ┼ ┴ ┬ ┤ ├
        while (nodoX != null) {

            nodoXRepr = nodoX.nodeRepr(false, fieldWidth);
            final int w = (nodoXRepr.get(0).length() - 4) / 3;

            if (nodoX.getSw() == 1) {

                nodosStack.push(nodoX);
                nodoSpacing.push(spacingX + 3 + w * 2);

            }

            spacingX += 2 + nodoXRepr.get(0).length();

            topLine.append("  ").append(nodoXRepr.get(0));
            line.append(String.format("%s>".concat(nodoXRepr.get(1).toString()), nodoX == getPrimerNodo() ? "└" : "─"));
            bottomLine.append("  ").append(nodoXRepr.get(2));

            nodoX = (NodoLg) nodoX.getLiga();

        }

        lines.add(topLine);
        lines.add(line);
        lines.add(bottomLine);

        int currentLine = lines.size() - 2;
        int arrowIdx = spacing;
        int arrowLineIdx = parentLine + 2;

        StringBuilder sbTemp;

        while (arrowLineIdx < currentLine) {

            sbTemp = lines.get(arrowLineIdx);
            sbTemp.replace(arrowIdx, arrowIdx + 1, "│");

            arrowLineIdx++;

        }

        while (!nodosStack.isEmpty()) {

            nodoX = nodosStack.pop();
            spacingX = nodoSpacing.pop();

            subLg = (Lg) nodoX.getDato();
            subLg.consLgRepr(lines, spacingX, currentLine,
                    fieldWidth);

        }

    }

    @Override
    public String toString() {

        String s = "(";

        NodoLg nodoX = (NodoLg) getPrimerNodo();
        Lg subLg;

        while (!finDeRecorrido(nodoX)) {

            if (nodoX != getPrimerNodo()) {

                s = s.concat(", ");

            }

            if (nodoX.getSw() == 0) {

                s = s.concat(nodoX.getDato().toString());

            } else {

                subLg = (Lg) nodoX.getDato();
                s = s.concat(subLg.toString());

            }

            nodoX = (NodoLg) nodoX.getLiga();

        }

        s = s.concat(")");

        return s;

    }

    /**
     * Retorna la representación gráfica de esta lista generalizada.
     * 
     * @param fieldWidth Ancho de los campos de los nodos.
     * @return Representación en forma de String.
     */
    public String lgRepr(int fieldWidth) {

        StringBuilder repr = new StringBuilder();

        ArrayList<StringBuilder> lines = new ArrayList<StringBuilder>();
        consLgRepr(lines, 0, 0, fieldWidth);

        lines.forEach((sb) -> {
            repr.append(sb).append("\n");
        });

        return repr.toString();
    }

    /**
     * Muestra la representación grádica de esta lista generalizada por consola.
     * 
     * @param fieldWidth Ancho de los campos de los nodos.
     */
    public void showAsLgRepr(int fieldWidth) {

        System.out.println(lgRepr(fieldWidth));

    }

    public static void main(String[] args) {

        Lg A = consLg("((a, b, c, def), (a, (a, b, c, d), f, (a, b, c, d)), xxx, (a, b, c, d))");

        A.show();

        System.out.println();

        A.showAsLgRepr(0);

    }

}
