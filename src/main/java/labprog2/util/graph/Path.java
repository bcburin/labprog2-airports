package labprog2.util.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Path {
    private List<Node> path;
    private int cost;

    public Path(Collection<Node> nodes, int cost) {
        this.cost = cost;
        this.path = new LinkedList<Node>();
        this.path.addAll(nodes);
    }

    public Path() {
        this.cost = Integer.MAX_VALUE;
        this.path = new LinkedList<Node>();
    }

    public Path addNode(Node node) {
        path.add(node);
        return this;
    }

    public Path reverse() {
        Collections.reverse(path);
        return this;
    }

    public Node getStart() {
        return path.get(0);
    }

    public Node getEnd() {
        return path.get(path.size() - 1);
    }

    public List<Node> getNodes() {
        return path;
    }

    public int getCost() {
        return cost;
    }

    public boolean isEmpty() {
        return path.size() == 0;
    }

    public void print() {
        for (int i = 0; i != path.size(); ++i) {
            System.out.print(((i == 0) ? "" : " -> ") + path.get(i).toString());
        }
        System.out.println();
    }

}
