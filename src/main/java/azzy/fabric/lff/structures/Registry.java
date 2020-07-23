package azzy.fabric.lff.structures;

import azzy.fabric.lff.base.BlockBasedGraph;
import azzy.fabric.lff.base.BlockEdge;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;

import java.util.*;

public class Registry<T extends BoundNode<V, T>, K extends Block & NetworkBlock, V extends BlockBasedGraph<T, ? extends BlockEdge<T>>> {
    private static final Registry<?, ?, ?> INSTANCE = new Registry<>();
    private final HashMap<Class<T>, List<K>> nodeBlockRegistry = new HashMap<>();
    private final HashMap<Class<V>, List<T>> networkNodeRegistry = new HashMap<>();

    private Registry(){
    }

    @SuppressWarnings("unchecked")
    public static <T extends BoundNode<V, T>, K extends Block & NetworkBlock, V extends BlockBasedGraph<T, ? extends BlockEdge<T>>> Registry<T, K, V> getInstance() {
        return (Registry<T, K, V>) INSTANCE;
    }

    public void registerNode(Class<T> node, K[] blocks){
        nodeBlockRegistry.put(node, Arrays.asList(blocks));
    }

    public void registerGraph(Class<V> graph, T[] nodes){
        networkNodeRegistry.put(graph, Arrays.asList(nodes));
    }

    public List<K> get(T node){
        return nodeBlockRegistry.get(node.getClass());
    }

    public List<T> get(V graph){
        return networkNodeRegistry.get(graph.getClass());
    }

    public List<Block> getGraphBlocks(Class<? extends BlockBasedGraph> graph){
        Set<Block> blocks = new HashSet<>();
        for(T node : networkNodeRegistry.get(graph)){
            blocks.addAll(nodeBlockRegistry.get(node.getClass()));
        }
        return new ArrayList<>(blocks);
    }
}
