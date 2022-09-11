package arboles;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class ArbolAVL extends ArbolBinarioLL {

    private int Fb;

    public ArbolAVL(Object dato) {

        super(dato);
        recalculateFb();

    }

    // a(b(c, d(e)), g(h, i(j, k(l))))
    protected static ArbolAVL consArbolAVL(String arbolStr, AtomicInteger globalIdx) {

        ArbolAVL A, subArbol;

        A = null;

        String atomo;
        int charIdx = globalIdx.get();

        atomo = "";

        while (charIdx < arbolStr.length()) {

            switch (arbolStr.charAt(charIdx)) {

                case '(':

                    A = new ArbolAVL(atomo.trim());

                    globalIdx.set(charIdx + 1);
                    subArbol = consArbolAVL(arbolStr, globalIdx);
                    if (subArbol != null) {
                        subArbol.recalculateFb();
                    }
                    charIdx = globalIdx.get();

                    A.setLi(subArbol);

                    if (arbolStr.charAt(charIdx) == ',') {

                        globalIdx.set(charIdx + 1);
                        subArbol = consArbolAVL(arbolStr, globalIdx);
                        if (subArbol != null) {
                            subArbol.recalculateFb();
                        }
                        charIdx = globalIdx.get();

                        A.setLd(subArbol);

                    }

                    atomo = "";
                    break;

                case ',':

                    if (atomo.trim().length() > 0) {

                        A = new ArbolAVL(atomo.trim());

                    }

                    globalIdx.set(charIdx);
                    return A;

                case ')':

                    if (atomo.trim().length() > 0) {

                        A = new ArbolAVL(atomo.trim());

                    }

                    globalIdx.set(charIdx);
                    return A;

                default:

                    atomo = atomo.concat(arbolStr.substring(charIdx, charIdx + 1));
                    break;

            }

            charIdx++;

        }

        A.recalculateFb();
        return A;

    }

    public ArbolAVL insert(Object d, Comparator<Object> comparator) {

        if (getDato() == null) {
            setDato(d);
            return this;
        }

        ArbolAVL p, q, pivote, pp, arbolX;

        arbolX = new ArbolAVL(d);

        p = this;
        q = null;
        pivote = p;
        pp = q;

        while (p != null) {

            // dato repetido
            if (comparator.compare(p.getDato(), d) == 0) {
                return this;
            }

            if (p.getFb() != 0) {
                pivote = p;
                pp = q;
            }

            q = p;

            if (comparator.compare(d, p.getDato()) > 0) {

                p = (ArbolAVL) p.getRightChild();

            } else {

                p = (ArbolAVL) p.getLeftChild();

            }

        }

        if (comparator.compare(d, q.getDato()) < 0) {

            q.setLi(arbolX);

        } else {

            q.setLd(arbolX);

        }

        if (comparator.compare(d, pivote.getDato()) < 0) {

            pivote.setFb(pivote.getFb() + 1);
            q = (ArbolAVL) pivote.getLeftChild();

        } else {

            pivote.setFb(pivote.getFb() - 1);
            q = (ArbolAVL) pivote.getRightChild();

        }

        p = q;

        while (p != arbolX) {

            if (comparator.compare(d, p.getDato()) < 0) {

                p.setFb(p.getFb() + 1);
                p = (ArbolAVL) p.getLeftChild();

            } else {

                p.setFb(p.getFb() - 1);
                p = (ArbolAVL) p.getRightChild();

            }

        }

        if (Math.abs(pivote.getFb()) < 2) {
            return this;
        }

        if (pivote.getFb() == 2) {

            if (q.getFb() == 1) {

                p = pivote.singleRightRotation();

            } else {

                p = pivote.doubleRightRotation();

            }

        } else {

            if (q.getFb() == 1) {

                p = pivote.singleLeftRotation();

            } else {

                p = pivote.doubleLeftRotation();

            }

        }

        if (pp == null) {
            return p;
        }

        if (comparator.compare(d, pp.getDato()) < 0) {

            pp.setLi(p);

        } else {

            pp.setLd(p);

        }

        return p;

    }

    protected void recalculateFb() {

        int leftHeight, rightHeight;

        leftHeight = (getLeftChild() == null) ? 0 : getLeftChild().getHeight();
        rightHeight = (getRightChild() == null) ? 0 : getRightChild().getHeight();

        setFb(leftHeight - rightHeight);

    }

    public int getFb() {
        return Fb;
    }

    public void setFb(int fb) {
        Fb = fb;
    }

    public boolean isBalanced() {

        return Math.abs(Fb) < 2;

    }

    // ----------------------------------------------------------------

    // Fb(p) == -2, Fb(q) == -1
    public ArbolAVL singleLeftRotation() {

        ArbolAVL p, q;
        p = this;
        q = (ArbolAVL) p.getRightChild();

        assert (p.getFb() == -2 && q.getFb() == -1);

        p.setLd(q.getLeftChild());
        q.setLi(p);
        p.setFb(0);
        q.setFb(0);

        return q;

    }

    // Fb(p) == + 2, Fb(q) == + 1
    public ArbolAVL singleRightRotation() {

        ArbolAVL p, q;
        p = this;
        q = (ArbolAVL) p.getLeftChild();

        assert (p.getFb() == +2 && q.getFb() == +1);

        p.setLi(q.getRightChild());
        q.setLd(p);
        p.setFb(0);
        q.setFb(0);

        return q;

    }

    // Fb(p) == -2, Fb(q) == +1
    public ArbolAVL doubleLeftRotation() {

        ArbolAVL p, q, r;

        p = this;
        q = (ArbolAVL) p.getRightChild();
        r = (ArbolAVL) q.getLeftChild();

        assert (p.getFb() == -2 && q.getFb() == +1);

        q.setLi(r.getRightChild());
        r.setLd(q);
        p.setLd(r.getLeftChild());
        r.setLi(p);

        switch (r.getFb()) {

            case 0:

                p.setFb(0);
                q.setFb(0);
                break;

            case +1:

                p.setFb(0);
                q.setFb(-1);
                break;

            case -1:

                p.setFb(1);
                q.setFb(0);
                break;

        }

        r.setFb(0);

        return r;

    }

    // Fb(p) == +2, fb(q) == -1
    public ArbolAVL doubleRightRotation() {

        ArbolAVL p, q, r;

        p = this;
        q = (ArbolAVL) p.getLeftChild();
        r = (ArbolAVL) q.getRightChild();

        assert (p.getFb() == +2 && q.getFb() == -1);

        q.setLd(r.getLeftChild());
        r.setLi(q);
        p.setLi(r.getRightChild());
        r.setLd(p);
        switch (r.getFb()) {

            case 0:

                p.setFb(0);
                q.setFb(0);
                break;

            case +1:

                p.setFb(-1);
                q.setFb(0);
                break;

            case -1:

                p.setFb(0);
                q.setFb(1);
                break;

        }

        r.setFb(0);

        return r;

    }

    // ----------------------------------------------------------------

    public String preorden() {

        String left, r, right;
        ArbolAVL leftChild, rightChild;

        leftChild = (ArbolAVL) getLeftChild();
        rightChild = (ArbolAVL) getRightChild();

        r = getDato().toString();
        left = (leftChild == null) ? "" : leftChild.preorden();
        right = (rightChild == null) ? "" : rightChild.preorden();

        return String.format("%s %s, %s", left, r, right);

    }

    public static void main(String[] args) {

        String arbolStr = "5(3)";
        ArbolAVL A;
        try {
            A = Arboles.consArbolAVL(arbolStr);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        A.showAsTreeRepr(2);

        System.out.println(A.preorden());

        A = A.insert(4, new Comparator<Object>() {

            @Override
            public int compare(Object o1, Object o2) {

                int i1, i2;

                i1 = (o1 instanceof String) ? Integer.parseInt((String) o1) : (int) o1;

                i2 = (o2 instanceof String) ? Integer.parseInt((String) o2) : (int) o2;

                return Integer.compare(i1, i2);

            }

        });

        A.showAsTreeRepr(2);

    }

}
