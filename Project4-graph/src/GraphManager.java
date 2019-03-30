import java.util.Random;

public class GraphManager {
    private EdgeWeightedDigraph directedGraph;
    public GraphManager(int V) {
        directedGraph = new EdgeWeightedDigraph(V);
    }
    public void addDirectedEdge(DirectedEdge diEdge) {
        directedGraph.addEdge(diEdge);
    }
    public Iterable<DirectedEdge> minLatency(int v, int w) {
        DijkstraAllPairsSP DAP = new DijkstraAllPairsSP(directedGraph);
        return DAP.path(v, w);
    }
    public void printDiGraph() {
        System.out.println(directedGraph);
    }
    public boolean hasPath(int v, int w) {
        DijkstraAllPairsSP DAP = new DijkstraAllPairsSP(directedGraph);
        return DAP.hasPath(v, w);
    }
    public boolean copperOnly() {
        DepthFirstSearch search = new DepthFirstSearch(directedGraph, 0);
        if (search.count() < directedGraph.V()) {
            return false;
        } else {
            return true;
        }
    }
    public boolean failTwoTest() {
        int V = directedGraph.V();
        int source = 0;
        for (int i = 0; i < V - 1; i++) {
            for (int j = i + 1; j < V; j++) {
                while (source == i || source == j) {
                    Random rand = new Random();
                    source = rand.nextInt(V);
                }
                DepthFirstSearch dfs = new DepthFirstSearch(directedGraph, source, i, j);
                if (dfs.failTwoCount() < (V - 2)) {
                    return false;
                }
            }
        }
        return true;
    }
    public Iterable<DirectedEdge> MST(){
        KruskalMST mst = new KruskalMST(directedGraph);
        return mst.edges();
    }
    public void delTwoVertices(int first, int second) {
        directedGraph.delTwoVertices(first, second);
    }
    public Vertex[] getVertices() {
        return directedGraph.getVertices();
    }
}
