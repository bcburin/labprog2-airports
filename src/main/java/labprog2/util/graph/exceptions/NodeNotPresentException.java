package labprog2.util.graph.exceptions;

/**
 * Exception thrown when a node is not present in the graph.
 */
public class NodeNotPresentException extends GraphException {
    private final Object node;

    /**
     *
     * @param node node that was not present.
     */
    public NodeNotPresentException(Object node) {
        this.node = node;
    }

    /**
     *
     * @return node that was not present.
     */
    public Object getNodeNotPresent() {
        return node;
    }
}
