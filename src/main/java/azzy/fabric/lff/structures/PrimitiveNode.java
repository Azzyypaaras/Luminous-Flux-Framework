package azzy.fabric.lff.structures;

abstract class PrimitiveNode<T extends NodeGraph> {

    private T network;

    private PrimitiveNode(T network){
        this.network = network;
    }

    public NodeGraph getNetwork(){
        return network;
    }



}
