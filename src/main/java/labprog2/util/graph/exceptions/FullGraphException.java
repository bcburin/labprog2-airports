package labprog2.util.graph.exceptions;


/**
 * Exception to be thrown when a graph is full and thus the required action could not be completed.
 */
public class FullGraphException extends GraphException {
    private final int maxNodes;

    /**
     *
     * @param maxNodes maximum number of nodes of the graph that generated the exception.
     */
    public FullGraphException(int maxNodes) {
        this.maxNodes = maxNodes;
    }

    /**
     *
     * @return maximum number of nodes of the graph that generated the exception.
     */
    public int getMaxNodes() {
        return maxNodes;
    }
}
