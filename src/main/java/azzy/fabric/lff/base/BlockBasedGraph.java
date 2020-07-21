package azzy.fabric.lff.base;

import azzy.fabric.lff.structures.BoundNode;
import azzy.fabric.lff.structures.Pathfinder;
import azzy.fabric.lff.structures.PrimitiveEdge;
import azzy.fabric.lff.structures.WorldGraph;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class BlockBasedGraph<T extends BoundNode<? extends BlockBasedGraph<T, K>, T>, K extends BlockEdge<T>> extends WorldGraph<T, K> {

    Set<BlockPos> members = new HashSet<>();

    public BlockBasedGraph(ServerWorld world, T bindingNode) {
        super(world, bindingNode);
    }
}
