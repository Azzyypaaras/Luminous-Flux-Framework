package azzy.fabric.lff.structures;

import net.minecraft.server.world.ServerWorld;

public abstract class WorldGraph<T extends BoundNode, K extends PrimitiveEdge, V extends NodeGraph> extends NodeGraph{

    private final T bindingNode;
    private final ServerWorld world;

    @SuppressWarnings("unchecked")
    protected WorldGraph(ServerWorld world, T bindingNode){
        this.world = world;
        this.bindingNode = bindingNode;
        addNode(bindingNode);
    }

    public ServerWorld getWorld() {
        return world;
    }
}