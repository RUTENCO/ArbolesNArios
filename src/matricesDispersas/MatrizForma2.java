package matricesDispersas;

import nodos.NodoDobleT;

public class MatrizForma2 implements MatrizDispersa {

    private NodoDobleT nodoCabeza;

    public MatrizForma2(int m, int n) {

        this.nodoCabeza = new NodoDobleT(new Tripleta(m, n, 0));
        this.nodoCabeza.setLd(nodoCabeza);
        this.nodoCabeza.setLi(nodoCabeza);

    }

    public MatrizForma2(int[][] m) {

        this.nodoCabeza = new NodoDobleT(new Tripleta(m.length, m[0].length, 0));
        this.nodoCabeza.setLd(nodoCabeza);
        this.nodoCabeza.setLi(nodoCabeza);

        for (int i = 0; i < m.length; i++) {

            for (int j = 0; j < m[0].length; j++) {

                if (m[i][j] != 0) {

                    conectar(new NodoDobleT(new Tripleta(i, j, m[i][j])));

                }

            }

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

    public boolean isEmpty() {

        return getNodoCabeza().getLd() == getNodoCabeza();

    }

    public boolean finDeRecorrido(NodoDobleT p) {

        return p == getNodoCabeza();

    }

    @Override
    public Object get(int i, int j) {

        if (i < 0 || j < 0 || i >= getNumFilas() || j >= getNumColumnas()) {

            throw new IndexOutOfBoundsException();

        }

        NodoDobleT nodoX = getNodoCabeza().getLd();

        while (nodoX != getNodoCabeza() && i > nodoX.getFila()) {

            nodoX = nodoX.getLd();

        }

        while (nodoX != getNodoCabeza() && i == nodoX.getFila() && j > nodoX.getColumna()) {

            nodoX = nodoX.getLd();

        }

        if (nodoX != getNodoCabeza() && i == nodoX.getFila() && j == nodoX.getColumna()) {

            return nodoX.getValor();

        }

        return 0;

    }

    @Override
    public void set(int i, int j, Object val) {

        if (i < 0 || j < 0 || i >= getNumFilas() || j >= getNumColumnas()) {

            throw new IndexOutOfBoundsException();

        }

        NodoDobleT nodoX = getNodoCabeza().getLd();

        while (nodoX != getNodoCabeza() && i > nodoX.getFila()) {

            nodoX = nodoX.getLd();

        }

        while (nodoX != getNodoCabeza() && i == nodoX.getFila() && j > nodoX.getColumna()) {

            nodoX = nodoX.getLd();

        }

        if (nodoX != getNodoCabeza() && i == nodoX.getFila() && j == nodoX.getColumna()) {

            nodoX.setValor(val);
            return;

        }

        conectar(new NodoDobleT(new Tripleta(i, j, val)));

    }

    public void conectar(NodoDobleT x) {

        conectarPorFilas(x);
        conectarPorColumnas(x);

    }

    private void conectarPorFilas(NodoDobleT x) {

        NodoDobleT antX, sigX;
        antX = getNodoCabeza();
        sigX = antX.getLd();

        while (sigX != getNodoCabeza() && x.getFila() > sigX.getFila()) {

            antX = sigX;
            sigX = antX.getLd();

        }

        while (sigX != getNodoCabeza() && x.getFila() == sigX.getFila() && x.getColumna() > sigX.getColumna()) {

            antX = sigX;
            sigX = antX.getLd();

        }

        if (sigX != getNodoCabeza() && x.getFila() == sigX.getFila() && x.getColumna() == sigX.getColumna()) {

            sigX.setValor(x.getValor());
            return;

        }

        x.setLd(sigX);
        antX.setLd(x);

    }

    private void conectarPorColumnas(NodoDobleT x) {

        NodoDobleT antX, sigX;
        antX = getNodoCabeza();
        sigX = antX.getLi();

        while (sigX != getNodoCabeza() && x.getColumna() > sigX.getColumna()) {

            antX = sigX;
            sigX = antX.getLi();

        }

        while (sigX != getNodoCabeza() && x.getColumna() == sigX.getColumna() && x.getFila() > sigX.getFila()) {

            antX = sigX;
            sigX = antX.getLi();

        }

        if (sigX != getNodoCabeza() && x.getColumna() == sigX.getColumna() && x.getFila() == sigX.getFila()) {

            sigX.setValor(x.getValor());
            return;

        }

        x.setLi(sigX);
        antX.setLi(x);

    }

    @Override
    public MatrizForma2 sum(MatrizDispersa B) {

        return sumF2((MatrizForma2) B);

    }

    private MatrizForma2 sumF2(MatrizForma2 matrizB) {

        if (getNumFilas() != matrizB.getNumFilas() || getNumColumnas() != matrizB.getNumColumnas()) {

            return null;

        }

        MatrizForma2 matrizC = new MatrizForma2(getNumFilas(), getNumColumnas());

        NodoDobleT nodoA, nodoB, nodoC;
        int s;

        nodoA = getNodoCabeza().getLd();
        nodoB = matrizB.getNodoCabeza().getLd();

        while (nodoA != getNodoCabeza() && nodoB != matrizB.getNodoCabeza()) {

            switch (Integer.compare(nodoA.getFila(), nodoB.getFila())) {

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

                    break;

            }

        }

        return matrizC;

    }

    @Override
    public MatrizForma2 multiply(MatrizDispersa B) {

        return multiplyF2((MatrizForma2) B);

    }

    private MatrizForma2 multiplyF2(MatrizForma2 matrizB) {

        if (getNumColumnas() != matrizB.getNumFilas()) {

            return null;

        }

        MatrizForma2 matrizC = new MatrizForma2(getNumFilas(), matrizB.getNumColumnas());

        NodoDobleT nodoFilaA, nodoColumnaB, nodoC;
        int s, fActual, cActual;

        nodoFilaA = getNodoCabeza().getLd();
        fActual = nodoFilaA.getFila();

        while (nodoFilaA != getNodoCabeza()) {

            nodoColumnaB = matrizB.getNodoCabeza().getLi();
            cActual = nodoColumnaB.getColumna();

            while (nodoColumnaB != matrizB.getNodoCabeza()) {

                s = smult(nodoFilaA, nodoColumnaB);
                if (s != 0) {

                    nodoC = new NodoDobleT(new Tripleta(nodoFilaA.getFila(), nodoColumnaB.getColumna(), s));
                    matrizC.conectar(nodoC);

                }

                while (nodoColumnaB != matrizB.getNodoCabeza() && nodoColumnaB.getColumna() == cActual) {

                    nodoColumnaB = nodoColumnaB.getLi();

                }

                cActual = nodoColumnaB.getColumna();

            }

            while (nodoFilaA != getNodoCabeza() && nodoFilaA.getFila() == fActual) {

                nodoFilaA = nodoFilaA.getLd();

            }

            fActual = nodoFilaA.getFila();

        }

        return matrizC;

    }

    private int smult(NodoDobleT nodoFilaA, NodoDobleT nodoColumnaB) {

        int s = 0;
        NodoDobleT nodoA, nodoB;

        nodoA = nodoFilaA;
        nodoB = nodoColumnaB;

        while (nodoA.getFila() == nodoFilaA.getFila() && nodoB.getColumna() == nodoColumnaB.getColumna()) {

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

        String s = String.format("{%d, %d}", getNumFilas(), getNumColumnas());

        NodoDobleT nodoX;
        int f = 0;

        nodoX = getNodoCabeza().getLd();

        while (nodoX != nodoCabeza) {

            while (nodoX.getFila() == f) {

                s = s.concat(" -> " + nodoX.getTripleta().toString());

                nodoX = nodoX.getLd();

            }

            f++;

        }

        return s;

    }

    public String toMatrixRepr(int widthFix) {

        String s, line;
        line = ("┬" + "─".repeat(widthFix)).repeat(getNumColumnas()) + "┐\n";
        line = line.replaceFirst("┬", "┌");
        s = line;

        NodoDobleT nodoX;

        nodoX = getNodoCabeza().getLd();

        for (int i = 0; i < getNumFilas(); i++) {

            for (int j = 0; j < getNumColumnas(); j++) {

                if (nodoX != getNodoCabeza() && nodoX.getFila() == i && nodoX.getColumna() == j) {

                    s = s.concat(String.format("│%" + widthFix + "d", nodoX.getValor()));

                    nodoX = nodoX.getLd();

                } else {

                    s = s.concat(String.format("│%" + widthFix + "d", 0));

                }

            }

            if (i < getNumFilas() - 1) {

                line = "│\n" + ("┼" + "─".repeat(widthFix)).repeat(getNumColumnas()) + "┤\n";
                line = line.replaceFirst("┼", "├");

            } else {

                line = "│\n" + ("┴" + "─".repeat(widthFix)).repeat(getNumColumnas()) + "┘\n";
                line = line.replaceFirst("┴", "└");

            }
            s = s.concat(line);

        }

        return s;

    }

    public void showAsMatrixRepr() {

        System.out.println(toMatrixRepr(4));

    }

    public void show() {

        System.out.println(toString());

    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {

        int[][] m = {
                { -2, 3 },
                { -5, -5 },
                { 0, -6 },
                { 0, 0 },
                { 40, 1 },
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

        MatrizForma2 a = new MatrizForma2(m);
        MatrizForma2 b = new MatrizForma2(m2);
        MatrizForma2 c = new MatrizForma2(m3);
        
        a.showAsMatrixRepr();
        System.out.println("   *\n");
        b.showAsMatrixRepr();

        System.out.println("   =\n");

        MatrizForma2 s = a.multiply(b);
        s.showAsMatrixRepr();
        s.show();

    }

}
