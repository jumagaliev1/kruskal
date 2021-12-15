package project;
import java.util.*;
import java.lang.*;

class Solutions{
    public static class Edge implements Comparable<Edge> {
        int src, dest, weight;
        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    };
    static class subset {
        int parent, rank;
    };

    int V, E; // V-> no. of vertices & E->no.of edges
    Edge edge[]; // collection of all edges

    Solutions(int v, int e) {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }

    int find(subset[] subsets, int i)
    {
        if (subsets[i].parent != i)
            subsets[i].parent
                    = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }

    void Union(subset[] subsets, int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank
                < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank
                > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    void KruskalMST()
    {
        // Tnis will store the resultant MST
        Edge[] result = new Edge[V];

        // An index variable, used for result[]
        int e = 0;

        // An index variable, used for sorted edges
        int i = 0;
        for (i = 0; i < V; ++i)
            result[i] = new Edge();
        Arrays.sort(edge);

        // Allocate memory for creating V subsets
        subset[] subsets = new subset[V];
        for (i = 0; i < V; ++i)
            subsets[i] = new subset();

        // Create V subsets with single elements
        for (int v = 0; v < V; ++v)
        {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0; // Index used to pick next edge

        // Number of edges to be taken is equal to V-1
        while (e < V - 1)
        {
            // Step 2: Pick the smallest edge. And increment
            // the index for next iteration
            Edge next_edge = edge[i++];

            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
            // Else discard the next_edge
        }

        int minimumCost = 0;
        for (i = 0; i < e; ++i)
        {
            minimumCost += result[i].weight;
        }
        System.out.println(minimumCost);
    }

    public static void main(String[] args)
    {

        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt(); // Number of vertices in graph
        int E = sc.nextInt(); // Number of edges in graph
        Solutions graph = new Solutions(V, E);
        for(int i = 0; i < E; i++){
            graph.edge[i].src = sc.nextInt();
            graph.edge[i].dest = sc.nextInt();
            graph.edge[i].weight = sc.nextInt();
        }
        graph.KruskalMST();
    }
}
