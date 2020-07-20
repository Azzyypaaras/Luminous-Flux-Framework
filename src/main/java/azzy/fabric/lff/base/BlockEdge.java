package azzy.fabric.lff.base;

import azzy.fabric.lff.structures.BoundNode;
import azzy.fabric.lff.structures.PrimitiveEdge;
import jdk.nashorn.internal.ir.Block;

import java.util.List;

public class BlockEdge<T extends Block, K extends BoundNode> extends PrimitiveEdge {

    protected volatile List<T> cachedPath;

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
