package matricesDispersas;

public interface MatrizDispersa {

    public int getNumFilas();

    public int getNumColumnas();

    public Object get(int i, int j);

    public void set(int i, int j, Object val);
    
    public MatrizDispersa sum(MatrizDispersa B);

    public MatrizDispersa multiply(MatrizDispersa B);

}
