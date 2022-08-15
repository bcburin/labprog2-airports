package labprog2.util.graph;

import labprog2.util.graph.exceptions.EdgeNotPresentException;
import labprog2.util.graph.exceptions.FullGraphException;
import labprog2.util.graph.exceptions.NodeAlreadyPresentException;
import labprog2.util.graph.exceptions.NodeNotPresentException;

public interface Graph {
    public Graph addNode(Node node) throws FullGraphException, NodeAlreadyPresentException;

    public Graph addEdge(Edge edge) throws NodeNotPresentException;

    public Edge removeEdgeBetween(Node srcNode, Node desNode) throws EdgeNotPresentException, NodeNotPresentException;

    public Edge getEdgeBetween(Node srcNode, Node desNode) throws NodeNotPresentException, EdgeNotPresentException;

    public Node[] getNodes();

    public Edge[] getEdges();

    public Edge[] getEdgesFrom(Node srcNode) throws NodeNotPresentException;

    public Path getShortestPath(Node srcNode, Node desNode) throws NodeNotPresentException;

    public Path getShortestNonDirectPath(Node srcNode, Node desNode)
            throws NodeNotPresentException, EdgeNotPresentException;

    public boolean connects(Node srcNode, Node desNode) throws NodeNotPresentException;

    public boolean has(Node node);

    public boolean has(Edge edge) throws NodeNotPresentException, EdgeNotPresentException;

    public boolean isEmpty();

    public int getWeightBetween(Node srcNode, Node desNode) throws EdgeNotPresentException, NodeNotPresentException;
}
