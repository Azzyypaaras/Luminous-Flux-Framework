package azzy.fabric.lff.base;

import azzy.fabric.lff.structures.BoundNode;
import azzy.fabric.lff.structures.PrimitiveEdge;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class BlockEdge<K extends BoundNode<BlockBasedGraph<K>, K, BlockEdge>> extends PrimitiveEdge<BlockBasedGraph<K>, K, BlockEdge<K>> {

    protected volatile List<BlockPos> cachedPath;

    protected BlockEdge(K nodeA, K nodeB) {
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
