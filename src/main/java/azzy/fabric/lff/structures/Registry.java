package azzy.fabric.lff.structures;

import azzy.fabric.lff.base.BlockBasedGraph;
import azzy.fabric.lff.base.BlockEdge;
import azzy.fabric.lff.base.PathingNode;
import net.minecraft.block.AbstractBlock;

import java.util.*;

public class Registry<T extends BoundNode, K extends AbstractBlock & NetworkBlock, V extends BlockBasedGraph<BoundNode<BlockBasedGraph, PathingNode, BlockEdge<PathingNode>>>> {

    private final HashMap<Class, List<K>> NODEBLOCKREGISTRY = new HashMap<>();
    private final HashMap<Class, List<T>> NETWORKNODEREGISTRY = new HashMap<>();
    public static Registry REGISTRY;

    private Registry(){
    }

    public static void init(){
        if(REGISTRY != null)
            throw(new IllegalStateException("The Registry is a singleton ya neet!"));

        REGISTRY = new Registry<>();
    }

    public void registerNode(Class<T> node, K[] blocks){
        NODEBLOCKREGISTRY.put(node, Arrays.asList(blocks));
    }

    public void registerGraph(Class<V> graph, T[] nodes){
        NETWORKNODEREGISTRY.put(graph, Arrays.asList(nodes));
    }

    public List<K> get(T node){
        return NODEBLOCKREGISTRY.get(node.getClass());
    }

    public List<T> get(V graph){
        return NETWORKNODEREGISTRY.get(graph.getClass());
    }

    public List<K> getGraphBlocks(Class<V> graph){
        Set<K> blocks = new HashSet<>();
        for(T node : NETWORKNODEREGISTRY.get(graph)){
            blocks.addAll(NODEBLOCKREGISTRY.get(node.getClass()));
        }
        return (List<K>) blocks;
    }

}
