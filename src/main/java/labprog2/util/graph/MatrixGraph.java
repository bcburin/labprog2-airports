package labprog2.util.graph;

import java.util.*;

import labprog2.util.graph.exceptions.EdgeNotPresentException;
import labprog2.util.graph.exceptions.FullGraphException;
import labprog2.util.graph.exceptions.NodeAlreadyPresentException;
import labprog2.util.graph.exceptions.NodeNotPresentException;

import org.javatuples.Pair;

public class MatrixGraph<Node> implements Graph<Node> {

    /**
     * Matrix representation of the graph that stores its topology. An entry at (i, j) equal to zero
     * indicates that there is no edge between node i and j. On the other hand, a non-zero entry specifies
     * the presence of an edge and its value represents its weight.
     */
    private final int[][] graph;

    /**
     * Stores the current number of nodes. Must be lesser than or equal to the maximum amount of nodes.
     */
    private int curNodes;

    /**
     * Stores the maximum amount of nodes, which is implemented as the order of the matrix that represents
     * the graph.
     */
    private final int maxNodes;

    /**
     * Dictionary that maps a node to an index of the matrix (integer between 0 and maxNodes-1, inclusive).
     */
    private final Map<Node, Integer> nodeIndexes;

    /**
     * Dictionary an index of the matrix (integer between 0 and maxNodes-1, inclusive) to a node.
     */
    private final Map<Integer, Node> indexNodes;

    /**
     * Implements a directional weighted graph as a matrix.
     *
     * @param maxNodes maximum number of allowed nodes (order of the matrix).
     */
    public MatrixGraph(int maxNodes) {
        this.graph = new int[maxNodes][maxNodes];
        this.curNodes = 0;
        this.maxNodes = maxNodes;
        this.nodeIndexes = new HashMap<>();
        this.indexNodes = new HashMap<>();
    }

    /**
     * Implements a directional weighted graph as a matrix.
     *
     * @param V set of vertices of the graph.
     * @param E set of edges of the graph.
     * @throws NodeAlreadyPresentException thrown if a node is duplicated in V.
     * @throws NodeNotPresentException thrown if E contains an edge that has at least one endpoint
     * not contained in V.
     */
    public MatrixGraph(List<Node> V, List<Edge<Node>> E)
            throws NodeAlreadyPresentException, NodeNotPresentException {

        // Sets the maximum size of the graph (order of the matrix) to the number of nodes provided.
        this(V.size());

        for (Node v : V) {
            try { addNode(v); }
            catch (FullGraphException ignored) { /* This will not be reached, thus is ignored */ }
        }

        for (Edge<Node> e : E)
            addEdge(e);
    }


    /**
     * Adds a node to the graph, given it is not already present nor the graph is full. Implemented by increasing
     * the counter of the amount of current nodes and by mapping the node to an integer that represents an index
     * of the matrix.
     *
     * @param node object implementing the node interface to be added to the graph.
     * @throws FullGraphException thrown if the graph is already full, as defined by its implementation.
     * @throws NodeAlreadyPresentException thrown if the node being added is already present
     *
     * @see Node
     */
    @Override
    public void addNode(Node node) throws FullGraphException, NodeAlreadyPresentException {
        if (curNodes == maxNodes)
            throw new FullGraphException(maxNodes);

        if (has(node))
            throw new NodeAlreadyPresentException(node);

        // Increase counter
        int index = curNodes++;

        // Persist index-node relation: map node to an index of the matrix
        nodeIndexes.put(node, index);
        indexNodes.put(index, node);
    }

    /**
     * Adds an edge to the graph. Both ends must be nodes already present. If the edge is already present,
     * it is substituted. Implemented by setting the value of the entry (i,j) in the matrix to the weight
     * of the edge provided.
     *
     * @param edge Edge to be added or updated.
     * @throws NodeNotPresentException thrown if either of the edge endpoints is not present in the graph.
     *
     * @see Edge
     */
    @Override
    public void addEdge(Edge<Node> edge) throws NodeNotPresentException {
        int srcIndex = getNodeIndex(edge.getSrcNode());
        int desIndex = getNodeIndex(edge.getDesNode());

        graph[srcIndex][desIndex] = edge.getWeight();
    }

    /**
     * Returns a list containing all the edges that come out of the specified node. Implemented by returning all
     * the nodes relative to the indexes j of the non-zero entries (i,j) of the graph, where i represents the index
     * of the provided node.
     *
     * @param srcNode source node of all the queried edges.
     * @return List containing all the edges.
     * @throws NodeNotPresentException thrown if the specified node is not present in the graph.
     */
    @Override
    public List<Edge<Node>> getEdgesFrom(Node srcNode) throws NodeNotPresentException {
        List<Edge<Node>> edges = new LinkedList<>();

        int srcIndex = getNodeIndex(srcNode);

        for (int desIndex = 0; desIndex != curNodes; ++desIndex) {
            int weight = graph[srcIndex][desIndex];
            if (weight != 0) {
                Node desNode = indexNodes.get(desIndex);
                edges.add(new Edge<>(srcNode, desNode, weight));
            }
        }

        return edges;
    }

    /**
     * Returns the shortest path between two nodes within a graph. Implemented with Dijkstra's path finding algorithm.
     *
     * @param srcNode source node (starting node of the path).
     * @param desNode destiny node (final node of the path).
     * @return shortest path.
     * @throws NodeNotPresentException thrown if either of the specified nodes is not present.
     */
    @Override
    public Path<Node> getShortestPath(Node srcNode, Node desNode) throws NodeNotPresentException {
        int srcIndex = getNodeIndex(srcNode);
        int desIndex = getNodeIndex(desNode);

        Pair<int[], int[]> dijkstraResults = getDijkstraResults(srcIndex);

        int[] dist = dijkstraResults.getValue0();
        int[] prev = dijkstraResults.getValue1();

        Path<Node> path = new Path<>();

        // Set the cost of the path as the cost to reach the destiny node
        path.setCost(dist[desIndex]);

        // If the distance to the destiny node is infinite (they are not connected)
        if (dist[desIndex] == Integer.MAX_VALUE)
            return path; // return empty path with infinite cost

        // Construct the path from destiny to source by backtracking with the prev array
        for (int index = desIndex; index != -1; index = prev[index])
            path.addNode(getNodeAtIndex(index));

        // Reverse the path, so it is directed from source to destiny
        return path.reverse();
    }

    /**
     * Gets the shortest path with at least one intermediate node between two specified nodes. Implemented
     * with Dijkstra's path finding algorithm.
     *
     * @param srcNode source node (starting node of the path).
     * @param desNode destiny node (final node of the path).
     * @return shortest path with at least one intermediate node.
     * @throws NodeNotPresentException thrown if either of the specified nodes is not present.
     */
    @Override
    public Path<Node> getShortestNonDirectPath(Node srcNode, Node desNode)
            throws NodeNotPresentException {

        Path<Node> nonDirectPath;

        try {
            Edge<Node> directPathEdge = removeEdgeBetween(srcNode, desNode);
            nonDirectPath = getShortestPath(srcNode, desNode);
            addEdge(directPathEdge);
        } catch (EdgeNotPresentException e) {
            nonDirectPath = getShortestPath(srcNode, desNode);
        }

        return nonDirectPath;
    }

    /**
     * Gets the index mapped to an inserted node.
     *
     * @param node node currently in the graph.
     * @return index mapped to the node.
     * @throws NodeNotPresentException thrown if there is no such node in the graph.
     */
    private int getNodeIndex(Node node) throws NodeNotPresentException {
        if (!nodeIndexes.containsKey(node))
            throw new NodeNotPresentException(node);
        return nodeIndexes.get(node);
    }

    /**
     * Gets the node object that corresponds to an index.
     *
     * @param index integer between 0 and current amount of nodes.
     * @return node corresponding to index.
     * @throws NodeNotPresentException thrown if there is no such node.
     */
    private Node getNodeAtIndex(int index) throws NodeNotPresentException {
        if (index < 0 || index >= curNodes)
            throw new NodeNotPresentException(null);
        return indexNodes.get(index);
    }

    /**
     * Applies Dijkstra's path finding algorithm to a node of the graph.
     *
     * @param srcIndex any node currently in the graph.
     * @return A pair containing two arrays.The first array contains all the shortest distances from the specified node
     * to all other nodes of the graph. The second array contains the previous node in the path to the nodes, which
     * allows the reconstruction of the shortest path.
     * @throws NodeNotPresentException thrown if the provided node is not present in the graph.
     */
    private Pair<int[], int[]> getDijkstraResults(int srcIndex)
            throws NodeNotPresentException {
        boolean[] visitedNode = new boolean[curNodes];
        int[] distToNode = new int[curNodes];
        int[] prevNode = new int[curNodes];

        Arrays.fill(visitedNode, false);
        Arrays.fill(distToNode, Integer.MAX_VALUE);
        Arrays.fill(prevNode, -1);

        distToNode[srcIndex] = 0;

        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Pair::getValue1));

        pq.add(new Pair<>(srcIndex, 0));

        while (pq.size() != 0) {
            Pair<Integer, Integer> pair = pq.poll();
            int index = pair.getValue0();
            int minValue = pair.getValue1();

            visitedNode[index] = true;

            if (distToNode[index] < minValue)
                continue;

            List<Edge<Node>> edges = getEdgesFrom(getNodeAtIndex(index));

            for (Edge<Node> edge : edges) {
                int desIndex = getNodeIndex(edge.getDesNode());

                if (visitedNode[desIndex])
                    continue;

                int newDist = distToNode[index] + edge.getWeight();

                if (newDist < distToNode[desIndex]) {
                    prevNode[desIndex] = index;
                    distToNode[desIndex] = newDist;
                    pq.add(new Pair<>(desIndex, newDist));
                }

            }

        }
        return new Pair<>(distToNode, prevNode);
    }

    /**
     * Verifies whether there exists an edge between two nodes in the graph. Checks if the entry (i,j) of the matrix
     * that corresponds to the provided nodes is not zero.
     *
     * @param srcNode source node.
     * @param desNode destiny node.
     * @return true if there exists an edge, false otherwise.
     * @throws NodeNotPresentException If either of the specified edges is not present in the graph.
     */
    @Override
    public boolean connects(Node srcNode, Node desNode) throws NodeNotPresentException {
        int index1 = getNodeIndex(srcNode);
        int index2 = getNodeIndex(desNode);
        return graph[index1][index2] != 0;
    }

    /**
     * Verifies the presence of a node in the graph.
     *
     * @param node queried node.
     * @return true if the node is present in the graph, false otherwise.
     */
    @Override
    public boolean has(Node node) {
        return nodeIndexes.containsKey(node);
    }

    /**
     * Verifies the presence of an edge in the graph.
     *
     * @param edge queried edge.
     * @return true if the edge is present, false otherwise.
     * @throws NodeNotPresentException if either of the provided edge endpoints (nodes) is not present in the graph.
     */
    @Override
    public boolean has(Edge<Node> edge) throws NodeNotPresentException {
        Node srcNode = edge.getSrcNode();
        Node desNode = edge.getDesNode();
        try {
            return connects(srcNode, desNode) && getWeightBetween(srcNode, desNode) == edge.getWeight();
        } catch (EdgeNotPresentException e) {
            return  false;
        }
    }

    /**
     * Verifies if graph is empty.
     *
     * @return true if there are no nodes, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return curNodes == 0;
    }

    /**
     * Removes the directional edge between the specified nodes, if it exists.
     *
     * @param srcNode source node of the edge to be removed.
     * @param desNode destiny node of the edge to be removed.
     * @return returns the edge that was removed.
     * @throws EdgeNotPresentException thrown if there is no edge between the specified nodes
     * @throws NodeNotPresentException thrown if either of the specified nodes is not present
     */
    @Override
    public Edge<Node> removeEdgeBetween(Node srcNode, Node desNode) throws EdgeNotPresentException, NodeNotPresentException {
        Edge<Node> edge = getEdgeBetween(srcNode, desNode);

        graph[getNodeIndex(edge.getSrcNode())][getNodeIndex(edge.getDesNode())] = 0;

        return edge;
    }

    /**
     * Returns the weight of the edge between two nodes of the graph.
     *
     * @param srcNode source node of the queried edge.
     * @param desNode destiny node of the queried edge.
     * @return weight of the edge with the specified endpoints.
     * @throws EdgeNotPresentException thrown if there is no edge between such nodes.
     * @throws NodeNotPresentException thrown if either of the nodes is not present in the graph.
     */
    @Override
    public int getWeightBetween(Node srcNode, Node desNode) throws EdgeNotPresentException, NodeNotPresentException {
        if (!connects(srcNode, desNode))
            throw new EdgeNotPresentException(srcNode, desNode);

        int srcIndex = getNodeIndex(srcNode);
        int desIndex = getNodeIndex(desNode);

        return graph[srcIndex][desIndex];
    }

    /**
     * Retrieves the edge between two nodes in the graph, if it exists.
     *
     * @param srcNode source node of the queried edge.
     * @param desNode destiny node of the queried edge.
     * @return returns the edge between the specified nodes.
     * @throws NodeNotPresentException thrown if either of the nodes is not present.
     * @throws EdgeNotPresentException thrown if there is no edge between the specified nodes.
     */
    @Override
    public Edge<Node> getEdgeBetween(Node srcNode, Node desNode) throws NodeNotPresentException, EdgeNotPresentException {

        int weight = getWeightBetween(srcNode, desNode);

        return new Edge<>(srcNode, desNode, weight);
    }

}
