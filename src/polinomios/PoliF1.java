package polinomios;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nodos.NodoSimple;

/**
 * Polinomios en vector, términos ordenados descendentemente por exponente
 */
public class PoliF1 {

    private double[] V;
    private char variable;

    /**
     * Constructor.
     * 
     * @param grade grado del polinomio.
     */
    public PoliF1(int grade) {

        assert (grade >= -1) : "grade must be >=-1";

        V = new double[grade + 2];
        V[0] = grade;
        variable = 'x';

    }

    /**
     * Constructor. Construye un polinomio representado en vector con los parámetros
     * dados.
     * 
     * @param grade Grado del polinomio.
     * @param var   Variable del polinomio.
     */
    public PoliF1(int grade, char var) {

        assert (grade >= -1) : "grade must be >=-1";

        V = new double[grade + 2];
        V[0] = grade;
        variable = var;

    }

    /**
     * Contructor. Construye un polinomio a partir del String dado.
     * <p>
     * Si el String que representa el polinomio contiene un terminocon un patron
     * inválido, se arrojará una excepción.
     * <p>
     * Si el String que representa el polinomio contiene un termino cuyo exponente
     * es mayor al grado del polinomio, se
     * omitirá.
     * 
     * @param grade Grado del polinomio.
     * @param poli  String que representa el polinomio.
     * @param var   Variable del polinomio.
     */
    public PoliF1(int grade, String poli, char var) {

        assert (grade >= -1) : "grade must be >=-1";

        V = new double[grade + 2];
        V[0] = grade;
        variable = var;

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

        double coefAndExp[];

        for (String term : terms) {

            coefAndExp = StringToCoefAndExp(term);

            if (coefAndExp[1] > grade) {
                continue;
            }

            sumCoefByExp(coefAndExp[0], (int) coefAndExp[1]);

        }

        normalize();

    }

    /**
     * Calcula la posición del término con exponente <b>exp</b> del vector
     * {@link #V}.
     * 
     * @param exp Exponente del término.
     * @return Posición del término con exponente <b>exp</b> en el vector
     *         {@link #V}.
     */
    public int getPos(int exp) {
        return getGrade() - exp + 1;
    }

    /**
     * Calcula el exponente del término en la posición <b>pos</b> del vector
     * {@link #V}.
     * 
     * @param pos Posición del término en el vector {@link #V}.
     * @return El exponente del término en la posición <b>pos</b> del vector
     *         {@link #V}.
     */
    public int getExp(int pos) {
        return getGrade() - pos + 1;
    }

    public void setCoef(double coef, int idx) {

        assert (idx != 0) : "idx cannot be zero!";

        V[idx] = coef;

    }

    public void setCoefByExp(double coef, int exp) {

        assert (exp >= 0 && exp <= getGrade()) : "No existen términos con el exponente dado.";

        V[getPos(exp)] = coef;

    }

    public double getCoef(int idx) {

        assert (idx != 0) : "idx cannot be zero!";

        return V[idx];

    }

    public double getCoefFromExp(int exp) {

        assert (exp >= 0 && exp <= getGrade()) : "No existen términos con el exponente dado.";

        return V[getPos(exp)];

    }

    /**
     * Suma el coeficiente <b>coef</b> dado al vector en la posición <b>idx</b> del
     * vector {@link #V}. Es decir:
     * <p>
     * {@code V[idx] = V[idx] + coef}.
     * 
     * @param coef Coeficiente a sumar.
     * @param idx  Índice en el vector a sumar.
     */
    public void sumCoef(double coef, int idx) {

        assert (idx != 0) : "idx cannot be zero!";

        setCoef(getCoef(idx) + coef, idx);

    }

    /**
     * Suma el coeficiente <b>coef</b> al vector {@link #V} en la posición
     * correspondiente al exponente <b>exp</b> dado.
     * <p>
     * {@code V[getPos(exp)] = V[idx] + coef}.
     * 
     * @param coef Coeficiente a sumar al vector.
     * @param exp  Exponente en donde se quiere sumar <b>coef</b>.
     * @see {@link #getPos()}
     */
    public void sumCoefByExp(double coef, int exp) {

        assert (exp >= 0 && exp <= getGrade()) : "No existen términos con el exponente dado.";

        setCoefByExp(getCoefFromExp(exp) + coef, exp);

    }

    public int getGrade() {

        return (int) V[0];

    }

    public char getVariable() {
        return variable;
    }

    public boolean isZero() {
        return (getCoef(1) == 0);
    }

    /**
     * Polinomio nulo
     * 
     * @return {@link PoliF1} que representa el polinomio nulo o cero.
     */
    public static PoliF1 zero(char var) {
        return new PoliF1(0, var);
    }

    /**
     * Realiza una suma entre este polinomio y el polinomio <b>PoliB</b> dado como
     * parámetro.
     * 
     * @param PoliB Segundo sumando de la suma a realizar.
     * @return {@link PoliF1}, resultado de la suma.
     */
    public PoliF1 sum(PoliF1 PoliB) {

        assert (variable == PoliB.getVariable()) : "No quiero sumar dos polinomios con diferentes variables.";

        int gradeA, gradeB, gradeC, i, j, k;
        double s;
        PoliF1 PoliC;

        gradeA = this.getGrade();
        gradeB = PoliB.getGrade();
        gradeC = Math.max(gradeA, gradeB);

        PoliC = new PoliF1(gradeC, variable);

        i = gradeA + 1;
        j = gradeB + 1;
        k = gradeC + 1;

        while (i > 0 && j > 0) {

            s = this.getCoef(i) + PoliB.getCoef(j);
            PoliC.setCoef(s, k);

            i--;
            j--;
            k--;

        }
        while (i > 0) {

            PoliC.setCoef(this.getCoef(i), k);
            i--;
            k--;

        }
        while (j > 0) {

            PoliC.setCoef(PoliB.getCoef(j), k);
            j--;
            k--;

        }

        PoliC.normalize();
        return PoliC;

    }

    public PoliF1 sumF2(PoliF2 poliF2_B) {

        assert (getVariable() == poliF2_B.getVariable())
                : "No es posible sumar dos polinomios con diferentes variables.";

        PoliF1 poliF1_C;

        double coefA, coefB, coefC;
        int expA, expB, i, j, gradeC;
        Termino termB;

        gradeC = Math.max(getGrade(), poliF2_B.getGrade());

        poliF1_C = new PoliF1(gradeC, getVariable());

        i = 1;
        j = 1;

        while (i <= getGrade() + 1 && j <= poliF2_B.getNumElementosNoCero()) {

            coefA = getCoef(i);
            expA = getExp(i);

            termB = poliF2_B.getTerm(j);
            coefB = termB.getCoef();
            expB = termB.getExp();

            if (expA > expB) {

                poliF1_C.setCoefByExp(coefA, expA);
                i++;

            } else if (expA < expB) {

                poliF1_C.setCoefByExp(coefB, expB);
                j++;

            } else {

                coefC = coefA + coefB;
                poliF1_C.setCoefByExp(coefC, expA);
                i++;
                j++;

            }

        }
        while (i <= getGrade() + 1) {

            coefA = getCoef(i);
            expA = getExp(i);
            poliF1_C.setCoefByExp(coefA, expA);
            i++;

        }
        while (j <= poliF2_B.getNumElementosNoCero()) {

            termB = poliF2_B.getTerm(j);
            coefB = termB.getCoef();
            expB = termB.getExp();
            poliF1_C.setCoefByExp(coefB, expB);
            j++;

        }

        poliF1_C.normalize();
        return poliF1_C;

    }

    public PoliF1 sumF2LSL(PoliF2LSL poliF2LSL_B) {

        assert (getVariable() == poliF2LSL_B.getVariable())
                : "No es posible sumar dos polinomios con diferentes variables.";

        PoliF1 poliF1_C;

        double coefA, coefB, coefC;
        int expA, expB, i, gradeC;
        NodoSimple nodoB = poliF2LSL_B.getPrimerNodo();
        Termino termB;

        gradeC = Math.max(getGrade(), poliF2LSL_B.getGrade());

        poliF1_C = new PoliF1(gradeC, getVariable());

        i = 1;
        while (i <= getGrade() + 1 && !poliF2LSL_B.finDeRecorrido(nodoB)) {

            coefA = getCoef(i);
            expA = getExp(i);

            termB = (Termino) nodoB.getDato();
            coefB = termB.getCoef();
            expB = termB.getExp();

            if (expA > expB) {

                poliF1_C.setCoefByExp(coefA, expA);
                i++;

            } else if (expA < expB) {

                poliF1_C.setCoefByExp(coefB, expB);
                nodoB = nodoB.getLiga();

            } else {

                coefC = coefA + coefB;
                poliF1_C.setCoefByExp(coefC, expA);
                i++;
                nodoB = nodoB.getLiga();

            }

        }
        while (i <= getGrade() + 1) {

            coefA = getCoef(i);
            expA = getExp(i);
            poliF1_C.setCoefByExp(coefA, expA);
            i++;

        }
        while (!poliF2LSL_B.finDeRecorrido(nodoB)) {

            termB = (Termino) nodoB.getDato();
            coefB = termB.getCoef();
            expB = termB.getExp();
            poliF1_C.setCoefByExp(coefB, expB);
            nodoB = nodoB.getLiga();

        }

        poliF1_C.normalize();
        return poliF1_C;

    }

    public PoliF1 substract(PoliF1 poliB) {

        assert (variable == poliB.getVariable()) : "No se pueden restar 2 polinomios con distintas variables.";

        PoliF1 poliC;
        int gradeA, gradeB, gradeC, i, j, k;
        double r;

        gradeA = getGrade();
        gradeB = poliB.getGrade();
        gradeC = Math.max(gradeA, gradeB);

        poliC = new PoliF1(gradeC, variable);

        i = gradeA + 1;
        j = gradeB + 1;
        k = gradeC + 1;

        while (i >= 1 && j >= 1) {

            r = getCoef(i) - poliB.getCoef(j);
            poliC.setCoef(r, k);

            i--;
            j--;
            k--;

        }
        while (i >= 1) {

            poliC.setCoef(getCoef(i), k);

            i--;
            k--;

        }
        while (j >= 1) {

            poliC.setCoef(poliB.getCoef(j), k);

            j--;
            k--;

        }

        normalize();
        return poliC;

    }

    /**
     * Multiplica dos polinomios y el resultado lo retorna como {@link PoliF1}
     * 
     * @param poliB Polinomio multiplicador.
     * @return {@link PoliF1} resultado de multiplicar este polinomio con el
     *         polinomio <b>poliB</b>.
     */
    public PoliF1 multiply(PoliF1 poliB) {

        assert (getVariable() == poliB.getVariable())
                : "No se pueden multiplicar 2 polinomios con distinta variable. (de momento)";

        if (isZero() || poliB.isZero()) {
            return zero(getVariable());
        }

        PoliF1 poliC;
        int gradeA, gradeB, gradeC, i, j, k, expA, expB, expC;
        double coefA, coefB, coefC;

        gradeA = getGrade();
        gradeB = poliB.getGrade();
        gradeC = gradeA + gradeB;

        poliC = new PoliF1(gradeC, variable);

        for (i = 1; i <= gradeA + 1; i++) {

            coefA = getCoef(i);
            expA = getExp(i);

            for (j = 0; j < gradeB + 1; j++) {

                coefB = poliB.getCoef(j);
                expB = poliB.getExp(j);

                expC = expA + expB;

                k = poliC.getPos(expC);
                coefC = poliC.getCoef(k) + coefA * coefB;

                poliC.setCoef(coefC, k);

            }

        }

        return poliC;

    }

    public static PoliF1 multiplyF2_F2LSL(PoliF2 poliF2_A, PoliF2LSL poliF2LSL_B) {

        assert (poliF2_A.getVariable() == poliF2LSL_B.getVariable())
                : "No se pueden multiplicar 2 polinomios con distinta variable. (de momento)";

        if (poliF2_A.isZero() || poliF2LSL_B.isZero()) {
            return zero(poliF2_A.getVariable());
        }

        PoliF1 poliF1_C;
        int gradeC, i, expA, expB, expC;
        double coefA, coefB, coefC;
        Termino termA, termB;
        NodoSimple nodoB;

        gradeC = poliF2_A.getGrade() + poliF2LSL_B.getGrade();
        poliF1_C = new PoliF1(gradeC, poliF2_A.getVariable());

        for (i = 1; i <= poliF2_A.getNumElementosNoCero(); i++) {

            termA = poliF2_A.getTerm(i);
            coefA = termA.getCoef();
            expA = termA.getExp();

            nodoB = poliF2LSL_B.getPrimerNodo();

            while (!poliF2LSL_B.finDeRecorrido(nodoB)) {

                termB = (Termino) nodoB.getDato();
                coefB = termB.getCoef();
                expB = termB.getExp();

                coefC = coefA * coefB;
                expC = expA + expB;

                poliF1_C.sumCoefByExp(coefC, expC);

                nodoB = nodoB.getLiga();

            }

        }

        return poliF1_C;

    }

    /**
     * Normaliza el grado del polinomio según los terminos iguales a cero
     * consecutivos que tiene el polinomio en sus exponentes mayores, posteriormente
     * actualiza el grado del polinomio si es necesario.
     */
    protected void normalize() {

        int grade, i, j;

        grade = this.getGrade();

        i = 1;
        while (i <= grade + 1 && this.getCoef(i) == 0) {
            i++;
        }

        if (i == 1) {
            return;
        }

        V[0] = grade - i + 1;

        j = 1;
        while (i <= grade + 1) {

            this.setCoef(this.getCoef(i), j);
            i++;
            j++;

        }

    }

    /**
     * Convierte todo este polinomio a un String usando el método
     * {@link #termToString(int, int)} como apoyo.
     * 
     * @return String que representa este polinomio.
     */
    @Override
    public String toString() {

        if (isZero()) {
            return "0";
        }

        String output, term;
        output = "";
        int grade, exp, i;
        double coef;
        grade = getGrade();

        for (i = 1; i <= grade + 1; i++) {

            coef = getCoef(i);
            exp = getExp(i);

            if (coef == 0) {
                continue;
            }

            term = termToString(coef, exp);

            if (i == 1) {
                output = output + term.replace("+ ", "");
            } else {
                output = output + term;
            }

            output = (i == grade + 1) ? output : output + " ";

        }

        return output;

    }

    /**
     * Convierte el término definido por los parámetros dados a un String de la
     * forma
     * <b><i>(+/-)cx^e</i></b> donde c es el coeficiente y e el exponente.
     * 
     * @param coef Coeficiente del término a convertir.
     * @param exp  Exponente del término a convertir.
     * @return String de la forma <b><i>(+/-)cx^e</i></b.
     */
    public String termToString(double coef, int exp) {

        String term = "%1$s%2$s%3$s%4$s"; // 1 = signo, 2 = coef, 3 = variable, 4 = exp

        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(4);

        if (coef == 0) {

            return "";

        }
        if (exp == 0) {

            term = term.replace("%3$s%4$s", "");

        } else if (exp == 1) {

            term = term.replace("%4$s", "");

        }
        if ((coef == 1 || coef == -1) && exp > 0) {

            term = term.replace("%2$d", "");

        }
        if (coef % 1 == 0) {

            term = term.replace("%2$s", "%2$s");

        } else {

        }
        if (coef > 0) {

            term = String.format(term, "+ ", df.format(Math.abs(coef)), variable, "^" + exp);

        } else {

            term = String.format(term, "- ", df.format(Math.abs(coef)), variable, "^" + exp);

        }

        return term;

    }

    /**
     * Toma un termino de tipo String y lo convierte en un par
     * coeficiente/exponente. El termino debe coincidir con este patron:
     * <p>
     * <b>(+/-)cx^e</b>
     * <br>
     * </br>
     * donde c es el coeficiente (un entero) y e el exponente(un entero no
     * negativo).
     * 
     * @param term El string a convertir.
     * @return Vector de 2 elementos, coeficiente y exponente.
     */
    public double[] StringToCoefAndExp(String term) {

        String coefAux, expAux;
        double[] coefAndExp = new double[2];
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

        return coefAndExp;

    }

    public String arrayToString() {

        String output = "";
        DecimalFormat df = new DecimalFormat();
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(4);

        for (int i = 0; i < V.length; i++) {

            output = (i == 0) ? output + String.format("[%s", df.format(V[i]))
                    : output + String.format(", (%s%c%d)", df.format(V[i]), '|', getExp(i));

            if (i == V.length - 1) {
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

        String polA = "x+1";
        String polB = "x-1";

        PoliF2 A = new PoliF2(2, polA, 'x');
        PoliF2LSL B = new PoliF2LSL(polB, 'x');

        System.out.println(A.toString() + " <==> " + A.arrayToString());
        System.out.println(B.toString() + " <==> " + B.arrayToString());

        System.out.println();

        PoliF1 C = multiplyF2_F2LSL(A, B);

        System.out.println(C.toString() + " <==> " + C.arrayToString());

    }

}