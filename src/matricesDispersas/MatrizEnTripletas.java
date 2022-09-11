package matricesDispersas;

import java.util.ArrayList;

public class MatrizEnTripletas implements MatrizDispersa {

    private ArrayList<Tripleta> V;

    public MatrizEnTripletas(int m, int n) {

        this.V = new ArrayList<Tripleta>();
        this.V.add(new Tripleta(m, n, 0));

    }

    public MatrizEnTripletas(int[][] m) {

        this.V = new ArrayList<Tripleta>();
        this.V.add(new Tripleta(m.length, m[0].length, 0));

        for (int i = 0; i < m.length; i++) {

            for (int j = 0; j < m[i].length; j++) {

                if (m[i][j] != 0) {
                    add(i, j, m[i][j]);
                }

            }

        }

    }

    public int getNumTripletas() {

        return (int) V.get(0).getValor();

    }

    public int getNumFilas() {

        return V.get(0).getFila();

    }

    public int getNumColumnas() {

        return V.get(0).getColumna();

    }

    protected void setNumTripletas(int numTripletas) {

        V.get(0).setValor(numTripletas);

    }

    protected void incrementNumTripletas() {

        setNumTripletas(getNumTripletas() + 1);

    }

    public Tripleta getTripleta(int idx) {

        assert (idx > 0 && idx <= getNumTripletas());

        return V.get(idx);

    }

    public void setTripleta(Tripleta t, int idx) {

        assert (idx > 0 && idx <= getNumTripletas());

        V.set(idx, t);

    }

    public void addTripleta(Tripleta t) {

        assert (t.getFila() > -1 && t.getFila() < getNumFilas());
        assert (t.getColumna() > -1 && t.getColumna() < getNumColumnas());

        V.add(t);
        incrementNumTripletas();

    }

    public void add(int f, int c, int v) {

        addTripleta(new Tripleta(f, c, v));

    }

    @Override
    public Object get(int i, int j) {

        if (i < 0 || j < 0 || i >= getNumFilas() || j >= getNumColumnas()) {

            throw new IndexOutOfBoundsException();

        }

        Tripleta t;
        int idx = 1;

        while (idx <= getNumTripletas() && i > (t = getTripleta(idx)).getFila()) {

            idx++;

        }

        while (idx <= getNumTripletas() && i == (t = getTripleta(idx)).getFila() && j > t.getColumna()) {

            idx++;

        }

        if (idx <= getNumTripletas() && i == (t = getTripleta(idx)).getFila() && j == t.getColumna()) {

            return t.getValor();

        }

        return 0;

    }

    @Override
    public void set(int i, int j, Object val) {

        if (i < 0 || j < 0 || i >= getNumFilas() || j >= getNumColumnas()) {

            throw new IndexOutOfBoundsException();

        }

        Tripleta t;
        int idx = 1;

        while (idx <= getNumTripletas() && i > (t = getTripleta(idx)).getFila()) {

            idx++;

        }

        while (idx <= getNumTripletas() && i == (t = getTripleta(idx)).getFila() && j > t.getColumna()) {

            idx++;

        }

        if (idx <= getNumTripletas() && i == (t = getTripleta(idx)).getFila() && j == t.getColumna()) {

            t.setValor(val);
            return;

        }

        insertTripleta(new Tripleta(i, j, val), true);

    }

    public void insertTripleta(Tripleta t, boolean replace) {

        int idx = 1;

        while (idx <= getNumTripletas() && t.getFila() > V.get(idx).getFila()) {
            idx++;
        }

        while (idx <= getNumTripletas() && t.getColumna() > V.get(idx).getColumna()) {
            idx++;
        }

        if (t.getFila() == V.get(idx).getFila() && t.getColumna() == V.get(idx).getColumna()) {

            if (replace) {

                setTripleta(t, idx);
                return;

            }

        }

        V.add(null);

        for (int last = getNumTripletas() + 1; last > idx; last--) {

            V.set(last, V.get(last - 1));

        }

        V.set(idx, t);
        incrementNumTripletas();

    }

    public void swapRows(int row0, int row1) {

        Tripleta tRowMin, tRowMax;
        int i, j, rowMin, rowMax;

        rowMin = Math.min(row0, row1);
        rowMax = Math.max(row0, row1);

        i = 1;
        while (i <= getNumTripletas() && rowMin > getTripleta(i).getFila()) {
            i++;
        }

        j = i + 1;
        while (j <= getNumTripletas() && rowMax > getTripleta(j).getFila()) {
            j++;
        }

        while (i <= getNumTripletas() && j <= getNumTripletas()) {

            tRowMin = getTripleta(i);
            tRowMax = getTripleta(j);

            if (tRowMin.getFila() != rowMin || tRowMax.getFila() != rowMax) {
                break;
            }

            tRowMin.setFila(rowMax);
            tRowMax.setFila(rowMin);

            setTripleta(tRowMax, i);
            setTripleta(tRowMin, j);

            i++;
            j++;

        }

        while (i <= getNumTripletas()) {

            tRowMin = getTripleta(i);

            if (tRowMin.getFila() != rowMin) {
                break;
            }

            for (int k = i; k < j - 1; k++) {

                setTripleta(getTripleta(k + 1), k);

            }

            tRowMin.setFila(rowMax);
            setTripleta(tRowMin, j - 1);

            i++;
        }

        while (j <= getNumTripletas()) {

            tRowMax = getTripleta(j);

            if (tRowMax.getFila() != rowMax) {
                break;
            }

            for (int k = j; k > i; k--) {

                setTripleta(getTripleta(k - 1), k);

            }

            tRowMax.setFila(rowMin);
            setTripleta(tRowMax, i);

            j++;
        }

    }

    @Override
    public MatrizEnTripletas sum(MatrizDispersa B) {

        return sumT((MatrizEnTripletas) B);

    }

    private MatrizEnTripletas sumT(MatrizEnTripletas b) {

        assert (getNumFilas() == b.getNumFilas() && getNumColumnas() == b.getNumColumnas());

        Tripleta tA, tB, tC;
        MatrizEnTripletas c = new MatrizEnTripletas(getNumFilas(), getNumColumnas());

        int i = 1, j = 1;

        while (i <= getNumTripletas() && j <= b.getNumTripletas()) {

            tA = getTripleta(i);
            tB = b.getTripleta(j);

            switch (Integer.compare(tA.getFila(), tB.getFila())) {

                case -1:

                    c.addTripleta(tA.clone());
                    i++;
                    break;

                case +1:

                    c.addTripleta(tB.clone());
                    j++;
                    break;

                case 0:

                    switch (Integer.compare(tA.getColumna(), tB.getColumna())) {

                        case -1:

                            c.addTripleta(tA.clone());
                            i++;
                            break;

                        case +1:

                            c.addTripleta(tB.clone());
                            j++;
                            break;

                        case 0:

                            tC = new Tripleta(tA.getFila(), tA.getColumna(), (int) tA.getValor() + (int) tB.getValor());
                            if ((int) tC.getValor() != 0) {

                                c.addTripleta(tC);

                            }

                            i++;
                            j++;
                            break;

                    }
                    break;
            }

        }
        while (i <= getNumTripletas()) {

            tA = getTripleta(i);
            c.addTripleta(tA.clone());
            i++;

        }
        while (j <= b.getNumTripletas()) {

            tB = b.getTripleta(j);
            c.addTripleta(tB.clone());
            j++;

        }

        return c;

    }

    @Override
    public MatrizEnTripletas multiply(MatrizDispersa B) {

        return multiplyT((MatrizEnTripletas) B);

    }

    private MatrizEnTripletas multiplyT(MatrizEnTripletas b) {

        assert (getNumColumnas() == b.getNumFilas());

        MatrizEnTripletas c, bT;
        int i, j, s;

        c = new MatrizEnTripletas(getNumFilas(), b.getNumColumnas());
        bT = b.transpuesta();

        for (i = 0; i < getNumFilas(); i++) {

            for (j = 0; j < b.getNumColumnas(); j++) {

                s = smult(bT, i, j);
                if (s != 0) {

                    c.add(i, j, s);

                }

            }

        }

        return c;

    }

    private int smult(MatrizEnTripletas bT, int fila0, int fila1) {

        Tripleta tA, tB;
        int s, idxFilaA, idxFilaB;

        idxFilaA = 1;
        while (idxFilaA <= getNumTripletas() && fila0 > getTripleta(idxFilaA).getFila()) {

            idxFilaA++;

        }

        idxFilaB = 1;
        while (idxFilaB <= getNumTripletas() && fila1 > bT.getTripleta(idxFilaB).getFila()) {

            idxFilaB++;

        }

        s = 0;
        while (idxFilaA <= getNumTripletas() && idxFilaB <= bT.getNumTripletas()) {

            tA = getTripleta(idxFilaA);
            tB = bT.getTripleta(idxFilaB);

            if (tA.getFila() != fila0 || tB.getFila() != fila1) {
                break;
            }

            switch (Integer.compare(tA.getColumna(), tB.getColumna())) {

                case 1:

                    idxFilaB++;
                    break;

                case -1:

                    idxFilaA++;
                    break;

                case 0:
                    s += (int) tA.getValor() * (int) tB.getValor();
                    idxFilaA++;
                    idxFilaB++;
                    break;

            }

        }

        return s;

    }

    public MatrizEnTripletas transpuesta() {

        int m, n, p, S[], T[];
        Tripleta t, tAT;
        m = getNumFilas();
        n = getNumColumnas();
        p = getNumTripletas();

        MatrizEnTripletas AT = new MatrizEnTripletas(n, m);

        for (int i = 1; i <= p; i++) {

            AT.V.add(null);

        }

        S = new int[n];
        T = new int[n];

        for (int idx = 1; idx <= p; idx++) {

            t = getTripleta(idx);
            S[t.getColumna()]++;

        }

        T[0] = 1;
        for (int i = 1; i < n; i++) {

            T[i] = T[i - 1] + S[i - 1];

        }

        for (int idx = 1; idx <= p; idx++) {

            t = getTripleta(idx);
            tAT = new Tripleta(t.getColumna(), t.getFila(), t.getValor());
            AT.setTripleta(tAT, T[t.getColumna()]);
            T[t.getColumna()]++;

        }

        AT.setNumTripletas(p);
        return AT;

    }

    @Override
    public String toString() {

        String s = String.format("[%d, %d, %d]\n", getNumFilas(), getNumColumnas(), getNumTripletas());

        for (int i = 1; i <= getNumTripletas(); i++) {

            s = s.concat(V.get(i).toString()).concat("\n");

        }

        return s;

    }

    public String toMatrixRepr(int widthFix) {

        String s, line;
        line = ("┬" + "─".repeat(widthFix)).repeat(getNumColumnas()) + "┐\n";
        line = line.replaceFirst("┬", "┌");
        s = line;

        int idx = 1;
        Tripleta t;

        t = getTripleta(idx);
        for (int i = 0; i < getNumFilas(); i++) {

            for (int j = 0; j < getNumColumnas(); j++) {

                if (idx <= getNumTripletas() && t.getFila() == i && t.getColumna() == j) {

                    s = s.concat(String.format("│%4d", t.getValor()));

                    idx++;
                    if (idx <= getNumTripletas()) {

                        t = getTripleta(idx);

                    }

                } else {

                    s = s.concat("│   0");

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

    public void show() {

        System.out.println(toString());

    }

    public void showAsMatrixRepr() {

        System.out.println(toMatrixRepr(4));

    }

    public static void main(String[] args) {

        int[][] m = {
                { -2, 3 },
                { -5, 1 },
                { 0, -6 }
        };

        int[][] m2 = {
                { 1, -5, 0 },
                { -8, 9, 2 },
        };

        MatrizEnTripletas a, b, c;

        a = new MatrizEnTripletas(m);
        b = new MatrizEnTripletas(m2);

        a.showAsMatrixRepr();
        System.out.println("    *\n");
        b.showAsMatrixRepr();

        System.out.println("---------");

        c = a.multiplyT(b);
        c.showAsMatrixRepr();
        c.show();

        c.set(2, 2, 12);
        c.showAsMatrixRepr();
        c.show();

    }

}
