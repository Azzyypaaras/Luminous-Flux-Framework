package azzy.fabric.lff.structures;

import com.google.common.collect.HashBiMap;

public class EdgeMap<T extends PrimitiveNode<V, T, K>, K extends PrimitiveEdge<V, T, K>, V extends NodeGraph<T, K, V>> {

    private final HashBiMap<T, K> mapT;
    private final HashBiMap<T, K> mapK;

    public EdgeMap(){
        mapT = HashBiMap.create(32);
        mapK = HashBiMap.create(32);
    }

    public boolean put(T nodeA, T nodeB, K edge){
        if(!checkExistance(nodeA, nodeB)){
            mapT.put(nodeA, edge);
            mapK.put(nodeB, edge);
            return true;
        }
        return false;
    }

    public K getEdge(T node){
        K edge = mapT.get(node);
        return edge != null ? edge : mapK.get(node);
    }

    public T getNode(T node){
        T otherNode = mapT.inverse().get(mapK.get(node));
        return otherNode != null ? otherNode : mapK.inverse().get(mapT.get(node));
    }

    private boolean checkExistance(T nodeA, T nodeB){
        return (mapT.containsKey(nodeA) && mapK.containsKey(nodeB)) || (mapT.containsKey(nodeB) && mapK.containsKey(nodeA));
    }
}
