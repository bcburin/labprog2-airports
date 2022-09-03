package labprog2.util.graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Implements a path as a list of nodes with a cost associated to them.
 */
public class Path {
    private final List<Node> path;
    private int cost;

    /**
     * Initializes an empty path.
     */
    public Path() {
        this.path = new LinkedList<>();
        this.cost = 0;
    }

    /**
     * Adds a node to the path.
     *
     * @param node node to be added.
     */
    public void addNode(Node node) {
        this.path.add(node);
    }

    /**
     * Sets the cost to execute the path.
     *
     * @param cost cost of the path.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     *
     * @return cost of the path
     */
    public int getCost() {
        return this.cost;
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
