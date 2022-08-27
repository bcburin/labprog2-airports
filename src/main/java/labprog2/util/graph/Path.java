package labprog2.util.graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements a path as a list of nodes.
 */
public class Path {
    private final List<Node> path;

    /**
     * Initializes an empty path.
     */
    public Path() {
        this.path = new LinkedList<>();
    }

    /**
     * Adds a node to the path.
     *
     * @param node node to be added.
     * @return reference to current path.
     */
    public Path addNode(Node node) {
        path.add(node);
        return this;
    }

    /**
     * Reverses the path.
     *
     * @return reversed path.
     */
    public Path reverse() {
        Collections.reverse(path);
        return this;
    }

    /**
     * Returns nodes of the path.
     *
     * @return List containing nodes of the path.
     */
    public List<Node> getNodes() {
        return path;
    }

}
