package labprog2.util.graph;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import labprog2.util.graph.exceptions.EdgeNotPresentException;
import labprog2.util.graph.exceptions.FullGraphException;
import labprog2.util.graph.exceptions.NodeAlreadyPresentException;
import labprog2.util.graph.exceptions.NodeNotPresentException;

import org.javatuples.Pair;

public class MatrixGraph implements Graph {
    private int[][] graph;
    private int curNodes;
    private int maxNodes;
    private Map<Node, Integer> nodeIndexes;
    private Map<Integer, Node> indexNodes;

    public MatrixGraph(int maxNodes) {
        this.graph = new int[maxNodes][maxNodes];
        this.curNodes = 0;
        this.maxNodes = maxNodes;
        this.nodeIndexes = new HashMap<Node, Integer>();
        this.indexNodes = new HashMap<Integer, Node>();
    }

    public MatrixGraph(Node[] V, Edge[] E)
            throws FullGraphException, NodeAlreadyPresentException, NodeNotPresentException {

        this(V.length);

        for (Node v : V)
            addNode(v);
        for (Edge e : E)
            addEdge(e);
    }

    @Override
    public Graph addNode(Node node) throws FullGraphException, NodeAlreadyPresentException {
        if (curNodes == maxNodes)
            throw new FullGraphException(maxNodes);

        if (has(node))
            throw new NodeAlreadyPresentException(node);

        int index = curNodes++;

        // Persist index-node relation
        nodeIndexes.put(node, index);
        indexNodes.put(index, node);

        return this;
    }

    @Override
    public Graph addEdge(Edge edge) throws NodeNotPresentException {
        int srcIndex = getNodeIndex(edge.getSrcNode());
        int desIndex = getNodeIndex(edge.getDesNode());

        graph[srcIndex][desIndex] = edge.getWeight();

        return this;
    }

    @Override
    public Node[] getNodes() {
        return nodeIndexes.keySet().toArray(new Node[0]);
    }

    @Override
    public Edge[] getEdges() {
        List<Edge> edges = new LinkedList<Edge>();

        for (int i = 0; i != curNodes; ++i) {
            for (int j = 0; j != curNodes; ++j) {
                int weight = graph[i][j];
                if (weight != 0) {
                    Node srcNode = indexNodes.get(i);
                    Node desNode = indexNodes.get(j);
                    edges.add(new Edge(srcNode, desNode, weight));
                }
            }
        }

        return edges.toArray(new Edge[0]);
    }

    @Override
    public Edge[] getEdgesFrom(Node srcNode) throws NodeNotPresentException {
        List<Edge> edges = new LinkedList<Edge>();

        int srcIndex = getNodeIndex(srcNode);

        for (int desIndex = 0; desIndex != curNodes; ++desIndex) {
            int weight = graph[srcIndex][desIndex];
            if (weight != 0) {
                Node desNode = indexNodes.get(desIndex);
                edges.add(new Edge(srcNode, desNode, weight));
            }
        }

        return edges.toArray(new Edge[0]);
    }

    @Override
    public Path getShortestPath(Node srcNode, Node desNode) throws NodeNotPresentException {
        int srcIndex = getNodeIndex(srcNode);
        int desIndex = getNodeIndex(desNode);

        Pair<int[], int[]> dijkstraResults = getDijkstraResults(srcIndex);

        int[] dist = dijkstraResults.getValue0();
        int[] prev = dijkstraResults.getValue1();

        Path path = new Path();

        if (dist[desIndex] == Integer.MAX_VALUE)
            return path;

        for (int index = desIndex; index != -1; index = prev[index])
            path.addNode(getNodeAtIndex(index));

        return path.reverse();
    }

    // @Override
    // public Path getShortestPath(Node srcNode, Node desNode) throws
    // NodeNotPresentException {
    // return getShortestPathUtil(srcNode, desNode);
    // }

    @Override
    public Path getShortestNonDirectPath(Node srcNode, Node desNode)
            throws NodeNotPresentException, EdgeNotPresentException {

        Path nonDirectPath = new Path();

        if (connects(srcNode, desNode)) {
            Edge directPathEdge = removeEdgeBetween(srcNode, desNode);
            nonDirectPath = getShortestPath(srcNode, desNode);
            addEdge(directPathEdge);
        }

        return nonDirectPath;
    }

    private int getNodeIndex(Node node) throws NodeNotPresentException {
        if (!nodeIndexes.containsKey(node))
            throw new NodeNotPresentException(node);
        return nodeIndexes.get(node);
    }

    private Node getNodeAtIndex(int index) throws NodeNotPresentException {
        if (index < 0 || index >= curNodes)
            throw new NodeNotPresentException(null);
        return indexNodes.get(index);
    }

    private Pair<int[], int[]> getDijkstraResults(int srcIndex)
            throws NodeNotPresentException {
        boolean[] visitedNode = new boolean[curNodes];
        int[] distToNode = new int[curNodes];
        int[] prevNode = new int[curNodes];

        Arrays.fill(visitedNode, false);
        Arrays.fill(distToNode, Integer.MAX_VALUE);
        Arrays.fill(prevNode, -1);

        distToNode[srcIndex] = 0;

        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(
                (pair1, pair2) -> Integer.compare(pair1.getValue1(), pair2.getValue1()));

        pq.add(new Pair<Integer, Integer>(srcIndex, 0));

        while (pq.size() != 0) {
            Pair<Integer, Integer> pair = pq.poll();
            int index = pair.getValue0();
            int minValue = pair.getValue1();

            visitedNode[index] = true;

            if (distToNode[index] < minValue)
                continue;

            Edge[] edges = getEdgesFrom(getNodeAtIndex(index));

            for (Edge edge : edges) {
                int desIndex = getNodeIndex(edge.getDesNode());

                if (visitedNode[desIndex])
                    continue;

                int newDist = distToNode[index] + edge.getWeight();

                if (newDist < distToNode[desIndex]) {
                    prevNode[desIndex] = index;
                    distToNode[desIndex] = newDist;
                    pq.add(new Pair<Integer, Integer>(desIndex, newDist));
                }

            }

        }
        return new Pair<int[], int[]>(distToNode, prevNode);
    }

    @Override
    public boolean connects(Node srcNode, Node desNode) throws NodeNotPresentException {
        int index1 = getNodeIndex(srcNode);
        int index2 = getNodeIndex(desNode);
        return graph[index1][index2] != 0;
    }

    @Override
    public boolean has(Node node) {
        return nodeIndexes.keySet().contains(node);
    }

    @Override
    public boolean has(Edge edge) throws NodeNotPresentException, EdgeNotPresentException {
        Node srcNode = edge.getSrcNode();
        Node desNode = edge.getDesNode();
        return connects(srcNode, desNode) && getWeightBetween(srcNode, desNode) == edge.getWeight();
    }

    @Override
    public boolean isEmpty() {
        return curNodes == 0;
    }

    @Override
    public Edge removeEdgeBetween(Node srcNode, Node desNode) throws EdgeNotPresentException, NodeNotPresentException {
        Edge edge = getEdgeBetween(srcNode, desNode);

        graph[getNodeIndex(edge.getSrcNode())][getNodeIndex(edge.getDesNode())] = 0;

        return edge;
    }

    @Override
    public int getWeightBetween(Node srcNode, Node desNode) throws EdgeNotPresentException, NodeNotPresentException {
        if (!connects(srcNode, desNode))
            throw new EdgeNotPresentException(srcNode, desNode);

        int srcIndex = getNodeIndex(srcNode);
        int desIndex = getNodeIndex(desNode);

        int weight = graph[srcIndex][desIndex];

        return weight;
    }

    @Override
    public Edge getEdgeBetween(Node srcNode, Node desNode) throws NodeNotPresentException, EdgeNotPresentException {

        int weight = getWeightBetween(srcNode, desNode);

        return new Edge(srcNode, desNode, weight);
    }

}
