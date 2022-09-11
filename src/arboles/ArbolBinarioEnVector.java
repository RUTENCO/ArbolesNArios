package arboles;

import java.util.Comparator;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class ArbolBinarioEnVector extends Vector<Object> {

    public ArbolBinarioEnVector() {

        super(0, 1);

    }

    public ArbolBinarioEnVector(int[] array) {

        super(array.length, 1);

        for (int i = 0; i < array.length; i++) {
            this.add(array[i]);
        }

    }

    protected static void consABV(ArbolBinarioEnVector A, String arbolStr, AtomicInteger globalCharIdx,
            int parentIdx) {

        if (arbolStr.trim().length() == 0) {

            A.setSize(0);
            return;

        }

        int charIdx, childIdx;
        childIdx = A.leftChildIdx(parentIdx);

        charIdx = globalCharIdx.get();
        String atomo = "";

        // xx.
        // a(b(c, d(e)), g(h, i(j, k(l))))
        do {

            switch (arbolStr.charAt(charIdx)) {

                case '(':

                    if (atomo.trim().length() == 0) {
                        break;
                    }

                    A.setSize(childIdx + 1);
                    A.set(childIdx, atomo.trim());

                    globalCharIdx.set(charIdx + 1);
                    consABV(A, arbolStr, globalCharIdx, childIdx);
                    charIdx = globalCharIdx.get();

                    atomo = "";
                    break;

                case ',':

                    if (atomo.trim().length() > 0) {

                        A.setSize(childIdx + 1);
                        A.set(childIdx, atomo.trim());

                    }

                    childIdx = A.rightChildIdx(parentIdx);
                    atomo = "";
                    break;

                case ')':

                    if (atomo.trim().length() > 0) {

                        A.setSize(childIdx + 1);
                        A.set(childIdx, atomo.trim());

                    }

                    globalCharIdx.set(charIdx);
                    return;

                default:

                    atomo = atomo.concat(arbolStr.substring(charIdx, charIdx + 1));
                    break;

            }

            charIdx++;

        } while (charIdx < arbolStr.length());

        globalCharIdx.set(charIdx);

    }

    public int leftChildIdx(int parentIdx) {

        if (parentIdx < 0)
            return 0;

        return 2 * parentIdx + 1;

    }

    public int rightChildIdx(int parentIdx) {

        if (parentIdx < 0)
            return 0;

        return 2 * parentIdx + 2;

    }

    public int parentIdx(int childIdx) {

        return (childIdx - 1) / 2;

    }

    public void setLeftChild(int parentIdx, Object value) {

        set(leftChildIdx(parentIdx), value);

    }

    public void setRightChild(int parentIdx, Object value) {

        set(rightChildIdx(parentIdx), value);

    }

    public void crearHeap(int parentIdx, int n) {

        int d, childIdx;

        d = (int) get(parentIdx);
        childIdx = leftChildIdx(parentIdx);

        while (childIdx < n) {

            if (childIdx < n - 1 && (int) get(childIdx) < (int) get(childIdx + 1)) {

                childIdx++;

            }

            if (d >= (int) get(childIdx)) {

                set(parentIdx(childIdx), d);
                return;

            }

            set(parentIdx(childIdx), get(childIdx));
            childIdx = leftChildIdx(childIdx);

        }

        set(parentIdx(childIdx), d);

    }

    public void heapSort() {

        // se crea los heaps para todos los subarboles, empezando desde el último
        // subarbol
        for (int i = parentIdx(size() - 1); i >= 0; i--) {

            crearHeap(i, size());

        }

        int aux;
        // el dato mayor queda en la primera posición del vector
        for (int i = size() - 2; i >= 0; i--) {

            aux = (int) get(0);
            set(0, get(i + 1));
            set(i + 1, aux);

            crearHeap(0, i);

        }

    }

    public String toVecRepr(int fieldWidth) {

        StringBuilder topLine, line, bottomLine;

        int size = size();

        // └ ┘ ┌ ┐ ─ │ ┼ ┴ ┬ ┤ ├
        topLine = new StringBuilder(("┬" + "─".repeat(fieldWidth)).repeat(size));
        topLine.replace(0, 1, "┌").append("┐");

        bottomLine = new StringBuilder(("┴" + "─".repeat(fieldWidth)).repeat(size));
        bottomLine.replace(0, 1, "└").append("┘");

        line = new StringBuilder("");

        String v;

        for (int i = 0; i < size; i++) {

            v = get(i) == null ? "" : get(i).toString();
            line.append(String.format("│%" + fieldWidth + "s", v));

        }

        line.append("│");

        return topLine.toString() + "\n" +
                line.toString() + "\n" +
                bottomLine.toString();

    }

    public String strRepr(int parentIdx) {

        if (parentIdx >= size() || get(parentIdx) == null) {

            return "";

        }

        String left, right, s;

        s = get(parentIdx).toString();

        left = strRepr(leftChildIdx(parentIdx));
        right = strRepr(rightChildIdx(parentIdx));

        if (left.length() > 0 && right.length() > 0) {

            s = s.concat(String.format("(%s, %s)", left, right));

        } else if (left.length() > 0) {

            s = s.concat(String.format("(%s)", left));

        } else if (right.length() > 0) {

            s = s.concat(String.format("(%s)", right));

        }

        return s;

    }

    @Override
    public synchronized String toString() {

        return strRepr(0);

    }

    public static <T> void main(String[] args) {

        int[] A = { 5, 9, 4, 1, 6, 2, 8, 3, 4, 45, 35, 12, 48, 6, 87, 95, 12, 32, 54 };

        long start, end;

        ArbolBinarioEnVector aV = new ArbolBinarioEnVector(A);

        System.out.println(aV.toVecRepr(2));

        start = System.nanoTime();
        aV.heapSort();
        end = System.nanoTime();

        System.out.println(aV.toVecRepr(2) + "time:" + (end - start));

        start = System.nanoTime();
        aV.sort(new Comparator<Object>() {

            @Override
            public int compare(Object o1, Object o2) {
                return Math.max((int) o1, (int) o2);
            }

        });
        end = System.nanoTime();

        System.out.println(aV.toVecRepr(2) + "time:" + (end - start));

    }

}