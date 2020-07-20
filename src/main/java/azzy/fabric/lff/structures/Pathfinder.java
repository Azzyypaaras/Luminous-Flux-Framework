package azzy.fabric.lff.structures;

import net.minecraft.util.math.BlockPos;

import java.util.LinkedList;

/**
 * An interface dictating any potential interactions with pathfinders. While Luminous Flux Framework comes prepackaged with
 * a few pathfinders, it is perfectly possible to define new ones and even replace the pathfinders of preexisting nodes.
 * However, any and all pathfinders to be used by a node must implement all methods listed bellow in the described ways,
 * otherwise, unexpected behaviour may arise.
 *
 * @see ReasonPair
 */
public interface Pathfinder{

    /**
     * Gets the shortest unobstructed path to the goal
     *
     * @param start The starting position of the pathfinder.
     * @param goal  The position the pathfinder will create paths to.
     * @param mesh  A list of objects the pathfinder can path through paired with their respective costs.
     * @return A list of BlockPos that make up the path the pathfinder found, in order of traversal.
     * @see CostPair
     */
    LinkedList<BlockPos> getPath(BlockPos start, BlockPos goal, CostPair[] mesh);

    /**
     * Gets the shortest unobstructed path to the goal that does not equal
     *
     * @param start The starting position of the pathfinder.
     * @param goal  The position the pathfinder will create paths to.
     * @param mesh  A list of objects the pathfinder can path through paired with their respective costs.
     * @return A list of BlockPos that make up the path the pathfinder found, in order of traversal.
     * @see CostPair
     */
    LinkedList<BlockPos> getPathExcept(BlockPos start, BlockPos goal, CostPair[] mesh, LinkedList<BlockPos> exceptions);

    /**
     * Gets the absolute shortest path to the goal, regardless of any obstructions
     *
     * @param start The starting position of the pathfinder.
     * @param goal  The position the pathfinder will create paths to.
     * @param mesh  A list of objects the pathfinder can path through paired with their respective costs.
     * @return A ReasonPair containing the path and a list of positions that prevent free transactions.
     * @see CostPair
     * @see CostPair
     */
    ReasonPair getTrueShortestPath(BlockPos start, BlockPos goal, CostPair[] mesh);

    /**
     * Gets the shortest unobstructed path to the goal and every path shorter than that, regardless of whether or not they are obstructed.
     * The paths should be ordered within the array by length, but that ultimately comes down to implementation and there is no guarantee that such will actually be the case,
     * so do not count on that for any applications of this.
     *
     * @param start The starting position of the pathfinder.
     * @param goal  The position the pathfinder will create paths to.
     * @param mesh  A list of objects the pathfinder can path through paired with their respective costs.
     * @return A list of ReasonPairs containing a path and an array of positions that prevent free transactions
     * @see CostPair
     * @see ReasonPair
     */
    LinkedList<ReasonPair>[] getPaths(BlockPos start, BlockPos goal, CostPair[] mesh);

    /**
     * Gets every non-intercepting path to the goal.
     * I don't know *why* you would want this, but if you don't cache its result I will stab you in your sleep
     *
     * @param start The starting position of the pathfinder.
     * @param goal  The position the pathfinder will create paths to.
     * @param mesh  A list of objects the pathfinder can path through paired with their respective costs.
     * @return A list of ReasonPairs containing a path and an array of positions that prevent free transactions
     * @see CostPair
     * @see ReasonPair
     */
    LinkedList<ReasonPair>[] getAllPaths(BlockPos start, BlockPos goal, CostPair[] mesh);

    /**
     * A combination pair of an object a pathfinder can path through and its cost
     *
     * @param <K> A value indicating what kind of types can be pathed through
     */
    class CostPair<K>{

        private final K medium;
        private final byte value;

        public CostPair(K medium, byte value){
            this.medium = medium;
            this.value = value;
        }
    }

    /**
     * A combination pair of a path and an array of positions that prevent free transactions
     */
    class ReasonPair{

        private final BlockPos[] obstructions;
        private final LinkedList<BlockPos> path;

        public ReasonPair(BlockPos[] obstructions, LinkedList<BlockPos> path){
            this.obstructions = obstructions;
            this.path = path;
        }

    }
}
