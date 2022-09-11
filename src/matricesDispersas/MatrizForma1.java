package matricesDispersas;

import nodos.NodoDobleT;

public class MatrizForma1 implements MatrizDispersa {

    private NodoDobleT nodoCabeza;

    public MatrizForma1(int m, int n) {

        this.nodoCabeza = new NodoDobleT(null);
        Tripleta t = new Tripleta(m, n, this.nodoCabeza);
        this.nodoCabeza.setTripleta(t);
        construirNodosCabeza();

    }

    public MatrizForma1(int[][] m) {

        this.nodoCabeza = new NodoDobleT(null);
        Tripleta t = new Tripleta(m.length, m[0].length, this.nodoCabeza);
        this.nodoCabeza.setTripleta(t);
        construirNodosCabeza();

        for (int i = 0; i < m.length; i++) {

            for (int j = 0; j < m[0].length; j++) {

                if (m[i][j] != 0) {

                    conectar(new NodoDobleT(new Tripleta(i, j, m[i][j])));

                }

            }

        }

    }

    private void construirNodosCabeza() {

        int max = Math.max(getNumFilas(), getNumColumnas());
        NodoDobleT cabeza, last;
        Tripleta t;
        last = getNodoCabeza();

        for (int i = 0; i < max; i++) {

            t = new Tripleta(i, i, getNodoCabeza());
            cabeza = new NodoDobleT(t);
            cabeza.setLd(cabeza);
            cabeza.setLi(cabeza);

            t = last.getTripleta();
            t.setValor(cabeza);
            last = cabeza;

        }

    }

    public NodoDobleT getNodoCabeza() {

        return nodoCabeza;

    }

    public int getNumFilas() {

        return getNodoCabeza().getFila();

    }

    public int getNumColumnas() {

        return getNodoCabeza().getColumna();

    }

    public NodoDobleT getPrimerNodo() {

        return (NodoDobleT) getNodoCabeza().getValor();

    }

    public boolean isEmpty() {

        return getPrimerNodo() == getNodoCabeza();

    }

    public boolean finDeRecorrido(NodoDobleT p) {

        return p == getNodoCabeza();

    }

    @Override
    public Object get(int i, int j) {

        if (i < 0 || j < 0 || i >= getNumFilas() || j >= getNumColumnas()) {

            throw new IndexOutOfBoundsException();

        }

        NodoDobleT nodoFila, nodoX;
        nodoFila = getPrimerNodo();

        for (int k = 0; k < i; k++) {

            nodoFila = (NodoDobleT) nodoFila.getValor();

        }

        nodoX = nodoFila.getLd();

        while (nodoX != nodoFila && j != nodoX.getColumna()) {

            nodoX = nodoX.getLd();

        }

        if (nodoX == nodoFila) {
            return 0;
        }

        return nodoX.getValor();

    }

    @Override
    public void set(int i, int j, Object val) {

        if (i < 0 || j < 0 || i >= getNumFilas() || j >= getNumColumnas()) {

            throw new IndexOutOfBoundsException();

        }

        NodoDobleT nodoFila, nodoX;

        nodoFila = getPrimerNodo();

        for (int k = 0; k < i; k++) {

            nodoFila = (NodoDobleT) nodoFila.getValor();

        }

        nodoX = nodoFila.getLd();

        while (nodoX != nodoFila && j > nodoX.getColumna()) {

            nodoX = nodoX.getLd();

        }

        if (nodoX != nodoFila && j == nodoX.getColumna()) {

            nodoX.setValor(val);
            return;

        }

        conectar(new NodoDobleT(new Tripleta(i, j, val)));

    }

    public void conectar(NodoDobleT x) {

        conectarPorFilas(x);
        conectarPorColumnas(x);

    }

    public void conectarPorFilas(NodoDobleT x) {

        NodoDobleT nodoFila, sigX, antX;

        nodoFila = getPrimerNodo();
        for (int i = 0; i < x.getFila(); i++) {

            nodoFila = (NodoDobleT) nodoFila.getValor();

        }
        antX = nodoFila;
        sigX = nodoFila.getLd();

        while (sigX != nodoFila && x.getColumna() > sigX.getColumna()) {

            antX = sigX;
            sigX = antX.getLd();

        }

        x.setLd(sigX);
        antX.setLd(x);

    }

    public void conectarPorColumnas(NodoDobleT x) {

        NodoDobleT nodoColumna, sigX, antX;

        nodoColumna = getPrimerNodo();
        for (int i = 0; i < x.getColumna(); i++) {

            nodoColumna = (NodoDobleT) nodoColumna.getValor();

        }
        antX = nodoColumna;
        sigX = nodoColumna.getLi();

        while (sigX != nodoColumna && x.getFila() > sigX.getFila()) {

            antX = sigX;
            sigX = antX.getLi();

        }

        x.setLi(sigX);
        antX.setLi(x);

    }

    @Override
    public MatrizForma1 sum(MatrizDispersa B) {

        return sumF1((MatrizForma1) B);

    }

    private MatrizForma1 sumF1(MatrizForma1 matrizB) {

        if (getNumFilas() != matrizB.getNumFilas() || getNumColumnas() != matrizB.getNumColumnas()) {

            return null;

        }

        NodoDobleT nodoFilaA, nodoFilaB, nodoA, nodoB, nodoC;
        int s;

        MatrizForma1 matrizC = new MatrizForma1(getNumFilas(), getNumColumnas());

        nodoFilaA = getPrimerNodo();
        nodoFilaB = matrizB.getPrimerNodo();

        while (nodoFilaA != getNodoCabeza() && nodoFilaB != matrizB.getNodoCabeza()) {

            nodoA = nodoFilaA.getLd();
            nodoB = nodoFilaB.getLd();

            while (nodoA != nodoFilaA && nodoB != nodoFilaB) {

                switch (Integer.compare(nodoA.getColumna(), nodoB.getColumna())) {

                    case 1:

                        nodoC = new NodoDobleT(nodoB.getTripleta().clone());
                        matrizC.conectar(nodoC);
                        nodoB = nodoB.getLd();
                        break;

                    case -1:

                        nodoC = new NodoDobleT(nodoA.getTripleta().clone());
                        matrizC.conectar(nodoC);
                        nodoA = nodoA.getLd();
                        break;

                    case 0:

                        s = (int) nodoA.getValor() + (int) nodoB.getValor();
                        if (s != 0) {

                            nodoC = new NodoDobleT(new Tripleta(nodoA.getFila(), nodoA.getColumna(), s));
                            matrizC.conectar(nodoC);

                        }

                        nodoA = nodoA.getLd();
                        nodoB = nodoB.getLd();
                        break;

                }

            }

            while (nodoA != nodoFilaA) {

                nodoC = new NodoDobleT(nodoA.getTripleta().clone());
                matrizC.conectar(nodoC);
                nodoA = nodoA.getLd();

            }

            while (nodoB != nodoFilaB) {

                nodoC = new NodoDobleT(nodoB.getTripleta().clone());
                matrizC.conectar(nodoC);
                nodoB = nodoB.getLd();

            }

            nodoFilaA = (NodoDobleT) nodoFilaA.getValor();
            nodoFilaB = (NodoDobleT) nodoFilaB.getValor();

        }

        return matrizC;

    }

    @Override
    public MatrizForma1 multiply(MatrizDispersa B) {

        return (MatrizForma1) multiplyF1((MatrizForma1) B);

    }

    private MatrizForma1 multiplyF1(MatrizForma1 matrizB) {

        if (getNumColumnas() != matrizB.getNumFilas()) {

            return null;

        }

        NodoDobleT nodoFilaA, nodoColumnaB, nodoC;
        int s;

        MatrizForma1 matrizC = new MatrizForma1(getNumFilas(), matrizB.getNumColumnas());

        nodoFilaA = getPrimerNodo();

        while (nodoFilaA != getNodoCabeza()) {

            nodoColumnaB = matrizB.getPrimerNodo();

            while (nodoColumnaB != matrizB.getNodoCabeza()) {

                s = smult(nodoFilaA, nodoColumnaB);
                if (s != 0) {

                    nodoC = new NodoDobleT(new Tripleta(nodoFilaA.getFila(), nodoColumnaB.getColumna(), s));
                    matrizC.conectar(nodoC);

                }

                nodoColumnaB = (NodoDobleT) nodoColumnaB.getValor();

            }

            nodoFilaA = (NodoDobleT) nodoFilaA.getValor();

        }

        return matrizC;

    }

    private int smult(NodoDobleT nodoFilaA, NodoDobleT nodoColumnaB) {

        int s = 0;
        NodoDobleT nodoA, nodoB;
        nodoA = nodoFilaA.getLd();
        nodoB = nodoColumnaB.getLi();

        while (nodoA != nodoFilaA && nodoB != nodoColumnaB) {

            switch (Integer.compare(nodoA.getColumna(), nodoB.getFila())) {

                case 1:

                    nodoB = nodoB.getLi();
                    break;

                case -1:

                    nodoA = nodoA.getLd();
                    break;

                case 0:

                    s += (int) nodoA.getValor() * (int) nodoB.getValor();
                    nodoA = nodoA.getLd();
                    nodoB = nodoB.getLi();
                    break;

            }

        }

        return s;

    }

    @Override
    public String toString() {

        String s = String.format("{%d, %d}\n", getNumFilas(), getNumColumnas());

        NodoDobleT nodoFila, nodoX;

        nodoFila = getPrimerNodo();
        while (nodoFila != getNodoCabeza()) {

            s = s.concat("  |  \n");
            s = s.concat(String.format("[%d, %d]", nodoFila.getFila(), nodoFila.getColumna()));

            nodoX = nodoFila.getLd();
            while (nodoX != nodoFila) {

                s = s.concat(" -> " + nodoX.getTripleta().toString());

                nodoX = nodoX.getLd();
            }

            s = s.concat("\n");
            nodoFila = (NodoDobleT) nodoFila.getValor();

        }

        return s;

    }

    public String toMatrixRepr(int widthFix) {

        String s, line;
        line = ("┬" + "─".repeat(widthFix)).repeat(getNumColumnas()) + "┐\n";
        line = line.replaceFirst("┬", "┌");
        s = line;

        NodoDobleT nodoX, nodoFila;
        Tripleta t;

        nodoFila = getPrimerNodo();

        for (int i = 0; i < getNumFilas(); i++) {

            nodoX = nodoFila.getLd();
            for (int j = 0; j < getNumColumnas(); j++) {

                if (nodoX != nodoFila && nodoX.getFila() == i && nodoX.getColumna() == j) {

                    s = s.concat(String.format("│%" + widthFix + "d", nodoX.getValor()));

                    nodoX = nodoX.getLd();

                } else {

                    s = s.concat(String.format("│%" + widthFix + "d", 0));

                }

            }

            if (i < getNumFilas() - 1) {

                line = "│\n" + ("┼" + "─".repeat(widthFix)).repeat(getNumColumnas()) + "┤\n";
                line = line.replaceFirst("┼", "├");

                t = nodoFila.getTripleta();
                nodoFila = (NodoDobleT) t.getValor();

            } else {

                line = "│\n" + ("┴" + "─".repeat(widthFix)).repeat(getNumColumnas()) + "┘\n";
                line = line.replaceFirst("┴", "└");

            }
            s = s.concat(line);

        }

        return s;

    }

    public void show() {

        System.out.println(toString());

    }

    public void showAsMatrixRepr() {

        System.out.println(toMatrixRepr(4));

    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        int[][] m = {
                { -2, 3 },
                { -5, -5 },
                { 0, -6 }
        };

        int[][] m2 = {
                { 1, -5, 0 },
                { -8, 5, 2 },
        };

        int[][] m3 = {
                { 2, 2 },
                { 2, 0 },
                { 0, -12 }
        };
        MatrizForma1 a = new MatrizForma1(m);
        MatrizForma1 b = new MatrizForma1(m2);
        MatrizForma1 c = new MatrizForma1(m3);

        a.showAsMatrixRepr();
        System.out.println("     *\n");
        b.showAsMatrixRepr();

        System.out.println("     =\n");

        MatrizForma1 s = a.multiply(b);

        s.showAsMatrixRepr();
        s.show();

        s.set(1, 1, 100);
        s.showAsMatrixRepr();
        s.show();

        System.out.println(s.get(2, 1));

    }

}
