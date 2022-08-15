package labprog2.util.graph;

public class Edge {
    private Node srcNode;
    private Node desNode;
    private int weight;

    public Edge(Node srcNode, Node desNode, int weight) {
        this.srcNode = srcNode;
        this.desNode = desNode;
        this.weight = weight;
    }

    public Edge(Node srcNode, Node desNode) {
        this.srcNode = srcNode;
        this.desNode = desNode;
        this.weight = 1;
    }

    public Edge(Edge other) {
        srcNode = other.getSrcNode();
        desNode = other.getDesNode();
        weight = other.getWeight();
    }

    public int getWeight() {
        return weight;
    }

    public Node getSrcNode() {
        return srcNode;
    }

    public Node getDesNode() {
        return desNode;
    }

    public Edge setWeight(int weight) {
        this.weight = weight;
        return this;
    }
}
