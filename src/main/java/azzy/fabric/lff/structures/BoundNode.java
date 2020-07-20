package azzy.fabric.lff.structures;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BoundNode<T extends WorldGraph, V extends PrimitiveNode, U extends Pathfinder, K extends BoundNode>  extends PrimitiveNode<T, V> {

    private final BlockPos pos;
    private final Block block;
    private U pathfinder;

    protected BoundNode(T network, BlockPos pos, Block block, U pathfinder) {
        super(network);
        this.pos = pos;
        this.block = block;
        this.pathfinder = pathfinder;
    }

    public BlockPos getPos() {
        return pos;
    }

    @SuppressWarnings("unchecked")
    public World getParentWorld(){
        return ((T) getNetwork()).getWorld();
    }

    public boolean checkIntegrity(BlockPos pos){
        return getParentWorld().getBlockState(pos).getBlock() == block;
    }
}
