package azzy.fabric.lff.base;

import azzy.fabric.lff.structures.Pathfinder;
import net.minecraft.util.math.BlockPos;

import java.util.LinkedList;

public class APathfinder<N extends PathingNode<T, N>, T extends BlockBasedGraph<N, ? extends BlockEdge<N>>> implements Pathfinder {

    private T network;

    public APathfinder(T network){
        this.network = network;
    }

    @Override
    public LinkedList<BlockPos> getPath(BlockPos start, BlockPos goal, CostPair[] mesh) {
        return null;
    }

    @Override
    public ReasonPair getTrueShortestPath(BlockPos start, BlockPos goal, CostPair[] mesh) {
        return null;
    }

    @Override
    public LinkedList<ReasonPair>[] getPaths(BlockPos start, BlockPos goal, CostPair[] mesh) {
        return new LinkedList[0];
    }

    @Override
    public LinkedList<ReasonPair>[] getAllPaths(BlockPos start, BlockPos goal, CostPair[] mesh) {
        return new LinkedList[0];
    }

    @Override
    public LinkedList<BlockPos> getPathExcept(BlockPos start, BlockPos goal, CostPair[] mesh, LinkedList exceptions) {
        return null;
    }
}
