package azzy.fabric.lff.base;

import azzy.fabric.lff.structures.BoundNode;
import azzy.fabric.lff.structures.NodeGraph;
import azzy.fabric.lff.structures.WorldGraph;
import net.minecraft.server.world.ServerWorld;

public class BlockEdgeGraph extends WorldGraph {


    protected BlockEdgeGraph(ServerWorld world, BoundNode bindingNode) {
        super(world, bindingNode);
    }

    @Override
    public NodeGraph create() {
        return null;
    }
}
