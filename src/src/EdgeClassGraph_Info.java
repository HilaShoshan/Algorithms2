import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class EdgeClassGraph_Info {

    private ArrayList<Edge> g = new ArrayList<>();
    private int num_vertices = 0;

    EdgeClassGraph_Info(ArrayList<Edge> g, int n) {
        this.g = g;
        this.num_vertices = n;  // the names of the vertices are {0, ... n}
    }

    /**
     * Reverse Kruskal to find the MST (Minimum spanning tree) of the undirected graph
     * @return the MST as the g parameter (array of arrayLists)
     */
    public ArrayList<Edge> reverseKruskal() {
        PriorityQueue<Edge> q = new PriorityQueue<>(g.size(), Collections.reverseOrder());
        for(int i = 0; i < g.size(); i++)  // added all the edges to the queue
            q.add(g.get(i));
        while (g.size() > num_vertices - 1 && !q.isEmpty()) {
            Edge r = q.poll();
            g.remove(r);  // should remove also the second direction ??
            System.out.println("poll : " + r);
            print_g();
            ArrayList<Integer>[] l = convertToList();
//            BFS bfs = new BFS(l);
            BFS_Anna bfs = new BFS_Anna(l);
            if (!bfs.isConnected())  // The deletion of the edge made the graph unconnected
                g.add(r);  // return the edge to the graph!
                System.out.println("not connected! returns " + r);
        }
        return g;
    }

    public ArrayList<Integer>[] convertToList() {
        ArrayList<Integer>[] ans = new ArrayList[num_vertices];
        for (int i = 0; i < g.size(); i++) {  // init loop
            Edge e = g.get(i);
            ans[e.src] = new ArrayList<>();
            ans[e.dest] = new ArrayList<>();  // because its undirected (the edge added once)
        }
        for (int i = 0; i < g.size(); i++) {
            Edge e = g.get(i);
            ans[e.src].add(e.dest);
            ans[e.dest].add(e.src);
        }
        for(int i = 0; i < ans.length; i++) {
            System.out.print("[" + i + "] ");
            for(int j = 0; j < ans[i].size(); j++) {
                System.out.print(ans[i].get(j) + ",");
            }
            System.out.println();
        }
        return ans;
    }

    public void print_g() {
        for(Edge e : g)
            System.out.println(e);
    }
}