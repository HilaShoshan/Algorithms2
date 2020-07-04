import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class BFS {

    private int size;  //number of vertexes
    private Queue<Integer> q;
    private int dist[], pred[], color[], partition[];
    private final int WHITE=1, GRAY=2,  BLACK=3, NIL = -1;
    private ArrayList<Integer>[] graph;
    private int numComps, source;
    private int components[];

    /**
     * constructor
     * @param g adjacency-list representation of graph (array of arrayLists)
     */
    public BFS(ArrayList<Integer> g[]) {
        size = g.length;
        q = new ArrayBlockingQueue<Integer>(size);
        dist = new int[size];
        pred = new int[size];
        color = new int[size];
        partition = new int[size];
        graph = new ArrayList[size];
        components = new int[size];
        for (int i = 0; i < size; i++)
            graph[i] = new ArrayList<Integer>(g[i]);
        source = 0;
        numComps = 0;
    }

    /**
     * BFS (Breadth-first search) algorithm
     * @param s = source node to start with
     */
    public void bfs_algo(int s){
        for (int i = 0; i < size; i++) {  // init all vertices data
            dist[i] = NIL;
            pred[i] = NIL;
            color[i] = WHITE;
        }
        source = s;
        dist[source] = 0;
        color[source] = GRAY;
        q.add(source);
        while(!q.isEmpty()){
            int u = q.poll();  // retrieves and removes the head of this queue, or returns null if this queue is empty
            for(int v : graph[u]) {  // all the neighbors of u
                if (color[v] == WHITE) {  // we haven't reach it yet
                    dist[v] = dist[u] + 1;
                    pred[v] = u;
                    color[v] = GRAY;
                    q.add(v);
                }
            }
            color[u] = BLACK;
        }
    }

    /**
     * The following procedure returns out the vertices on a shortest path from s to v
     * @param s source node
     * @param v destination node
     * @return a shortest path from s to v
     */
    public String getPath(int s, int v){
        bfs_algo(s);
        if (dist[v] == NIL) return null;  // there is no path from s to v
        String path = "";
        if (v==s) path = path + s;
        else {
            path = path + v;
            int t = pred[v];
            while (t != NIL){
                path = t + "->" + path;
                t = pred[t];
            }
        }
        return path;
    }

    /**
     * Check if the graph is connected or no (using bfs_algo)
     * @return true if the graph is connected,
     * otherwise return false
     */
    public boolean isConnected(){
        bfs_algo(0);
        for (int i = 0; i < size; i++)
            if (dist[i] == NIL) return false;
        return true;
    }

    /**
     * Calculate a number of the Graph Connected Components
     */
    private void connectedComponents() {
        while (hasNextComponent()) {
            numComps++;
            bfs_algo(source);
            for (int i = 0; i < components.length; i++) {
                if (dist[i] != NIL) components[i] = numComps;
            }
        }
    }

    /**
     * @return true if the graph has more WHITE vertices (more component/s)
     * otherwise return false
     */
    private boolean hasNextComponent() {
        for (int i = 0; i < components.length; i++)
            if (components[i] == 0) {
                source = i;
                return true;
            }
        return false;
    }

    /**
     *
     * @return All Graph Connected Components
     */
    public String getAllComponents() {
        connectedComponents();
        ArrayList<Integer>[] compsList = new ArrayList[numComps];
        String ans = "";
        for (int i = 0; i < numComps; i++) {
            compsList[i] = new ArrayList<Integer>();
            int n = components[i];
            compsList[n-1].add(i);
            ans = ans + compsList[i] + "\n";
        }
        return ans;
    }

    /**
     * Check if the graph is bipartite or no
     * @return true if the graph is bipartite,
     * otherwise return false
     */
    public boolean isBipartite() {
        int partitions[] = new int[size];
        int s = 0;  //source vertex
        dist[s] = 0;
        color[s] = GRAY;
        partitions[s] = 1;
        q = new ArrayBlockingQueue<Integer>(size);
        q.add(s);
        while(!q.isEmpty()){
            int u = q.poll();//Retrieves and removes the head of this queue, or returns null if this queue is empty
            for(int v : graph[u]){
                if (partition[u] == partition[v])
                    return false;
                if (color[v] == WHITE) {
                    dist[v] = dist[u] + 1;
                    pred[v] = u;
                    color[v] = GRAY;
                    partition[v] = 3 - partition[u];
                    q.add(v);
                }
            }
            color[u] = BLACK;
        }
        return true;
    }

    /**
     * Calculate a graph diameter
     * @param graph = graph given as an array of arrayLists
     * @param s source or starting node
     * @return a graph diameter
     */
    static int diameter(ArrayList<Integer>[] graph, int s) {
        BFS bf1 = new BFS(graph);
        bf1.bfs_algo(s);
        int maxIndex = max_ind(bf1.dist);  // the vertex furthest from vertex s
        BFS bf2 = new BFS(graph);
        bf2.bfs_algo(maxIndex);
        return max_ind(bf2.dist);
    }

    /**
     * Find index of maximum element in an array
     * @param a
     * @return index of maximum element
     */
    private static int max_ind(int[] a) {
        int ind = 0;
        for (int i = 1; i < a.length; i++)
            if (a[i] > a[ind]) ind = i;
        return ind;
    }
}
