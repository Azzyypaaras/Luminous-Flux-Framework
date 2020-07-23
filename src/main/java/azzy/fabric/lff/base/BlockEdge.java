package azzy.fabric.lff.base;

import azzy.fabric.lff.structures.BoundNode;
import azzy.fabric.lff.structures.Pathfinder;
import azzy.fabric.lff.structures.PrimitiveEdge;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class BlockEdge<T extends BoundNode<? extends BlockBasedGraph<T, ? extends BlockEdge<T>>, T>> extends PrimitiveEdge<T> {

    protected volatile List<Pathfinder.NetworkPos> cachedPath;

    protected BlockEdge(T nodeA, T nodeB) {
        super(nodeA, nodeB);
    }

    @Override
    public void validate() {
        validated = true;
    }

    @Override
    public void check() {
    }
}
