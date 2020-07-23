package azzy.fabric.lff.structures;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.LinkedList;
import java.util.Set;

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
    LinkedList<NetworkPos> getPath(BlockPos start, BlockPos goal, Set<CostPair> mesh, Set<Block> obstructors);

    /**
     * Gets the shortest unobstructed path to the goal that does not pass through any exempted blocks. May return null.
     *
     * @param start The starting position of the pathfinder.
     * @param goal  The position the pathfinder will create paths to.
     * @param mesh  A list of objects the pathfinder can path through paired with their respective costs.
     * @param exceptions A set of positions that the pathfinder will not, under any conditions
     * @return A list of BlockPos that make up the path the pathfinder found, in order of traversal.
     * @see CostPair
     */
    LinkedList<NetworkPos> getPathExcept(BlockPos start, BlockPos goal, Set<CostPair> mesh, LinkedList<BlockPos> exceptions, Set<Block> obstructors);

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
    ReasonPair getTrueShortestPath(BlockPos start, BlockPos goal, Set<CostPair> mesh, Set<Block> obstructors);

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
    LinkedList<ReasonPair>[] getPaths(BlockPos start, BlockPos goal, Set<CostPair> mesh, Set<Block> obstructors);

    /**
     * A combination pair of an object a pathfinder can path through and its cost
     */
    class CostPair {

        private final Block medium;
        private final int value;

        public CostPair(Block medium, int value){
            this.medium = medium;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public net.minecraft.block.Block getMedium(){
            return medium;
        }
    }

    class CostPos implements Comparable<CostPos>{

        private final BlockPos location;
        private int value;
        private final CostPos parent;
        private final boolean obstruction;

        public CostPos(BlockPos location, int value){
            this.location = location;
            this.value = value;
            parent = null;
            obstruction = false;
        }

        public CostPos(BlockPos location, int value, CostPos parent){
            this.location = location;
            this.value = value;
            this.parent = parent;
            obstruction = false;
        }

        public CostPos(BlockPos location, int value, CostPos parent, boolean obstructs){
            this.location = location;
            this.value = value;
            this.parent = parent;
            obstruction = obstructs;
        }


        @Override
        public int compareTo(CostPos o) {
            return value;
        }

        public BlockPos getLocation() {
            return location;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public CostPos getParent() {
            return parent;
        }

        public boolean hasObstruction() {
            return obstruction;
        }
    }

    /**
     * A combination pair of a path and an array of positions that prevent free transactions
     */
    class ReasonPair{

        private final BlockPos[] obstructions;
        private final LinkedList<NetworkPos> path;

        public ReasonPair(BlockPos[] obstructions, LinkedList<NetworkPos> path){
            this.obstructions = obstructions;
            this.path = path;
        }

    }

    class NetworkPos{

        private final BlockPos location;
        private final boolean turn;

        public NetworkPos(BlockPos location, boolean turn){
            this.location = location;
            this.turn = turn;
        }

        public NetworkPos(BlockPos location){
            this.location = location;
            this.turn = false;
        }

        public boolean isTurn() {
            return turn;
        }

        public BlockPos blockPos(){
            return location;
        }
    }
}
