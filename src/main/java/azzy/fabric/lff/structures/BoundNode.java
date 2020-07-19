package azzy.fabric.lff.structures;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BoundNode<T extends WorldGraph, K extends PrimitiveEdge, V extends BoundNode>  extends PrimitiveNode<T, K, V> {

    private final BlockPos pos;
    private final Block block;

    protected BoundNode(T network, BlockPos pos, Block block) {
        super(network);
        this.pos = pos;
        this.block = block;
    }

    @SuppressWarnings("unchecked")
    public World getParentWorld(){
        return ((T) getNetwork()).getWorld();
    }

    public boolean integrityCheck(BlockPos pos){
        return getParentWorld().getBlockState(pos).getBlock() == block;
    }
}
