package azzy.fabric.lff.structures;

import java.util.*;
import java.util.stream.Stream;


public abstract class NodeGraph<T extends PrimitiveNode, K extends PrimitiveEdge, V extends NodeGraph> implements Runnable{

    private final List<T> nodes = new LinkedList<>();

    public abstract V create();

    public abstract boolean validateDeletion();

    @Override
    public void run() {
        for (T node : nodes) {
            if(node.hasScheduledUpdate())
                node.update();
        }
    }

    public T getNode(int id){
        return nodes.get(id);
    }

    public T getNode(int id, Class<T> nodeType){
        return nodeType.cast(nodes.get(id));
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

    public Stream<T> nodeStream(){
        return nodes.parallelStream();
    }

    public int addNode(T node){
        nodes.add(node);
        return nodes.size() - 1;
    }
}
