package labprog2.util.graph.exceptions;

import labprog2.util.graph.Node;

public class NodeNotPresentException extends GraphException {
    private Node node;

    public NodeNotPresentException(Node node) {
        this.node = node;
    }

    public Node getNodeNotPresent() {
        return node;
    }
}
