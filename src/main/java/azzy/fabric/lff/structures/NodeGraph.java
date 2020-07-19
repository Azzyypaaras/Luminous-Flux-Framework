package azzy.fabric.lff.structures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public abstract class NodeGraph<T extends PrimitiveNode, K extends PrimitiveEdge> {

    private final List<T> nodes = new LinkedList<>();

    private static final Random RANDOM = new Random();
    private final long UUID;
    private volatile short idCount;

    protected NodeGraph(){
        UUID =
    }

    protected T getNode(int id){
        return nodes.get(id);
    }

    protected T getNode(int id, Class<T> nodeType){
        return nodeType.cast(nodes.get(id));
    }

    protected int addNode(T node){
        nodes.add(node);
        return nodes.size() - 1;
    }

    synchronized short generateId(){
        return idCount++;
    }
}
