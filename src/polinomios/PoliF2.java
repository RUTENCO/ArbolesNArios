package polinomios;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nodos.NodoSimple;

public class PoliF2 {

    private ArrayList<Object> V;

    private char variable;

    public PoliF2(int numElementosNoCero, char variable) {

        V = new ArrayList<Object>();
        V.add(numElementosNoCero);
        this.variable = variable;

    }

    public PoliF2(int numElementosNoCero, String poli, char variable) {

        V = new ArrayList<Object>();
        V.add(numElementosNoCero);
        this.variable = variable;
        Termino t;

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

        assert (terms.size() == numElementosNoCero)
                : "El número de elementos diferentes de cero no coincide con los terminos dados en el String.";

        for (String term : terms) {

            t = stringToTerm(term);
            V.add(t);

        }

    }

    public int getNumElementosNoCero() {
        return (int) V.get(0);
    }

    public void setNumElementosNoCero(int n) {
        V.set(0, n);
    }

    /**
     * Retorna el {@link Termino} ubicado en la posición <b>idx</b> del vector
     * {@link #V}.
     * 
     * @param idx Índice del termino a obtener.
     * @return {@link Termino} en la posición <b>idx</b> del vector {@link #V}.
     */
    public Termino getTerm(int idx) {
        return (Termino) V.get(idx);
    }

    public void setTerm(Termino term, int idx) {

        assert (idx <= V.size()) : "Debe añadir terminos en las posiciones anteriores a 'idx'.";

        if (idx == V.size()) {
            V.add(term);
            return;
        }

        V.set(idx, term);

    }

    public int getGrade() {

        if (V.size() == 1) {
            return 0;
        }

        Termino t = (Termino) V.get(1);

        return t.getExp();

    }

    public char getVariable() {
        return variable;
    }

    public boolean isZero() {
        return (getNumElementosNoCero() == 0);
    }

    /**
     * Polinomio nulo
     * 
     * @return {@link PoliF2} que representa el polinomio nulo o cero.
     */
    public static PoliF2 zero(char var) {
        return new PoliF2(0, var);
    }

    public void insertTerm(Termino term, boolean reemplazar) {

        // int i = 1;

        // while (i < V.size() && term.getExp() < ((Termino) V.get(i)).getExp()) {
        // i++;
        // }

        // if (i == V.size()) {
        // V.add(term);
        // return;
        // }

        // if (term.getExp() == ((Termino) V.get(i)).getExp()) {

        // if (reemplazar) {

        // V.set(i, term);
        // return;

        // } else {
        // return;
        // }

        // }

        // V.add(i, term);

    }

    /**
     * Suma este polinomio con el polinomio dado como parámetro y retorna el
     * resultado como un polinomio de la clase {@link PoliF2}.
     * 
     * @param poliB {PoliF2} sumando.
     * @return {@link PoliF2} resultado de la suma.
     */
    public PoliF2 sum(PoliF2 poliB) {

        assert (getVariable() == poliB.getVariable()) : "No se pueden sumar 2 polinomios con distintas variables.";

        PoliF2 poliC;

        int numElemNoCeroA, numElemNoCeroB, numElemNoCeroC, i, j, k;
        double s;
        Termino termA, termB, termC;

        numElemNoCeroA = getNumElementosNoCero();
        numElemNoCeroB = poliB.getNumElementosNoCero();
        numElemNoCeroC = numElemNoCeroA + numElemNoCeroB;

        poliC = new PoliF2(numElemNoCeroC, variable);

        i = 1;
        j = 1;
        k = 1;

        while (i <= numElemNoCeroA && j <= numElemNoCeroB) {

            termA = getTerm(i);
            termB = poliB.getTerm(j);

            if (termA.getExp() > termB.getExp()) {

                termC = new Termino(termA.getCoef(), termA.getExp());
                poliC.setTerm(termC, k);
                i++;

            } else if (termA.getExp() < termB.getExp()) {

                termC = new Termino(termB.getCoef(), termB.getExp());
                poliC.setTerm(termC, k);
                j++;

            } else {

                s = termA.getCoef() + termB.getCoef();
                if (s != 0) {

                    termC = new Termino(s, termA.getExp());
                    poliC.setTerm(termC, k);

                } else {
                    k--;
                }

                i++;
                j++;

            }
            k++;

        }
        while (i <= numElemNoCeroA) {

            termA = getTerm(i);
            termC = new Termino(termA.getCoef(), termA.getExp());
            poliC.setTerm(termC, k);
            i++;
            k++;

        }
        while (j <= numElemNoCeroB) {

            termB = poliB.getTerm(j);
            termC = new Termino(termB.getCoef(), termB.getExp());
            poliC.setTerm(termC, k);
            j++;
            k++;

        }

        poliC.setNumElementosNoCero(k - 1);
        return poliC;

    }

    public PoliF2 sumF2LSL(PoliF2LSL poliF2LSL_B) {

        assert (getVariable() == poliF2LSL_B.getVariable())
                : "No se pueden sumar 2 polinomios con distintas variables.";

        PoliF2 poliC;

        int numElemNoCeroA, numElemNoCeroB, numElemNoCeroC, i, k;
        double s;
        Termino termA, termB, termC;
        NodoSimple nodoB;

        numElemNoCeroA = getNumElementosNoCero();
        numElemNoCeroB = poliF2LSL_B.getNumElementosNoCero();
        numElemNoCeroC = numElemNoCeroA + numElemNoCeroB;

        poliC = new PoliF2(numElemNoCeroC, variable);

        i = 1;
        nodoB = poliF2LSL_B.getPrimerNodo();
        k = 1;

        while (i <= numElemNoCeroA && !poliF2LSL_B.finDeRecorrido(nodoB)) {

            termA = getTerm(i);
            termB = (Termino) nodoB.getDato();

            if (termA.getExp() > termB.getExp()) {

                termC = new Termino(termA.getCoef(), termA.getExp());
                poliC.setTerm(termC, k);
                i++;

            } else if (termA.getExp() < termB.getExp()) {

                termC = new Termino(termB.getCoef(), termB.getExp());
                poliC.setTerm(termC, k);
                nodoB = nodoB.getLiga();

            } else {

                s = termA.getCoef() + termB.getCoef();
                if (s != 0) {

                    termC = new Termino(s, termA.getExp());
                    poliC.setTerm(termC, k);

                } else {
                    k--;
                }

                i++;
                nodoB = nodoB.getLiga();

            }
            k++;

        }
        while (i <= numElemNoCeroA) {

            termA = getTerm(i);
            termC = new Termino(termA.getCoef(), termA.getExp());

            poliC.setTerm(termC, k);
            i++;
            k++;

        }
        while (!poliF2LSL_B.finDeRecorrido(nodoB)) {

            termB = (Termino) nodoB.getDato();
            termC = new Termino(termB.getCoef(), termB.getExp());

            poliC.setTerm(termC, k);
            nodoB = nodoB.getLiga();
            k++;

        }

        poliC.setNumElementosNoCero(k - 1);
        return poliC;

    }

    /**
     * Reste este polinomio con el polinomio dado como parámetro y retorna el
     * resultado como un polinomio de la clase {@link PoliF2}.
     * 
     * @param poliB {PoliF2} sustraendo.
     * @return {@link PoliF2} resultado de la resta.
     */
    public PoliF2 substract(PoliF2 poliB) {

        assert (getVariable() == poliB.getVariable()) : "No se pueden restar 2 polinomios con distintas variables.";

        PoliF2 poliC;

        int numElemNoCeroA, numElemNoCeroB, numElemNoCeroC, i, j, k;
        double r;
        Termino termA, termB, termC;

        numElemNoCeroA = getNumElementosNoCero();
        numElemNoCeroB = poliB.getNumElementosNoCero();
        numElemNoCeroC = numElemNoCeroA + numElemNoCeroB;

        poliC = new PoliF2(numElemNoCeroC, variable);

        i = 1;
        j = 1;
        k = 1;

        while (i <= numElemNoCeroA && j <= numElemNoCeroB) {

            termA = getTerm(i);
            termB = poliB.getTerm(j);

            if (termA.getExp() > termB.getExp()) {

                termC = new Termino(termA.getCoef(), termA.getExp());
                poliC.setTerm(termC, k);
                i++;

            } else if (termA.getExp() < termB.getExp()) {

                termC = new Termino(-termB.getCoef(), termB.getExp());
                poliC.setTerm(termC, k);
                j++;

            } else {

                r = termA.getCoef() - termB.getCoef();
                if (r != 0) {

                    termC = new Termino(r, termA.getExp());
                    poliC.setTerm(termC, k);

                } else {
                    k--;
                }

                i++;
                j++;

            }
            k++;

        }
        while (i <= numElemNoCeroA) {

            termA = getTerm(i);
            termC = new Termino(termA.getCoef(), termA.getExp());
            poliC.setTerm(termC, k);
            i++;
            k++;

        }
        while (j <= numElemNoCeroB) {

            termB = poliB.getTerm(j);
            termC = new Termino(-termB.getCoef(), termB.getExp());
            poliC.setTerm(termC, k);
            j++;
            k++;

        }

        poliC.setNumElementosNoCero(k - 1);
        return poliC;

    }

    /**
     * Multiplica este polinomio con el polinomio dado como parámetro, usando el
     * método {@link #smult(Termino)} como apoyo, y retorna el resultado como un
     * polinomio de
     * la clase {@link PoliF2}.
     * 
     * @param poliB {@link PoliF2} multiplicador.
     * @return {@link PoliF2} resultado de la multiplicación,
     */
    public PoliF2 multiply(PoliF2 poliB) {

        assert (getVariable() == poliB.getVariable())
                : "No se pueden multiplicar 2 polinomios con distinta variable. (de momento)";

        PoliF2 poliC = zero(getVariable());

        if (isZero() || poliB.isZero()) {
            return poliC;
        }

        Termino termB;

        for (int j = 1; j <= poliB.getNumElementosNoCero(); j++) {

            termB = poliB.getTerm(j);
            poliC = poliC.sum(smult(termB));

        }

        return poliC;

    }

    public static PoliF2 multiplyF1_F2LSL(PoliF1 poliF1_A, PoliF2LSL poliF2LSL_B) {

        assert (poliF1_A.getVariable() == poliF2LSL_B.getVariable())
                : "No se pueden multiplicar 2 polinomios con distinta variable. (de momento)";

        PoliF2 poliF2_C = zero(poliF1_A.getVariable());

        if (poliF1_A.isZero() || poliF2LSL_B.isZero()) {
            return poliF2_C;
        }

        double coefA;
        int i, expA;
        Termino termA;

        for (i = 1; i <= poliF1_A.getGrade() + 1; i++) {

            coefA = poliF1_A.getCoef(i);
            expA = poliF1_A.getExp(i);
            termA = new Termino(coefA, expA);

            poliF2_C = poliF2_C.sumF2LSL(poliF2LSL_B.smult(termA));

        }

        return poliF2_C;

    }

    /**
     * Multiplica todo este polinomio por el término entregado como parámetro, y
     * devuelde un {@link PoliF2} como resultado.
     * 
     * @param term Multiplicador.
     * @return {@link PoliF2} resultado de la multiplicación.
     */
    public PoliF2 smult(Termino term) {

        PoliF2 poliC = zero(getVariable());

        if (isZero() || term.isZero()) {
            return poliC;
        }

        Termino termA, termC;

        for (int i = 1; i <= getNumElementosNoCero(); i++) {

            termA = getTerm(i);
            termC = new Termino(termA.getCoef() * term.getCoef(), termA.getExp() + term.getExp());
            poliC.setTerm(termC, i);

        }

        return poliC;

    }

    @Override
    public String toString() {

        if (isZero()) {
            return "0";
        }

        String output, term;

        output = "";

        for (int i = 1; i <= getNumElementosNoCero(); i++) {

            term = ((Termino) V.get(i)).toString();

            output = (i == 1) ? output + term.replace("+ ", "") : output + term;
            output = (i == getNumElementosNoCero()) ? output : output + " ";

        }

        return output;

    }

    /**
     * Convierte el String dado a un {@link Termino} siempre que este formado
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
            if (Pattern.matches(String.format("\\-?%c?", this.variable), coefAux)) {

                coefAndExp[0] = 1;

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

        String output = "";
        Termino t;

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(4);

        for (int i = 0; i < V.size(); i++) {

            if (i == 0) {
                output = output + String.format("[%s", df.format(V.get(i)));
            } else {
                t = (Termino) V.get(i);
                output = output + String.format(", (%s%c%d)", df.format(t.getCoef()), '|', t.getExp());
            }

            if (i == V.size() - 1) {
                output = output + "]";
            }

        }

        return output;

    }

    public void show() {

        System.out.println(toString());

    }

    public void showArray() {

        System.out.println(arrayToString());

    }

    public static void main(String[] args) {

        String polA = "7x^4 + 4x^2 + 7x + 2";
        String polB = "6x^3 + 8x +3";

        PoliF1 A = new PoliF1(4, polA, 'x');
        PoliF2LSL B = new PoliF2LSL(polB, 'x');

        System.out.println(A.toString() + " <==> " + A.arrayToString());
        System.out.println(B.toString() + " <==> " + B.arrayToString());

        PoliF2 C = PoliF2.multiplyF1_F2LSL(A, B);

        System.out.println();

        System.out.println(C.toString() + " <==> " + C.arrayToString());

    }

}

// --------------------------------------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------------------------------

class Termino {

    private double coef;

    private int exp;

    Termino(double coef, int exp) {
        this.coef = coef;
        this.exp = exp;
    }

    public double getCoef() {
        return coef;
    }

    public int getExp() {
        return exp;
    }

    public void setCoef(double coef) {
        this.coef = coef;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean isZero() {
        return getCoef() == 0;
    }

    @Override
    public String toString() {

        String output = "%1$s%2$s%3$s%4$s"; // 1 = signo, 2 = coef, 3 = variable, 4 = exp

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(4);

        if (coef == 0) {

            return "";

        }
        if (exp == 0) {

            output = output.replace("%3$s%4$s", "");

        } else if (exp == 1) {

            output = output.replace("%4$s", "");

        }
        if ((coef == 1 || coef == -1) && exp > 0) {

            output = output.replace("%2$d", "");

        }

        if (coef > 0) {

            output = String.format(output, "+ ", df.format(Math.abs(coef)), 'x', "^" + exp);

        } else {

            output = String.format(output, "- ", df.format(Math.abs(coef)), 'x', "^" + exp);

        }

        return output;

    }

}