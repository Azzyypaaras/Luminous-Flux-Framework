package azzy.fabric.lff.base;

import azzy.fabric.lff.structures.BoundNode;
import azzy.fabric.lff.structures.Pathfinder;
import azzy.fabric.lff.structures.WorldGraph;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class PathingNode extends BoundNode<BlockBasedGraph<PathingNode>, PathingNode, BlockEdge<PathingNode>>{

    Pathfinder pathfinder;

    protected PathingNode(WorldGraph network, BlockPos pos, Block block, Pathfinder pathfinder) {
        super(network, pos, block);
        this.pathfinder = pathfinder;
    }

    @Override
    public void update() {

    }
}
