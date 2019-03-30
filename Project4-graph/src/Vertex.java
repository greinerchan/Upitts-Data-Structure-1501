import java.util.LinkedList;

public class Vertex {
    private int v;
    private LinkedList<DirectedEdge> adjacentList;
    public Vertex(int v) {
        this.v = v;
        adjacentList = new LinkedList<DirectedEdge>();
    }
    public LinkedList<DirectedEdge> getList() {
        return adjacentList;
    }
    public int getVertex() {
        return v;
    }
}
