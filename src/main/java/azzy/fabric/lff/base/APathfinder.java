package azzy.fabric.lff.base;

import azzy.fabric.lff.structures.Pathfinder;
import azzy.fabric.lff.structures.Registry;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.*;

public class APathfinder<N extends PathingNode<T, N>, T extends BlockBasedGraph<N, ? extends BlockEdge<N>>> implements Pathfinder {

    private T network;
    private Comparator<Pathfinder.CostPos> comparator = Comparator.comparing(e -> -e.getValue());
    private PriorityQueue<Pathfinder.CostPos> paths = new PriorityQueue<>(32, comparator);

    public APathfinder(T network){
        this.network = network;
    }

    @Override
    public LinkedList<NetworkPos> getPath(BlockPos start, BlockPos goal, Set<CostPair> mesh, Set<Block> obstructors) {
        return null;
    }

    @Override
    public LinkedList<NetworkPos> getPathExcept(BlockPos start, BlockPos goal, Set<CostPair> mesh, LinkedList<BlockPos> exceptions, Set<Block> obstructors) {
        return null;
    }

    @Override
    public ReasonPair getTrueShortestPath(BlockPos start, BlockPos goal, Set<CostPair> mesh, Set<Block> obstructors) {
        return null;
    }

    @Override
    public LinkedList<ReasonPair>[] getPaths(BlockPos start, BlockPos goal, Set<CostPair> mesh, Set<Block> obstructors) {
        return new LinkedList[0];
    }


    private List<NetworkPos> pathfind(BlockPos start, BlockPos goal, Set<CostPair> mesh, LinkedList<BlockPos> exceptions, Set<Block> obstructors){
        paths.offer(new CostPos(start, Integer.MAX_VALUE));
        boolean success = false;
        CostPos pos = null;
        Set<BlockPos> visited = new HashSet<>(exceptions);
        visited.add(start);

        while(!paths.isEmpty()) {
            pos = paths.poll();
            if (pos.getLocation() == goal) {
                success = true;
                break;
            }
            List<CostPos> next = look(pos, mesh, visited, obstructors);

            for (CostPos costPos : next) {
                int startDistance = costPos.getLocation().getManhattanDistance(new Vec3i(start.getX(), start.getY(), start.getZ()));
                int goalDistance = costPos.getLocation().getManhattanDistance(new Vec3i(goal.getX(), goal.getY(), goal.getZ()));

                costPos.setValue((costPos.getValue() + startDistance + goalDistance));
                visited.add(costPos.getLocation());
                paths.offer(costPos);
            }
        }

        if(!success)
            return null;

        return trackeback(pos, start);
    }

    private List<CostPos> look(CostPos current, Set<CostPair> mesh, Set<BlockPos> visited, Set<Block> obstructors){
        List<CostPos> sorroundings = new LinkedList<>();
        Set<Block> validBlocks = new HashSet<>(Registry.getInstance().getGraphBlocks(network.getClass()));
        BlockPos[] faces = new BlockPos[6];
        CostPos currentPos = null;
        World world = network.getWorld();

        faces[0] = current.getLocation().north();
        faces[1] = current.getLocation().east();
        faces[2] = current.getLocation().south();
        faces[3] = current.getLocation().west();
        faces[4] = current.getLocation().down();
        faces[5] = current.getLocation().up();

        for(BlockPos face : faces){
            Block block = world.getBlockState(face).getBlock();
            Iterator<CostPair> meshIterator = mesh.iterator();
            boolean isInMesh = false;
            if(!validBlocks.contains(block) || visited.contains(face) || obstructors.contains(world.getBlockState(face).getBlock()))
                continue;

            while(meshIterator.hasNext()){
                CostPair cost = meshIterator.next();
                if(cost.getMedium() == block){
                    sorroundings.add(new CostPos(face, cost.getValue(), current));
                    isInMesh = true;
                }
            }
            if(!isInMesh)
                sorroundings.add(new CostPos(face, 1000, current));

        }
        return sorroundings;
    }

    private List<NetworkPos> trackeback(CostPos current, BlockPos start){
        List<NetworkPos> traceback = new LinkedList<>();
        Direction direction = null;
        Direction lastDirection = null;



        while(current != null && current.getLocation() != start){
            BlockPos parentPos = current.getLocation();
            CostPos pos = current.getParent();
            boolean isTurn = false;

            if(parentPos.up() == pos.getLocation())
                direction = Direction.UP;
            else if(parentPos.down() == pos.getLocation())
                direction = Direction.DOWN;
            else if(parentPos.north() == pos.getLocation())
                direction = Direction.NORTH;
            else if(parentPos.west() == pos.getLocation())
                direction = Direction.WEST;
            else if(parentPos.south() == pos.getLocation())
                direction = Direction.SOUTH;
            else if(parentPos.east() == pos.getLocation())
                direction = Direction.EAST;

            if(lastDirection != direction)
                isTurn = true;

            traceback.add(new NetworkPos(pos.getLocation(), isTurn));
            lastDirection = direction;
            current = pos;
        }

        return traceback;
    }
}
