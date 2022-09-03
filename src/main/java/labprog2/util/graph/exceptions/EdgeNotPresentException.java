package labprog2.util.graph.exceptions;

import labprog2.util.graph.Node;

/**
 * Exception to be thrown when a required edge is not in the graph.
 */
public class EdgeNotPresentException extends GraphException {
    private final Node srcNode;
    private final Node desNode;

    /**
     *
     * @param srcNode source node of the edge that caused the exception.
     * @param desNode destiny node of the edge that caused the exception.
     */
    public EdgeNotPresentException(Node srcNode, Node desNode) {
        this.srcNode = srcNode;
        this.desNode = desNode;
    }

    /**
     *
     * @return source node of the edge that caused the exception.
     */
    public Node getSrcNode() {
        return srcNode;
    }

    /**
     *
     * @return destiny node of the edge that caused the exception.
     */
    public Node getDesNode() {
        return desNode;
    }
}
