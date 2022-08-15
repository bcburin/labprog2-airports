package labprog2.util.graph.exceptions;

import labprog2.util.graph.Node;

public class EdgeNotPresentException extends GraphException {
    private Node srcNode;
    private Node desNode;

    public EdgeNotPresentException(Node srcNode, Node desNode) {
        this.srcNode = srcNode;
        this.desNode = desNode;
    }

    public Node getSrcNode() {
        return srcNode;
    }

    public Node getDesNode() {
        return desNode;
    }
}
