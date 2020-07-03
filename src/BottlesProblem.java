public class BottlesProblem {

    private int m, n;  // m = the volume (capacity) of bottle A, n = volume of bottle B
    int dim;
    private boolean [][]mat;

    BottlesProblem(int n, int m) {
        this.n = n;
        this.m = m;
        this.dim = (m+1)*(n+1);  // matrix dimension
        this.mat = new boolean[dim][dim];
        fill_mat();
    }

    private int index(int i, int j) {
        int index = (n + 1) * i + j;
        return index;
    }

    private void fill_mat() {
        for(int i=0; i<dim; i++)
            for(int j=0; j<dim; j++)
                mat[i][j]= false;
        for(int i = 0; i <= m; i++) {
            for(int j = 0; j <= n; j++) {
                int ij = index(i, j);
                mat[ij][index(0, j)] = true;  // (i, j)  --> (0, j)
                mat[ij][index(i, 0)] = true;  // (i, j)  --> (i, 0)
                mat[ij][index(m, j)] = true;  // (i, j) -->  (m, j)
                mat[ij][index(i, n)] = true;  // (i, j) -->  (i, n)
                int second = index(Math.max(0, i+j-n),Math.min(n, i+j));
                mat[ij][second] = true;  // (i, j) --> (max(0, i + j - n), min(n, i + j))
                second = index(Math.min(m, i+j),Math.max(0, j+i-m));
                mat[ij][second] = true;  // (i, j) --> (min(m, i + j), max(0, i + j - m))
            }
        }
    }

    public boolean[][] getMat() {return this.mat;}

    public boolean canGetFrom(int i1, int j1, int i2, int j2) {
        int index1 = index(i1, j1);
        int index2 = index(i2, j2);
        return mat[index1][index2];
    }
}
