package azzy.fabric.lff.structures;

public abstract class PrimitiveEdge<T extends NodeGraph<V, K, T>, V extends PrimitiveNode<T, V, K>, K extends PrimitiveEdge<T, V, K>> {

    protected V nodeA, nodeB;
    protected boolean validated;

    protected PrimitiveEdge(V nodeA, V nodeB){
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public static <T extends PrimitiveNode> PrimitiveEdge of(T nodeA, T nodeB){
        return null;
    }

    public abstract void validate();

    public abstract void check();

    public boolean isValidated() {
        return validated;
    }
}
