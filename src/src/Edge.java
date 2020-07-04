public class Edge implements Comparable<Edge> {
    public int src, dest, weight;

    public Edge(int s, int d, int w) {
        this.src = s;
        this.dest = d;
        this.weight = w;
    }

    public String toString() {
        return "{" + src + ", " + dest + "}" + " weight: " + weight;
    }

    public int compareTo(Edge v) {
        int ans = 0;
        if (this.weight - v.weight > 0) ans = 1;
        else if (this.weight - v.weight < 0) ans = -1;
        return ans;
    }
}

