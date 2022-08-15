package labprog2.util.graph.exceptions;

public class FullGraphException extends GraphException {
    private int maxNodes;

    public FullGraphException(int maxNodes) {
        this.maxNodes = maxNodes;
    }

    public int getMaxNodes() {
        return maxNodes;
    }
}
