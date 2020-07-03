// given a graph as a matrix represents the neighbors and the weights between vertices
// We will run algorithms to get information about the graph

import java.util.Arrays;

public class Graph_Info {

    private int[][] mat;
    private int n;
    static int infinity = Integer.MAX_VALUE;

    /**
     * graph with weights on the edges
     * @param mat = matrix that represents the weights on each edge {i,j}
     */
    Graph_Info(int[][] mat) {
        this.mat = mat;
        this.n = mat.length;
    }

    /**
     * graph with weights on the vertices
     * @param vertexWeight = array of the weight of each vertex
     * @param mb = boolean matrix that represent where there is edges
     *           mb[i]]j] = true if edge exists between vertices i, j
     */
    Graph_Info(int[] vertexWeight, boolean mb[][]) {
        this.n = mb.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (mb[i][j] && i != j)
                    this.mat[i][j] = vertexWeight[i] + vertexWeight[j];
                else this.mat[i][j] = infinity;
            }
        }
    }

    Graph_Info(int[] vertexWeight, int[][] edgeWeight) {
        this.n = edgeWeight.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (edgeWeight[i][j] != infinity && i!=j)
                    this.mat[i][j] = vertexWeight[i] + vertexWeight[j] + 2*edgeWeight[i][j];
                else this.mat[i][j] = infinity;
            }
        }
    }

    /**
     * Using floyd-warshall algo to find all pairs shortest path between vertices on the graph
     * @return nXn matrix that represents all shortest path from i to j
     */
    public int[][] floydWarshall() {
        int[][] ans = mat.clone();
        for(int k = 0; k < n; k++)
            for(int i = 0; i < n; i++)
                for(int j = 0; j < n; j++)
                    if (mat[i][k] != infinity && mat[k][j] != infinity) {
                        ans[i][j] = Math.min(mat[i][j], mat[i][k] + mat[k][j]);
                    }
        return ans;
    }

    /**
     * @return String matrix of the shortest paths
     */
    public String[][] buildPathMatrix(){
        String [][]pathMat = new String[n][n];
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                if (mat[i][j] != infinity)
                    pathMat[i][j] = " "+i+"->"+j+" ";
                else pathMat[i][j] = "";
            }
        }
        // path matrix building
        for (int k = 0; k<n; k++)
            for (int i = 0; i<n; i++)
                for (int j = 0; j<n; j++)
                    if (mat[i][k]!=infinity && mat[k][j]!=infinity)
                        if (mat[i][j] > mat[i][k]+mat[k][j]){
                            mat[i][j] = mat[i][k]+mat[k][j];
                            pathMat[i][j] = pathMat[i][k]+pathMat[k][j];
                        }
        return pathMat;
    }

    public int connectComponents_undirected() {
        int connectComp[] = new int[n];
        int numComponentes = 0;
        for(int i = 0; i < n; i++) {
            if(connectComp[i] == 0){
                numComponentes++;
                connectComp[i] = numComponentes;
            }
            for(int j = i+1; j < n; j++) {
                // vertex j is not defined yet - the path exists
                if (connectComp[j] == 0 && mat[i][j] != infinity)
                    connectComp[j] = numComponentes;
            }
        }
        String vs[] = new String[numComponentes];
        for(int i=0; i<numComponentes; i++)
            vs[i] = new String();
        for(int i=0; i<n; i++){
            int index = connectComp[i] - 1;
            vs[index] = vs[index] + i + ",";
        }
        System.out.println(Arrays.toString(connectComp));
        for (int i=0; i<numComponentes; i++)
            System.out.println("component number " + i + ", vertices: [" + vs[i].substring(0, vs[i].length()-1) + "]");
        return numComponentes;
    }

    /**
     * fix the FW answer to a matrix with weights on vertices
     * @param fw = FW solution (after converting to edges' weights graph)
     * @param vertexWeight = the vertices weights array
     * @return the true answer after fixing
     */
    public int[][] fix_weights(int[][] fw, int[] vertexWeight) {
        for(int i = 0; i < fw.length; i++) {
            for(int j = 0; j < fw[0].length; j++) {
                fw[i][j] = (fw[i][j] + vertexWeight[i] + vertexWeight[j]) / 2;
            }
        }
        return fw;
    }

    public boolean hasNegativeCircle() {
        int[][] FW_sol = floydWarshall();
        for (int i = 0; i < FW_sol.length; i++){
            if (FW_sol[i][i] < 0)  // there is negative weight between vertex to itself
                return true;
        }
        return false;
    }
}
