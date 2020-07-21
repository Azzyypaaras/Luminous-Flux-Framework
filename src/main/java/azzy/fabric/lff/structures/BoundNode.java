package azzy.fabric.lff.structures;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BoundNode<T extends WorldGraph<V, ?>, V extends BoundNode<T, V>> extends PrimitiveNode<T, V> {

    private final BlockPos pos;
    private final Block block;

    protected BoundNode(T network, BlockPos pos, Block block) {
        super(network);
        this.pos = pos;
        this.block = block;
    }

    public BlockPos getPos() {
        return pos;
    }

    public World getParentWorld(){
        return getNetwork().getWorld();
    }

    public boolean checkIntegrity(BlockPos pos){
        return getParentWorld().getBlockState(pos).getBlock() == block;
    }
}
