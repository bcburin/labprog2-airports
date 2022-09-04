package labprog2.util.graph;

public class Edge<Node> {
    private final Node srcNode;
    private final Node desNode;
    private int weight;

    /**
     * Implements the edge of a graph.
     *
     * @param srcNode source node.
     * @param desNode destiny node.
     * @param weight weight of the edge.
     */
    public Edge(Node srcNode, Node desNode, int weight) {
        this.srcNode = srcNode;
        this.desNode = desNode;
        this.weight = weight;
    }

    /**
     * Implements the edge of a graph. Weight is defaulted to 1.
     *
     * @param srcNode source node.
     * @param desNode destiny node.
     */
    public Edge(Node srcNode, Node desNode) {
        this.srcNode = srcNode;
        this.desNode = desNode;
        this.weight = 1;
    }

    /**
     * Copies edge object.
     *
     * @param other original edge object.
     */
    public Edge(Edge<Node> other) {
        srcNode = other.getSrcNode();
        desNode = other.getDesNode();
        weight = other.getWeight();
    }

    /**
     * Weight getter.
     *
     * @return weight of the edge.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Source node getter.
     *
     * @return source node of the edge.
     */
    public Node getSrcNode() {
        return srcNode;
    }

    /**
     * Destiny node getter.
     *
     * @return destiny node of the edge.
     */
    public Node getDesNode() {
        return desNode;
    }

    /**
     * Setter of the weight.
     *
     * @param weight new weight.
     * @return reference to current edge.
     */
    public Edge<Node> setWeight(int weight) {
        this.weight = weight;
        return this;
    }
}
