package azzy.fabric.lff.structures;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public abstract class NodeGraph<T extends PrimitiveNode<?, T>, K extends PrimitiveEdge<T>> {

    private final List<T> nodes = new LinkedList<>();
    private final EdgeMap<T, K> edges = new EdgeMap<>();
    private BiFunction<T, T, K> edgeFactory = (a, b) -> null;

    public T getNode(int id){
        return nodes.get(id);
    }

    public T getNode(int id, Class<T> nodeType){
        return nodeType.cast(nodes.get(id));
    }

    public boolean createEdge(T nodeA, T nodeB){
        return edges.put(nodeA, nodeB, edgeFactory.apply(nodeA, nodeB));
    }

    public K getEdge(T node){
        return edges.getEdge(node);
    }

    public int getNodeId(T node){
        return nodes.indexOf(node);
    }

    public void mergeNodes(Collection<T> mergedNodes){
        nodes.addAll(mergedNodes);
    }

    public List<T> getNodes() {
        return nodes;
    }

    public void setEdgeFactory(BiFunction<T, T, K> edgeFactory) {
        this.edgeFactory = edgeFactory;
    }

    public Stream<T> nodeStream(){
        return nodes.parallelStream();
    }

    public int addNode(T node){
        nodes.add(node);
        return nodes.size() - 1;
    }
}
