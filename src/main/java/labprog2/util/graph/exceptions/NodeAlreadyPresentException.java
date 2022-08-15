package labprog2.util.graph.exceptions;

import labprog2.util.graph.Node;

public class NodeAlreadyPresentException extends GraphException {
    private Node node;

    public NodeAlreadyPresentException(Node node) {
        this.node = node;
    }

    public Node getNodeAlreadyPresent() {
        return node;
    }
}
