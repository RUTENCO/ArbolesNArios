package arboles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import nodos.Nodo;

public class ArbolLL extends Nodo implements Arbol {

    private List<ArbolLL> children;

    public ArbolLL() {

        super();

        this.children = new ArrayList<ArbolLL>();

    }

    public ArbolLL(Object dato) {

        super(dato);

        this.children = new ArrayList<ArbolLL>();

    }

    public boolean addChild(ArbolLL child) {

        return this.children.add(child);

    }

    public ArbolLL getChild(int index) {

        return this.children.get(index);

    }

    public ArbolLL removeChild(int index) {

        return this.children.remove(index);

    }

    public boolean removeChild(ArbolLL child) {

        return this.children.remove(child);

    }

    public static ArbolLL consArbolLL(String arbolStr, AtomicInteger globalIdx) {

        ArbolLL A, subArbol;

        A = null;

        int charIdx = globalIdx.get();
        String atomo = "";

        // xxxxx.
        // a(b(c, d(e)), f, g(h, i(j, k(l)), m, n))

        // a(b, c)
        // a(b(c))
        // a(b(c), d)
        // a(b, c(d))
        while (charIdx < arbolStr.length()) {

            switch (arbolStr.charAt(charIdx)) {

                case '(':

                    A = new ArbolLL(atomo.trim());

                    globalIdx.set(charIdx + 1);
                    subArbol = consArbolLL(arbolStr, globalIdx);
                    charIdx = globalIdx.get();

                    A.addChild(subArbol);

                    atomo = "";
                    break;

                case ',':

                    if (A != null) {

                        globalIdx.set(charIdx + 1);
                        subArbol = consArbolLL(arbolStr, globalIdx);
                        charIdx = globalIdx.get();

                        A.addChild(subArbol);

                    }

                    if (atomo.trim().length() > 0) {

                        A = new ArbolLL(atomo.trim());
                        globalIdx.set(charIdx - 1);
                        return A;

                    }

                    break;

                case ')':

                    if (atomo.trim().length() > 0) {

                        A = new ArbolLL(atomo.trim());
                        globalIdx.set(charIdx - 1);

                    } else {

                        globalIdx.set(charIdx);

                    }

                    return A;

                default:

                    atomo = atomo.concat(arbolStr.substring(charIdx, charIdx + 1));
                    break;

            }

            charIdx++;

        }

        return A;

    }

    @Override
    public ArbolLL find(Object d) {

        if (getDato().equals(d)) {
            return this;
        }

        ArbolLL subResult;

        for (ArbolLL arbolLL : children) {

            subResult = arbolLL.find(d);
            if (subResult != null) {
                return subResult;
            }

        }

        return null;

    }

    @Override
    public ArbolLL getParent(Nodo child) {

        if (children.contains(child)) {
            return this;
        }

        ArbolLL subResult;

        for (ArbolLL childX : children) {

            subResult = childX.getParent(child);
            if (subResult != null) {
                return subResult;
            }

        }

        return null;

    }

    @Override
    public List<Object> getAncestors(Nodo arbol) {

        if (arbol == null) {
            return null;
        }

        List<Object> ancestors, subAncestors;

        ancestors = new ArrayList<Object>();

        if (arbol == this) {

            ancestors.add(this.getDato());
            return ancestors;

        }

        for (ArbolLL child : children) {

            subAncestors = child.getAncestors(arbol);
            if (subAncestors.size() > 0 && subAncestors.get(subAncestors.size() - 1) == arbol.getDato()) {

                subAncestors.add(0, this.getDato());
                return subAncestors;

            }

        }

        return ancestors;

    }

    @Override
    public int getHeight() {

        int maxHeight, childHeight;

        maxHeight = 1;

        for (ArbolLL child : children) {

            childHeight = child.getHeight();
            maxHeight = Math.max(maxHeight, childHeight + 1);

        }

        return maxHeight;

    }

    @Override
    public int getMaxDegree() {

        int maxDegree, childDegree;

        maxDegree = children.size();

        for (ArbolLL child : children) {

            childDegree = child.getMaxDegree();
            maxDegree = Math.max(maxDegree, childDegree);

        }

        return maxDegree;

    }

    @Override
    public int countLeafs() {

        int leafs, childLeafs;

        leafs = (children.isEmpty()) ? 1 : 0;

        for (ArbolLL child : children) {

            childLeafs = child.countLeafs();
            leafs += childLeafs;

        }

        return leafs;

    }

    public void consTreeRepr(StringBuilder sb, String prefix, int widthFix) {

        sb.append(getDato().toString());
        sb.append("\n");

        String subPrefix;
        boolean lastChild;

        for (ArbolLL child : children) {

            lastChild = children.indexOf(child) == children.size() - 1;

            subPrefix = prefix.concat(lastChild ? "└" : "├");
            subPrefix = subPrefix.concat("─".repeat(widthFix));

            sb.append(subPrefix);

            subPrefix = prefix.concat(lastChild ? " " : "│").concat(" ".repeat(widthFix));

            child.consTreeRepr(sb, subPrefix, widthFix);

        }

    }

    @Override
    public String toString() {

        StringBuilder root, children;

        root = new StringBuilder(getDato().toString());

        if (this.children.isEmpty()) {

            return root.toString();

        }

        children = new StringBuilder("");

        for (ArbolLL child : this.children) {

            children.append(",").append(child.toString());

        }

        children.replace(0, 1, "(").append(")");

        return root.append(children.toString()).toString();

    }

    public void show() {

        System.out.println(toString());

    }

    public void showAsTreeRepr(int widthFix) {

        StringBuilder sb = new StringBuilder();

        consTreeRepr(sb, "", widthFix);

        System.out.println(sb.toString());

    }

    public static void main(String[] args) {

        ArbolLL A = consArbolLL("a(b(c, d(e)), f, g(h, i(j, k(l(x(z, o(p))))), m, n))", new AtomicInteger(0));

        A.showAsTreeRepr(2);

        System.out.println();

        ArbolLL B = A.find("p");
        B.showAsTreeRepr(2);

        System.out.println(B.countLeafs());
        System.out.println(A.getParent(B));
        System.out.println(A.getAncestors(B));

    }

}
