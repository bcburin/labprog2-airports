package labprog2.util.graph.exceptions;

import labprog2.util.graph.Node;

/**
 * Exception thrown when a node is not present in the graph.
 */
public class NodeNotPresentException extends GraphException {
    private final Node node;

    /**
     *
     * @param node node that was not present.
     */
    public NodeNotPresentException(Node node) {
        this.node = node;
    }

    /**
     *
     * @return node that was not present.
     */
    public Node getNodeNotPresent() {
        return node;
    }
}
