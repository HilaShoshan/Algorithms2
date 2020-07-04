import java.util.ArrayList;
import java.util.Arrays;

public class main {
    static int infinity = Integer.MAX_VALUE;

    public static void printMatrix(int[][] mat){
        for(int i=0; i<mat.length; i++){
            for(int j=0; j<mat[0].length; j++){
                if (mat[i][j] == infinity) System.out.print("INF ");
                else System.out.print(mat[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void test_bottles(int m, int n) {
        BottlesProblem b = new BottlesProblem(m, n);
        boolean[][] bool_mat = b.getBool_mat();
        for(int i=0; i<bool_mat.length; i++) {
            for (int j = 0; j < bool_mat[0].length; j++) {
                System.out.print(bool_mat[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void test_reverseKruskal() {
        ArrayList<Edge> g = new ArrayList<>();
        g.add(new Edge(0, 3, 5));
        g.add(new Edge(0, 1, 7));
        g.add(new Edge(1, 3, 9));
        g.add(new Edge(1, 2, 8));
        g.add(new Edge(1, 4, 7));
        g.add(new Edge(2, 4, 5));
        g.add(new Edge(3, 4, 15));
        g.add(new Edge(3, 5, 6));
        g.add(new Edge(5, 4, 8));
        g.add(new Edge(5, 6, 11));
        g.add(new Edge(4, 6, 9));

        /*g.add(new Edge(3, 0, 5));
        g.add(new Edge(1, 0, 7));
        g.add(new Edge(3, 1, 9));
        g.add(new Edge(2, 1, 8));
        g.add(new Edge(4, 1, 7));
        g.add(new Edge(4, 2, 5));
        g.add(new Edge(4, 3, 15));
        g.add(new Edge(5, 3, 6));
        g.add(new Edge(4, 5, 8));
        g.add(new Edge(6, 5, 11));
        g.add(new Edge(6, 4, 9));*/

        EdgeClassGraph_Info graph = new EdgeClassGraph_Info(g, 7);
        graph.reverseKruskal();
//        graph.print_g();
    }

    public static void main(String[] args) {
        //test_bottles(1, 2);
        test_reverseKruskal();
        /*int[][] mat = {{0, 2, infinity, infinity, 18},
                {2, 0, 4, infinity, infinity},
                {infinity, 4, 0, 1, infinity},
                {infinity, infinity, 1, 0, 5},
                {18, infinity, infinity, 5, 0}};

        Graph_Info g = new Graph_Info(mat);
        int[][] shortestWeights = g.floydWarshall();
        printMatrix(shortestWeights);
        String[][] shortestPath = g.buildPathMatrix();
        for(int i = 0; i < shortestPath.length; i++) {
            for(int j = 0; j < shortestPath[0].length; j++) {
                System.out.print(shortestPath[i][j] +" ");
            }
            System.out.println();
        }

        int cc = g.connectComponents_undirected();*/
    }
}
