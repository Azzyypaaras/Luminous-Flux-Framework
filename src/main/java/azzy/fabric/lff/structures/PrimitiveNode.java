package azzy.fabric.lff.structures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

abstract class PrimitiveNode<T extends NodeGraph, K extends PrimitiveEdge, V extends PrimitiveNode> {

    private volatile T network;
    private volatile HashMap<V, K> edges = new HashMap<>();

    protected PrimitiveNode(T network){
        this.network = network;
    }

    public NodeGraph getNetwork(){
        return network;
    }

    public void setNetwork(T network) {
        this.network = network;
    }

    public K getEdge(V node){
        return edges.get(node);
    }

    public void createEdge(V targetNode){
        edges.put(targetNode, (K) PrimitiveEdge.of(this, targetNode));
    }
}
