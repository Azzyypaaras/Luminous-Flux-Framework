package azzy.fabric.lff.structures;

abstract class PrimitiveEdge<T extends PrimitiveNode> {

    private T nodeA, nodeB;
    private boolean validated;

    protected PrimitiveEdge(T nodeA, T nodeB){
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    public static <T extends PrimitiveNode> PrimitiveEdge of(T nodeA, T nodeB){
        return null;
    }

    abstract void validate();

    abstract void check();

    public boolean isValidated() {
        return validated;
    }
}
