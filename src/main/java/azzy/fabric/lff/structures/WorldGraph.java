package azzy.fabric.lff.structures;

import net.minecraft.server.world.ServerWorld;

public abstract class WorldGraph<T extends PrimitiveEdge<V, K, T>, K extends BoundNode<V, K, T>, V extends WorldGraph<T, K, V>> extends NodeGraph<K, T, V>{

    protected final K bindingNode;
    private final ServerWorld world;

    @SuppressWarnings("unchecked")
    protected WorldGraph(ServerWorld world, K bindingNode){
        this.world = world;
        this.bindingNode = bindingNode;
        addNode(bindingNode);
    }

    public ServerWorld getWorld() {
        return world;
    }
}