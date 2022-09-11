package arboles;

import java.util.List;

import nodos.Nodo;
import nodos.NodoDobleE;

public class ArbolBinarioE implements Arbol {

    private NodoDobleE mat, root;

    public ArbolBinarioE() {

        this.mat = new NodoDobleE();
        this.mat.setLi(this.mat);
        this.mat.setBi(0);
        this.mat.setLd(this.mat);
        this.mat.setBd(1);

    }

    public ArbolBinarioE(Object d) {

        this.root = new NodoDobleE(d);
        this.mat = new NodoDobleE();

        this.mat.setLi(root);
        this.mat.setBi(1);

        this.mat.setLd(this.mat);
        this.mat.setBd(1);

    }

    public NodoDobleE next(NodoDobleE x) {

        NodoDobleE next = x.getLd();

        if (x.getBd() == 1) {

            while (next.getBi() == 1) {

                next = next.getLi();

            }

        }

        return next;

    }

    public boolean isEmpty() {

        return getMat().getBi() == 0;
        
    }
    
    @Override
    public Nodo find(Object d) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Nodo getParent(Nodo child) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Object> getAncestors(Nodo arbol) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getHeight() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getMaxDegree() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int countLeafs() {
        // TODO Auto-generated method stub
        return 0;
    }

    public NodoDobleE getMat() {
        return mat;
    }

    public NodoDobleE getRoot() {
        return root;
    }

}
