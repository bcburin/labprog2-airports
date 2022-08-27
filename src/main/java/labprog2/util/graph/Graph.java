package labprog2.util.graph;

import labprog2.util.graph.exceptions.EdgeNotPresentException;
import labprog2.util.graph.exceptions.FullGraphException;
import labprog2.util.graph.exceptions.NodeAlreadyPresentException;
import labprog2.util.graph.exceptions.NodeNotPresentException;

import java.util.List;

/**
 * Interface that specifies a directional weighted graph.
 */
public interface Graph {
    /**
     * Adds a node to the graph, given it is not already present nor the graph is full.
     *
     * @param node object implementing the node interface to be added to the graph.
     * @return returns a reference to the current graph, in order to allow chaining.
     * @throws FullGraphException thrown if the graph is already full, as defined by its implementation.
     * @throws NodeAlreadyPresentException thrown if the node being added is already present
     *
     * @see Node
     */
    public Graph addNode(Node node) throws FullGraphException, NodeAlreadyPresentException;

    /**
     * Adds an edge to the graph. Both ends must be nodes already present. If the edge is already present,
     * it is substituted.
     *
     * @param edge Edge to be added or updated.
     * @return returns a reference to the current graph, in order to allow chaining.
     * @throws NodeNotPresentException thrown if either of the edge endpoints is not present in the graph.
     *
     * @see Edge
     */
    public Graph addEdge(Edge edge) throws NodeNotPresentException;

    /**
     * Removes the directional edge between the specified nodes, if it exists.
     *
     * @param srcNode source node of the edge to be removed.
     * @param desNode destiny node of the edge to be removed.
     * @return returns the edge that was removed.
     * @throws EdgeNotPresentException thrown if there is no edge between the specified nodes
     * @throws NodeNotPresentException thrown if either of the specified nodes is not present
     */
    public Edge removeEdgeBetween(Node srcNode, Node desNode) throws EdgeNotPresentException, NodeNotPresentException;

    /**
     * Retrieves the edge between two nodes in the graph, if it exists.
     *
     * @param srcNode source node of the queried edge.
     * @param desNode destiny node of the queried edge.
     * @return returns the edge between the specified nodes.
     * @throws NodeNotPresentException thrown if either of the nodes is not present.
     * @throws EdgeNotPresentException thrown if there is no edge between the specified nodes.
     */
    public Edge getEdgeBetween(Node srcNode, Node desNode) throws NodeNotPresentException, EdgeNotPresentException;

    /**
     * Returns a list containing all the edges that come out of the specified node.
     *
     * @param srcNode source node of all the queried edges.
     * @return List containing all the edges.
     * @throws NodeNotPresentException thrown if the specified node is not present in the graph.
     */
    public List<Edge> getEdgesFrom(Node srcNode) throws NodeNotPresentException;

    /**
     * Returns the shortest path between two nodes within a graph.
     *
     * @param srcNode source node (starting node of the path).
     * @param desNode destiny node (final node of the path).
     * @return shortest path.
     * @throws NodeNotPresentException thrown if either of the specified nodes is not present.
     */
    public Path getShortestPath(Node srcNode, Node desNode) throws NodeNotPresentException;

    /**
     * Gets the shortest path with at least one intermediate node between two specified nodes.
     *
     * @param srcNode source node (starting node of the path).
     * @param desNode destiny node (final node of the path).
     * @return shortest path with at least one intermediate node.
     * @throws NodeNotPresentException thrown if either of the specified nodes is not present.
     */
    public Path getShortestNonDirectPath(Node srcNode, Node desNode)
            throws NodeNotPresentException;

    /**
     * Verifies whether there exists an edge between two nodes in the graph.
     *
     * @param srcNode source node.
     * @param desNode destiny node.
     * @return true if there exists an edge, false otherwise.
     * @throws NodeNotPresentException If either of the specified edges is not present in the graph.
     */
    public boolean connects(Node srcNode, Node desNode) throws NodeNotPresentException;

    /**
     * Verifies the presence of a node in the graph.
     *
     * @param node queried node.
     * @return true if the node is present in the graph, false otherwise.
     */
    public boolean has(Node node);

    /**
     * Verifies the presence of an edge in the graph.
     *
     * @param edge queried edge.
     * @return true if the edge is present, false otherwise.
     * @throws NodeNotPresentException if either of the provided edge endpoints (nodes) is not present in the graph.
     */
    public boolean has(Edge edge) throws NodeNotPresentException;

    /**
     * Verifies if graph is empty.
     *
     * @return true if there are no nodes, false otherwise.
     */
    public boolean isEmpty();

    /**
     * Returns the weight of the edge between two nodes of the graph.
     *
     * @param srcNode source node of the queried edge.
     * @param desNode destiny node of the queried edge.
     * @return weight of the edge with the specified endpoints.
     * @throws EdgeNotPresentException thrown if there is no edge between such nodes.
     * @throws NodeNotPresentException thrown if either of the nodes is not present in the graph.
     */
    public int getWeightBetween(Node srcNode, Node desNode) throws EdgeNotPresentException, NodeNotPresentException;
}
