package labprog2.util.graph.exceptions;


/**
 * Exception thrown when a node is already present in the graph and thus the action could not be completed.
 */
public class NodeAlreadyPresentException extends GraphException {
    private final Object node;

    /**
     *
     * @param node node that was already present
     */
    public NodeAlreadyPresentException(Object node) {
        this.node = node;
    }

    /**
     *
     * @return node that was already present
     */
    public Object getNodeAlreadyPresent() {
        return node;
    }
}
