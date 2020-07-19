package azzy.fabric.lff.structures;

import java.util.HashMap;
import java.util.Random;

public class GraphTracker<T extends NodeGraph> {

    private final HashMap<String, T> networks;
    private static final Random RANDOM = new Random();
    public static volatile GraphTracker INSTANCE;

    private GraphTracker(){
        networks = new HashMap<>();
        INSTANCE = this;
    }

    public static void init(){
        new GraphTracker<>();
    }

    public static Random getRandom() {
        return RANDOM;
    }

    public String createNetwork(T graphType){
        String id = generateNetworkId();
        networks.put(id, (T) graphType.create());
        return id;
    }

    public void deleteNetwork(String id){
        if(networks.get(id).validateDeletion())
            networks.remove(id);
    }

    public void forceDeleteNetwork(String id){
        networks.remove(id);
    }

    public boolean mergeNetworks(String idSurvivor, String idSacrifice){
        T graphA = networks.get(idSurvivor);
        T graphB = networks.get(idSacrifice);
        if(graphA == null || graphB == null)
            return false;

        graphB.nodeStream().forEach(e -> ((PrimitiveNode) e).setNetwork(graphA));
        forceDeleteNetwork(idSacrifice);

        return true;
    }

    public void splitNetwork(String id){

    }

    private String generateNetworkId(){
        return RANDOM.ints(48, 123).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(32).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }
}
