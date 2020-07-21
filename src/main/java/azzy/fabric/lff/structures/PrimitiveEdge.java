package azzy.fabric.lff.structures;

public abstract class PrimitiveEdge<T extends PrimitiveNode<?, ?>> {

    protected T nodeA, nodeB;
    protected boolean validated;

    protected PrimitiveEdge(T nodeA, T nodeB){
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public abstract void validate();

    public abstract void check();

    public boolean isValidated() {
        return validated;
    }
}
