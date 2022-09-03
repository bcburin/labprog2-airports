package labprog2.util.graph.exceptions;

import labprog2.util.graph.Node;

/**
 * Exception thrown when a node is already present in the graph and thus the action could not be completed.
 */
public class NodeAlreadyPresentException extends GraphException {
    private final Node node;

    /**
     *
     * @param node node that was already present
     */
    public NodeAlreadyPresentException(Node node) {
        this.node = node;
    }

    /**
     *
     * @return node that was already present
     */
    public Node getNodeAlreadyPresent() {
        return node;
    }
}
