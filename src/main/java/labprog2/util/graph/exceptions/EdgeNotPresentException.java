package labprog2.util.graph.exceptions;

/**
 * Exception to be thrown when a required edge is not in the graph.
 */
public class EdgeNotPresentException extends GraphException {
    private final Object srcNode;
    private final Object desNode;

    /**
     *
     * @param srcNode source node of the edge that caused the exception.
     * @param desNode destiny node of the edge that caused the exception.
     */
    public EdgeNotPresentException(Object srcNode, Object desNode) {
        this.srcNode = srcNode;
        this.desNode = desNode;
    }

    /**
     *
     * @return source node of the edge that caused the exception.
     */
    public Object getSrcNode() {
        return srcNode;
    }

    /**
     *
     * @return destiny node of the edge that caused the exception.
     */
    public Object getDesNode() {
        return desNode;
    }
}
