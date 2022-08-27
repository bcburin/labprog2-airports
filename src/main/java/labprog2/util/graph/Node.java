package labprog2.util.graph;

/**
 * Interface that must be implemented so an object can be a node in a graph.
 */
public interface Node {

    @Override
    public boolean equals(Object other);

    @Override
    public int hashCode();

    @Override
    public String toString();

}
