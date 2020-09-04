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
        return pathfind(start, goal, mesh, new LinkedList<>(), obstructors);
    }

    @Override
    public LinkedList<NetworkPos> getPathExcept(BlockPos start, BlockPos goal, Set<CostPair> mesh, LinkedList<BlockPos> exceptions, Set<Block> obstructors) {
        return pathfind(start, goal, mesh, exceptions, obstructors);
    }

    @Override
    public ReasonPair getTrueShortestPath(BlockPos start, BlockPos goal, Set<CostPair> mesh, Set<Block> obstructors) {
        return pathfindThorougly(start, goal, mesh, new LinkedList<>(), obstructors);
    }

    @Override
    public List<ReasonPair> getPaths(BlockPos start, BlockPos goal, Set<CostPair> mesh, LinkedList<BlockPos> exceptions, Set<Block> obstructors) {
        ReasonPair pair;
        List<ReasonPair> paths = new LinkedList<>();

        match: {
            while (true) {
                pair = pathfindThorougly(start, goal, mesh, exceptions, obstructors);

                if (pair == null)
                    break;

                for (ReasonPair path : paths) {
                    if(path.getPath().containsAll(pair.getPath()))
                        break match;
                }

                for(NetworkPos pos : pair.getPath())
                    exceptions.add(pos.blockPos());

                if(pair.getObstructions() == null || pair.getObstructions().size() == 0)
                    break match;

                paths.add(pair);
            }
        }

        return paths;
    }

    @Override
    public List<ReasonPair> getAllPaths(BlockPos start, BlockPos goal, Set<CostPair> mesh, Set<Block> obstructors) {
        ReasonPair pair;
        List<ReasonPair> paths = new LinkedList<>();
        LinkedList<BlockPos> exceptions = new LinkedList<>();

        match: {
            while (true) {
                pair = pathfindThorougly(start, goal, mesh, exceptions, obstructors);

                if (pair == null)
                    break;

                for (ReasonPair path : paths) {
                    if(path.getPath().containsAll(pair.getPath()))
                        break match;
                }

                for(NetworkPos pos : pair.getPath())
                    exceptions.add(pos.blockPos());

                paths.add(pair);
            }
        }

        return  paths;
    }

    /**
     *  Could these four methods be merged into two?
     *  Yes.
     *
     *  Am I too lazy to merge them?
     *  But of course!
     */

    private LinkedList<NetworkPos> pathfind(BlockPos start, BlockPos goal, Set<CostPair> mesh, LinkedList<BlockPos> exceptions, Set<Block> obstructors){
        paths.offer(new CostPos(start, Short.MAX_VALUE));
        boolean success = false;
        CostPos pos = null;
        Set<BlockPos> visited = new HashSet<>();
        Set<BlockPos> forbidden = new HashSet<>(exceptions);
        visited.add(start);

        while(!paths.isEmpty()) {
            pos = paths.poll();
            if (pos.getLocation() == goal) {
                success = true;
                break;
            }
            List<CostPos> next = look(pos, mesh, visited, obstructors, forbidden);

            for (CostPos costPos : next) {
                int startDistance = costPos.getLocation().getManhattanDistance(new Vec3i(start.getX(), start.getY(), start.getZ()));
                int goalDistance = costPos.getLocation().getManhattanDistance(new Vec3i(goal.getX(), goal.getY(), goal.getZ()));

                if(costPos.getValue() != Short.MIN_VALUE)
                    costPos.setValue((costPos.getValue() + startDistance + goalDistance));

                visited.add(costPos.getLocation());
                paths.offer(costPos);
            }
        }

        if(!success)
            return null;

        return trackeback(pos, start);
    }

    private ReasonPair pathfindThorougly(BlockPos start, BlockPos goal, Set<CostPair> mesh, LinkedList<BlockPos> exceptions, Set<Block> obstructors){
        paths.offer(new CostPos(start, Short.MAX_VALUE));
        boolean success = false;
        CostPos pos = null;
        Set<BlockPos> visited = new HashSet<>();
        Set<BlockPos> forbidden = new HashSet<>(exceptions);
        List<BlockPos> obstructions  = new LinkedList<>();
        List<CostPos> next;
        visited.add(start);

        while(!paths.isEmpty()) {
            pos = paths.poll();
            if (pos.getLocation() == goal) {
                success = true;
                break;
            }
            if(obstructors.size() > 0)
                next = lookThoroughly(pos, mesh, visited, obstructors, forbidden);
            else
                next = look(pos, mesh, visited, obstructors, forbidden);

            for (CostPos costPos : next) {
                int startDistance = costPos.getLocation().getManhattanDistance(new Vec3i(start.getX(), start.getY(), start.getZ()));
                int goalDistance = costPos.getLocation().getManhattanDistance(new Vec3i(goal.getX(), goal.getY(), goal.getZ()));

                if(costPos.getValue() != Short.MIN_VALUE)
                    costPos.setValue((costPos.getValue() + startDistance + goalDistance));

                if(costPos.hasObstruction())
                    obstructions.add(costPos.getLocation());

                visited.add(costPos.getLocation());
                paths.offer(costPos);
            }
        }

        if(!success)
            return null;

        return new ReasonPair(obstructions, trackeback(pos, start));
    }

    private List<CostPos> lookThoroughly(CostPos current, Set<CostPair> mesh, Set<BlockPos> visited, Set<Block> obstructors, Set<BlockPos> forbidden){
        List<CostPos> sorroundings = new LinkedList<>();
        Set<Block> validBlocks = new HashSet<>(Registry.getInstance().getGraphBlocks(network.getClass()));
        boolean isInMesh;
        boolean isObstruction;
        BlockPos face;
        World world = network.getWorld();

        for(Direction direction : Direction.values()){
            face = current.getLocation().offset(direction);
            Block block = world.getBlockState(face).getBlock();
            Iterator<CostPair> meshIterator = mesh.iterator();
            isInMesh = false;
            isObstruction = false;

            if(!validBlocks.contains(block) || visited.contains(face))
                continue;

            if(obstructors.contains(block))
                isObstruction = true;

            while(meshIterator.hasNext()){
                CostPair cost = meshIterator.next();
                if(forbidden.contains(face)){
                    sorroundings.add(new CostPos(face, Short.MIN_VALUE, current, isObstruction));
                    isInMesh = true;
                }
                if(cost.getMedium() == block){
                    sorroundings.add(new CostPos(face, cost.getValue(), current, isObstruction));
                    isInMesh = true;
                }
            }
            if(!isInMesh)
                sorroundings.add(new CostPos(face, (short) 1000, current, isObstruction));

        }
        return sorroundings;
    }

    private List<CostPos> look(CostPos current, Set<CostPair> mesh, Set<BlockPos> visited, Set<Block> obstructors, Set<BlockPos> forbidden){
        List<CostPos> sorroundings = new LinkedList<>();
        Set<Block> validBlocks = new HashSet<>(Registry.getInstance().getGraphBlocks(network.getClass()));
        BlockPos[] faces = new BlockPos[6];
        boolean isInMesh;
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
            isInMesh = false;

            if(!validBlocks.contains(block) || visited.contains(face) || obstructors.contains(block))
                continue;

            while(meshIterator.hasNext()){
                CostPair cost = meshIterator.next();
                if(forbidden.contains(face)){
                    sorroundings.add(new CostPos(face, Short.MIN_VALUE, current));
                    isInMesh = true;
                }
                if(cost.getMedium() == block){
                    sorroundings.add(new CostPos(face, cost.getValue(), current));
                    isInMesh = true;
                }
            }
            if(!isInMesh)
                sorroundings.add(new CostPos(face, (short) 1000, current));

        }
        return sorroundings;
    }

    private LinkedList<NetworkPos> trackeback(CostPos current, BlockPos start){
        LinkedList<NetworkPos> traceback = new LinkedList<>();
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
