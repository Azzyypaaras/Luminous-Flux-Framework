package azzy.fabric.lff.base;

import azzy.fabric.lff.structures.BoundNode;
import azzy.fabric.lff.structures.Pathfinder;
import azzy.fabric.lff.structures.PrimitiveEdge;
import azzy.fabric.lff.structures.WorldGraph;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class BlockBasedGraph<P extends BoundNode<BlockBasedGraph<P, T>, P, T>, T extends BlockEdge<P>> extends WorldGraph<T, P, BlockBasedGraph<P, T>>
{

    Set<BlockPos> members = new HashSet<>();

    public BlockBasedGraph(ServerWorld world, BoundNode bindingNode) {
        super(world, bindingNode);
    }


}
