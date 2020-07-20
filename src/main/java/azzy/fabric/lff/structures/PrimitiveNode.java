package azzy.fabric.lff.structures;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

abstract class PrimitiveNode<T extends NodeGraph, V extends PrimitiveNode> {

    private volatile T network;
    private volatile AtomicBoolean scheduledUpdate;

    PrimitiveNode(T network){
        this.network = network;
    }

    public NodeGraph getNetwork(){
        return network;
    }

    public void setNetwork(T network) {
        this.network = network;
    }

    @SuppressWarnings("unchecked")
    public void createEdge(V targetNode){
        network.createEdge(this, targetNode);
    }

    @SuppressWarnings("unchecked")
    public synchronized void createAndValidateEdge(V targetNode){
        network.createEdge(this, targetNode);
        network.getEdge(targetNode).validate();
    }

    public boolean hasScheduledUpdate(){
        return scheduledUpdate.get();
    }

    public abstract void update();
}
