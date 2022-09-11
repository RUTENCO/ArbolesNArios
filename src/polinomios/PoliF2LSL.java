package polinomios;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import listasLigadas.listas.LSL;
import nodos.NodoSimple;

/**
 * Clase que representa Polinomios en La forma 2, y en Listas Simplemente
 * Ligadas. Veáse {@link LSL}.
 */
public class PoliF2LSL extends LSL {

    /**
     * Variable o incógnita del polinomio.
     */
    private char variable;

    /**
     * Constructor. Contruye esta lista ligada llamando al contructor de su padre,
     * ({@code super()}).
     * 
     * @param variable Carácter a asignar como variable de este polinomio.
     */
    public PoliF2LSL(char variable) {

        super();
        this.variable = variable;

    }

    /**
     * Constructor. Contruye esta lista ligada llamando al contructor de su padre,
     * ({@code super()}). Además lee el String <b>poli</b> para contruir este
     * polinomio, siempre que esté bien formado.
     * 
     * @param poli     String que representa el polinomio.
     * @param variable Carácter a asignar como variable de este polinomio.
     */
    public PoliF2LSL(String poli, char variable) {

        super();
        this.variable = variable;

        Termino t;
        NodoSimple nodoX, nodoAntX;
        nodoAntX = null;

        ArrayList<String> terms = new ArrayList<String>();

        int i, j;
        i = 0;
        j = 0;
        while (j < poli.length()) {

            if ((poli.charAt(j) != '+' && poli.charAt(j) != '-') || i == j) {
                j++;
                continue;
            }

            terms.add(poli.substring(i, j));
            i = j;
            j++;

        }

        terms.add(poli.substring(i, j));

        for (String term : terms) {

            t = stringToTerm(term);
            nodoX = new NodoSimple(t);
            conectar(nodoX, nodoAntX);
            nodoAntX = nodoX;

        }

    }

    /**
     * Retorna el grado del polinomio, el cual se encuentra en el exponente del
     * término en el primer nodo, ({@code getPrimerNodo().getDato().getExp()})
     * 
     * @return Grado del polinomio.
     */
    public int getGrade() {
        return ((Termino) getPrimerNodo().getDato()).getExp();
    }

    /**
     * retorna el tamaño de la lista, el cual es igual al número de Términos
     * diferentes de cero.
     * 
     * @return Tamaño de la lista.
     */
    public int getNumElementosNoCero() {
        return getSize();
    }

    /**
     * Retorna la variable con la que se representa el polinomio.
     * 
     * @return {@link #variable}.
     */
    public char getVariable() {
        return variable;
    }

    public boolean isZero() {
        return isEmpty();
    }

    /**
     * Polinomio nulo
     * 
     * @return {@link PoliF2LSL} que representa el polinomio nulo o cero.
     */
    public static PoliF2LSL zero(char var) {
        return new PoliF2LSL(var);
    }

    /**
     * Realiza una suma entre este polinomio y el polinomio dado como parámetro,
     * luego retorna el resultado en otro polinomio de la clase {@link PoliF2LSL}.
     * 
     * @param poliB {@link PoliF2LSL} a sumar a este polinomio.
     * @return {@link PoliF2LSL} resultado de la suma.
     */
    public PoliF2LSL sum(PoliF2LSL poliB) {

        assert (getVariable() == poliB.getVariable()) : "No es posible sumar dos polinomios con distintas variables.";

        PoliF2LSL poliC = new PoliF2LSL(getVariable());

        NodoSimple nodoA, nodoB, nodoC;
        Termino termA, termB, termC;
        double s;
        nodoA = getPrimerNodo();
        nodoB = poliB.getPrimerNodo();

        while (!finDeRecorrido(nodoA) && !poliB.finDeRecorrido(nodoB)) {

            termA = (Termino) nodoA.getDato();
            termB = (Termino) nodoB.getDato();

            if (termA.getExp() > termB.getExp()) {

                termC = new Termino(termA.getCoef(), termA.getExp());
                nodoC = new NodoSimple(termC);
                poliC.add(nodoC);
                nodoA = nodoA.getLiga();

            } else if (termA.getExp() < termB.getExp()) {

                termC = new Termino(termB.getCoef(), termB.getExp());
                nodoC = new NodoSimple(termC);
                poliC.add(nodoC);
                nodoB = nodoB.getLiga();

            } else {

                s = termA.getCoef() + termB.getCoef();
                if (s != 0) {

                    termC = new Termino(s, termA.getExp());
                    nodoC = new NodoSimple(termC);
                    poliC.add(nodoC);

                }

                nodoA = nodoA.getLiga();
                nodoB = nodoB.getLiga();

            }

        }
        while (!finDeRecorrido(nodoA)) {

            termA = (Termino) nodoA.getDato();
            termC = new Termino(termA.getCoef(), termA.getExp());
            nodoC = new NodoSimple(termC);
            poliC.add(nodoC);
            nodoA = nodoA.getLiga();

        }
        while (!poliB.finDeRecorrido(nodoB)) {

            termB = (Termino) nodoB.getDato();
            termC = new Termino(termB.getCoef(), termB.getExp());
            nodoC = new NodoSimple(termC);
            poliC.add(nodoC);
            nodoB = nodoB.getLiga();

        }

        return poliC;

    }

    public PoliF2LSL sumF2(PoliF2 poliF2_B) {

        assert (getVariable() == poliF2_B.getVariable())
                : "No es posible sumar dos polinomios con distintas variables.";

        PoliF2LSL poliF2LSL_C = new PoliF2LSL(getVariable());

        NodoSimple nodoA, nodoC;
        Termino termA, termB, termC;
        int j;
        double s;

        nodoA = getPrimerNodo();
        j = 1;

        while (!finDeRecorrido(nodoA) && j <= poliF2_B.getNumElementosNoCero()) {

            termA = (Termino) nodoA.getDato();
            termB = poliF2_B.getTerm(j);

            switch (Integer.compare(termA.getExp(), termB.getExp())) {
                case +1:

                    termC = new Termino(termA.getCoef(), termA.getExp());
                    nodoC = new NodoSimple(termC);
                    poliF2LSL_C.add(nodoC);
                    nodoA = nodoA.getLiga();
                    break;

                case -1:

                    termC = new Termino(termB.getCoef(), termB.getExp());
                    nodoC = new NodoSimple(termC);
                    poliF2LSL_C.add(nodoC);
                    j++;
                    break;

                case 0:

                    s = termA.getCoef() + termB.getCoef();
                    if (s != 0) {

                        termC = new Termino(s, termA.getExp());
                        nodoC = new NodoSimple(termC);
                        poliF2LSL_C.add(nodoC);

                    }

                    nodoA = nodoA.getLiga();
                    j++;
                    break;

            }

        }
        while (!finDeRecorrido(nodoA)) {

            termA = (Termino) nodoA.getDato();
            termC = new Termino(termA.getCoef(), termA.getExp());

            nodoC = new NodoSimple(termC);
            poliF2LSL_C.add(nodoC);
            nodoA = nodoA.getLiga();

        }
        while (j <= poliF2_B.getNumElementosNoCero()) {

            termB = poliF2_B.getTerm(j);
            termC = new Termino(termB.getCoef(), termB.getExp());

            nodoC = new NodoSimple(termC);
            poliF2LSL_C.add(nodoC);
            j++;

        }

        return poliF2LSL_C;

    }

    public static PoliF2LSL sumF1_F2(PoliF1 poliF1_A, PoliF2 poliF2_B) {

        assert (poliF1_A.getVariable() == poliF2_B.getVariable())
                : "No es posible sumar dos polinomios con diferentes variables.";

        PoliF2LSL poliF2LSL_C = new PoliF2LSL(poliF1_A.getVariable());

        double coefA, coefB, coefC;
        int i, j, expA, expB;
        Termino termB, termC;
        NodoSimple nodoC;

        i = 1;
        j = 1;

        while (i <= poliF1_A.getGrade() + 1 && j <= poliF2_B.getNumElementosNoCero()) {

            coefA = poliF1_A.getCoef(i);
            expA = poliF1_A.getExp(i);

            termB = poliF2_B.getTerm(j);
            coefB = termB.getCoef();
            expB = termB.getExp();

            if (expA > expB) {

                termC = new Termino(coefA, expA);
                nodoC = new NodoSimple(termC);
                poliF2LSL_C.add(nodoC);
                i++;

            } else if (expA < expB) {

                termC = new Termino(coefB, expB);
                nodoC = new NodoSimple(termC);
                poliF2LSL_C.add(nodoC);
                j++;

            } else {

                coefC = coefA + coefB;
                if (coefC != 0) {

                    termC = new Termino(coefC, expA);
                    nodoC = new NodoSimple(termC);
                    poliF2LSL_C.add(nodoC);

                }

                i++;
                j++;

            }

        }
        while (i <= poliF1_A.getGrade() + 1) {

            coefA = poliF1_A.getCoef(i);
            expA = poliF1_A.getExp(i);

            termC = new Termino(coefA, expA);
            nodoC = new NodoSimple(termC);
            poliF2LSL_C.add(nodoC);
            i++;

        }
        while (j <= poliF2_B.getNumElementosNoCero()) {

            termB = poliF2_B.getTerm(j);
            coefB = termB.getCoef();
            expB = termB.getExp();

            termC = new Termino(coefB, expB);
            nodoC = new NodoSimple(termC);
            poliF2LSL_C.add(nodoC);
            j++;

        }

        return poliF2LSL_C;

    }

    public static void sum(PoliF2LSL poliA, PoliF2LSL poliB) {

        NodoSimple nodoA, nodoAntA, nodoB, nodoAntB;
        Termino termA, termB;
        double coefA, coefB, s;
        int expA, expB;

        nodoA = poliA.getPrimerNodo();
        nodoB = poliB.getPrimerNodo();

        nodoAntA = null;
        nodoAntB = null;

        while (!poliA.finDeRecorrido(nodoA) && !poliB.finDeRecorrido(nodoB)) {

            termA = (Termino) nodoA.getDato();
            coefA = termA.getCoef();
            expA = termA.getExp();

            termB = (Termino) nodoB.getDato();
            coefB = termB.getCoef();
            expB = termB.getExp();

            if (expA > expB) {

                nodoAntA = nodoA;
                nodoA = nodoA.getLiga();

            } else if (expA < expB) {

                nodoA = nodoB;
                nodoB = nodoA.getLiga();
                poliB.desconectar(nodoA, nodoAntB);
                poliA.conectar(nodoA, nodoAntA);
                nodoAntA = nodoA;
                nodoA = nodoA.getLiga();

            } else {

                s = coefA + coefB;
                if (s != 0) {

                    termA.setCoef(s);
                    nodoA.setDato(termA);
                    nodoAntA = nodoA;
                    nodoA = nodoA.getLiga();

                } else {

                    poliA.desconectar(nodoA, nodoAntA);

                    if (nodoAntA != null) {

                        nodoA = nodoAntA.getLiga();

                    } else {

                        nodoA = poliA.getPrimerNodo();

                    }

                }

                poliB.desconectar(nodoB, nodoAntB);

                if (nodoAntB != null) {

                    nodoB = nodoAntB.getLiga();

                } else {

                    nodoB = poliB.getPrimerNodo();

                }

            }

        }

        while (!poliB.finDeRecorrido(nodoB)) {

            nodoAntA = poliA.getUltimoNodo();
            poliB.desconectar(nodoB, nodoAntB);
            poliA.conectar(nodoB, nodoAntA);
            nodoAntB = nodoB;
            nodoB = nodoB.getLiga();

        }

    }

    /**
     * Realiza una resta entre este polinomio y el polinomio dado como parámetro,
     * luego retorna el resultado en otro polinomio de la clase {@link PoliF2LSL}.
     * 
     * @param poliB {@link PoliF2LSL} a restar a este polinomio.
     * @return {@link PoliF2LSL} resultado de la resta.
     */
    public PoliF2LSL substract(PoliF2LSL poliB) {

        assert (getVariable() == poliB.getVariable()) : "No es posible restar dos polinomios con distintas variables.";

        PoliF2LSL poliC = new PoliF2LSL(getVariable());

        NodoSimple nodoA, nodoB, nodoC;
        Termino termA, termB, termC;
        double r;
        nodoA = getPrimerNodo();
        nodoB = poliB.getPrimerNodo();

        while (!finDeRecorrido(nodoA) && !poliB.finDeRecorrido(nodoB)) {

            termA = (Termino) nodoA.getDato();
            termB = (Termino) nodoB.getDato();

            if (termA.getExp() > termB.getExp()) {

                termC = new Termino(termA.getCoef(), termA.getExp());
                nodoC = new NodoSimple(termC);
                poliC.add(nodoC);
                nodoA = nodoA.getLiga();

            } else if (termA.getExp() < termB.getExp()) {

                termC = new Termino(-termB.getCoef(), termB.getExp());
                nodoC = new NodoSimple(termC);
                poliC.add(nodoC);
                nodoB = nodoB.getLiga();

            } else {

                r = termA.getCoef() - termB.getCoef();
                if (r != 0) {

                    termC = new Termino(r, termA.getExp());
                    nodoC = new NodoSimple(termC);
                    poliC.add(nodoC);

                }

                nodoA = nodoA.getLiga();
                nodoB = nodoB.getLiga();

            }

        }
        while (!finDeRecorrido(nodoA)) {

            termA = (Termino) nodoA.getDato();
            termC = new Termino(termA.getCoef(), termA.getExp());
            nodoC = new NodoSimple(termC);
            poliC.add(nodoC);
            nodoA = nodoA.getLiga();

        }
        while (!poliB.finDeRecorrido(nodoB)) {

            termB = (Termino) nodoB.getDato();
            termC = new Termino(-termB.getCoef(), termB.getExp());
            nodoC = new NodoSimple(termC);
            poliC.add(nodoC);
            nodoB = nodoB.getLiga();

        }

        return poliC;

    }

    /**
     * Multiplica este polinomio con el polinomio dado como parámetro, usando el
     * método {@link #smult(Termino)} como apoyo, y retorna el resultado como un
     * polinomio de la clase {@link PoliF2LSL}.
     * 
     * @param poliB {@link PoliF2LSL} multiplicador.
     * @return {@link PoliF2LSL} resultado de la multiplicación,
     */
    public PoliF2LSL multiply(PoliF2LSL poliB) {

        assert (getVariable() == poliB.getVariable())
                : "No se pueden multiplicar 2 polinomios con distinta variable. (de momento)";

        PoliF2LSL poliC = zero(getVariable());

        if (isZero() || poliB.isZero()) {
            return poliC;
        }

        NodoSimple nodoB;
        Termino termB;

        nodoB = poliB.getPrimerNodo();

        while (!poliB.finDeRecorrido(nodoB)) {

            termB = (Termino) nodoB.getDato();
            poliC = poliC.sum(smult(termB));
            nodoB = nodoB.getLiga();

        }

        return poliC;

    }

    public static PoliF2LSL multiplyF1_F2(PoliF1 poliF1_A, PoliF2 poliF2_B) {

        assert (poliF1_A.getVariable() == poliF2_B.getVariable())
                : "No se pueden multiplicar 2 polinomios con distinta variable. (de momento)";

        PoliF2LSL poliF2LSL_C = zero(poliF1_A.getVariable());

        if (poliF1_A.isZero() || poliF2_B.isZero()) {
            return poliF2LSL_C;
        }

        double coefA;
        int i, expA;
        Termino termA;

        for (i = 1; i <= poliF1_A.getGrade() + 1; i++) {

            coefA = poliF1_A.getCoef(i);
            expA = poliF1_A.getExp(i);

            termA = new Termino(coefA, expA);

            poliF2LSL_C = poliF2LSL_C.sumF2(poliF2_B.smult(termA));

        }

        return poliF2LSL_C;

    }

    /**
     * Multiplica todo este polinomio por el término entregado como parámetro, y
     * devuelde un {@link PoliF2LSL} como resultado.
     * 
     * @param term Multiplicador.
     * @return {@link PoliF2LSL} resultado de la multiplicacion.
     */
    public PoliF2LSL smult(Termino term) {

        PoliF2LSL poliC = zero(getVariable());

        if (isZero() || term.isZero()) {
            return poliC;
        }

        Termino termA, termC;
        NodoSimple nodoA, nodoC;

        nodoA = getPrimerNodo();

        while (!finDeRecorrido(nodoA)) {

            termA = (Termino) nodoA.getDato();
            termC = new Termino(termA.getCoef() * term.getCoef(), termA.getExp() + term.getExp());
            nodoC = new NodoSimple(termC);
            poliC.add(nodoC);
            nodoA = nodoA.getLiga();

        }

        return poliC;

    }

    /**
     * Realiza una división entre este polinomio y el polinomio dado como parámetro,
     * luego retorna solo el cociente de esta división como un polinomio de la clase
     * {@link PoliF2LSL}.
     * <p>
     * Sea <b><i>P(x)</i></b> este polinomio, <b><i>Q(x))</i></b> el polinomio
     * divisor (<b>poliDivisor</b>), <b><i>R(x)</i></b> el residuo, y
     * <b><i>C(x)</i></b> el cociente:
     * 
     * La división es: <br>
     * </br>
     * <b><i>P(x)/Q(x) = C(x) + R(x)/Q(x)</i></b><br>
     * </br>
     * dónde solo se retornará <b><i>C(x)</i></b
     * 
     * @param poliDivisor {@link PoliF2LSL} divisor.
     * @return {@link PoliF2LSL} Cociente de la división.
     */
    public PoliF2LSL divide(PoliF2LSL poliDivisor) {

        assert (getVariable() == poliDivisor.getVariable())
                : "No es posible dividir dos polinomios con diferentes variables.";

        assert (!poliDivisor.isEmpty()) : "No es posible dividir por 0.";

        assert (getGrade() >= poliDivisor.getGrade())
                : "No es posible dividir por un polinomio con grado mayor que este polinomio.";

        PoliF2LSL poliCociente, poliResiduo, poliSustraendo;
        poliCociente = new PoliF2LSL(getVariable());

        if (isEmpty()) {
            return poliCociente;
        }

        NodoSimple nodoA, nodoB, nodoC;
        Termino termA, termB, termC;

        poliResiduo = this;

        nodoB = poliDivisor.getPrimerNodo();
        termB = (Termino) nodoB.getDato();

        while (poliResiduo.getGrade() >= poliDivisor.getGrade()) {

            nodoA = poliResiduo.getPrimerNodo();
            termA = (Termino) nodoA.getDato();

            termC = new Termino(termA.getCoef() / termB.getCoef(), termA.getExp() - termB.getExp());

            nodoC = new NodoSimple(termC);
            poliCociente.add(nodoC);

            poliSustraendo = poliDivisor.smult(termC);
            poliResiduo = poliResiduo.substract(poliSustraendo);

        }

        return poliCociente;

    }

    /**
     * Retorna un String que representa este polinomio.
     */
    @Override
    public String toString() {

        String output, term;
        output = "";

        NodoSimple nodoX = getPrimerNodo();

        while (nodoX != null) {

            Termino t = (Termino) nodoX.getDato();
            term = t.toString();

            output = (nodoX == getPrimerNodo()) ? output + term.replace("+ ", "") : output + term;
            output = (nodoX == getUltimoNodo()) ? output : output + " ";

            nodoX = nodoX.getLiga();

        }

        return output;

    }

    /**
     * Convierte el String dado a un {@link Termino} siempre que este esté formado
     * correctamente. Ej: <i>-4x^5</i>
     * 
     * @param term String que describe al termino a retornar.
     * @return {@link Termino} que describía el String dado.
     */
    public Termino stringToTerm(String term) {

        String coefAux, expAux;
        Termino t;
        double coefAndExp[] = new double[2];
        term = term.replaceAll(" ", "");

        if (term.length() == 0) {
            return null;
        }

        Pattern coefPat, expPat;
        Matcher coefMatc, expMatc;

        coefPat = Pattern.compile(String.format("(\\-|\\+?)([0-9]*)(.[0-9])*%c?", this.variable));
        expPat = Pattern.compile(String.format("%c((\\^[0-9]+)?)", this.variable));

        coefMatc = coefPat.matcher(term);
        expMatc = expPat.matcher(term);

        if (coefMatc.find()) {

            coefAux = coefMatc.group().replaceAll("[^\\d\\-\\.]", "");
            if (Pattern.matches(String.format("\\+?%c?", this.variable), coefAux)) {

                coefAndExp[0] = 1;

            } else if (Pattern.matches(String.format("\\-?%c?", this.variable), coefAux)) {

                coefAndExp[0] = -1;

            } else {

                try {

                    coefAndExp[0] = Double.parseDouble(coefAux);

                } catch (NumberFormatException e) {

                    e.printStackTrace();
                }

            }

        } else {

            throw new IllegalArgumentException("Misformed term expression: " + term);

        }

        if (expMatc.find()) {

            expAux = expMatc.group().replaceAll("[^\\d\\-]", "");
            if (expAux.equalsIgnoreCase("")) {

                coefAndExp[1] = 1;

            } else {

                try {

                    coefAndExp[1] = Integer.parseInt(expAux);

                } catch (NumberFormatException e) {

                    e.printStackTrace();

                }

            }

        } else {

            coefAndExp[1] = 0;

        }

        t = new Termino(coefAndExp[0], (int) coefAndExp[1]);
        return t;

    }

    public String arrayToString() {

        if (isEmpty()) {
            return "[]";
        }

        String output = "";

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(4);

        NodoSimple nodoX = getPrimerNodo();
        Termino t;

        while (!finDeRecorrido(nodoX)) {

            t = (Termino) nodoX.getDato();
            if (nodoX == getPrimerNodo()) {

                output = output + String.format("[(%s%c%d)", df.format(t.getCoef()), '|', t.getExp());

            } else {

                output = output + String.format(", (%s%c%d)", df.format(t.getCoef()), '|', t.getExp());

            }

            if (nodoX == getUltimoNodo()) {

                output = output + "]";

            }

            nodoX = nodoX.getLiga();

        }

        return output;

    }

    /**
     * Muestra la representación en string de este polinomio por consola.
     */
    public void show() {

        System.out.println(toString());

    }

    public void showArray() {

        System.out.println(arrayToString());

    }

    public static void main(String[] args) {
        String polA = "4x^5 + 6x^4 -x";
        String polB = "6x^6 - 6x^4 -2x + 8";

        PoliF2LSL A = new PoliF2LSL(polA, 'x');
        PoliF2LSL B = new PoliF2LSL(polB, 'x');

        System.out.println(A.toString() + " <==> " + A.arrayToString());
        System.out.println(B.toString() + " <==> " + B.arrayToString());

        PoliF2LSL.sum(B, A);

        System.out.println();
        System.out.println(A.toString() + " <==> " + A.arrayToString());
        System.out.println(B.toString() + " <==> " + B.arrayToString());

    }

}