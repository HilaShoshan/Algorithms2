import java.util.Arrays;

public class BottlesProblem {

    static int infinity = Integer.MAX_VALUE;

    private int m, n;  // m = the volume (capacity) of bottle A, n = volume of bottle B
    private int dim;
    private boolean[][] bool_mat;
    private int[][] mat;

    BottlesProblem(int n, int m) {
        this.n = n;
        this.m = m;
        this.dim = (m+1)*(n+1);  // matrix dimension
        this.bool_mat = new boolean[dim][dim];
        this.mat = new int[dim][dim];
        fill_bool_mat();
        fill_mat();
    }

    private int index(int i, int j) {
        int index = (n + 1) * i + j;
        return index;
    }

    private void fill_bool_mat() {
        Arrays.fill(bool_mat, false);
        for(int i = 0; i <= m; i++) {
            for(int j = 0; j <= n; j++) {
                int ij = index(i, j);
                bool_mat[ij][index(0, j)] = true;  // (i, j)  --> (0, j)
                bool_mat[ij][index(i, 0)] = true;  // (i, j)  --> (i, 0)
                bool_mat[ij][index(m, j)] = true;  // (i, j) -->  (m, j)
                bool_mat[ij][index(i, n)] = true;  // (i, j) -->  (i, n)
                int second = index(Math.max(0, i+j-n),Math.min(n, i+j));
                bool_mat[ij][second] = true;  // (i, j) --> (max(0, i + j - n), min(n, i + j))
                second = index(Math.min(m, i+j),Math.max(0, j+i-m));
                bool_mat[ij][second] = true;  // (i, j) --> (min(m, i + j), max(0, i + j - m))
            }
        }
    }

    private void fill_mat() {
        Arrays.fill(mat, infinity);
        for(int i = 0; i <= m; i++) {
            for(int j = 0; j <= n; j++) {
                int ij = index(i, j);
                mat[ij][index(0, j)] = 1;  // (i, j)  --> (0, j)
                mat[ij][index(i, 0)] = 1;  // (i, j)  --> (i, 0)
                mat[ij][index(m, j)] = 1;  // (i, j) -->  (m, j)
                mat[ij][index(i, n)] = 1;  // (i, j) -->  (i, n)
                int second = index(Math.max(0, i+j-n),Math.min(n, i+j));
                mat[ij][second] = 1;  // (i, j) --> (max(0, i + j - n), min(n, i + j))
                second = index(Math.min(m, i+j),Math.max(0, j+i-m));
                mat[ij][second] = 1;  // (i, j) --> (min(m, i + j), max(0, i + j - m))
            }
        }
    }

    public boolean[][] getBool_mat() {return this.bool_mat;}

    public boolean canGetFrom(int i1, int j1, int i2, int j2) {
        int index1 = index(i1, j1);
        int index2 = index(i2, j2);
        return bool_mat[index1][index2];
    }

    //Constructing the all-pairs shortest-paths for modes
    public String[][] FWWeightForBottle() {
        String[][] pathMat = new String[dim][dim];
        for (int i = 0; i < dim; i++) {
            int ai = i/(n+1),  bi = i%(n+1);
            for (int j = 0; j < dim; j++) {
                int aj = j/(n+1),  bj = j%(n+1);
                if (bool_mat[i][j]) pathMat[i][j] = "(" + ai +","+ bi + ")->(" + aj +","+ bj + ") ";
                else pathMat[i][j] = "";
            }
        }
        // shortest path matrix building
        for (int k = 0; k < dim; k++)
            for (int i = 0; i < dim; i++)
                for (int j = 0; j < dim; j++)
                    if (bool_mat[i][k] && bool_mat[k][j])
                        if (mat[i][j] > mat[i][k]+mat[k][j]) {
                            mat[i][j] = mat[i][k]+mat[k][j];
                            pathMat[i][j] = pathMat[i][k] + pathMat[k][j];
                        }
        return pathMat;
    }
}
