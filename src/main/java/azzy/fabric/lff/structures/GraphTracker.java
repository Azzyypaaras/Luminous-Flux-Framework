package azzy.fabric.lff.structures;

import net.minecraft.server.MinecraftServer;

import java.util.*;
import java.util.concurrent.*;


public class GraphTracker<T extends NodeGraph<?, ?>> extends Thread {

//    private final HashMap<String, T> networks;
//    private static final SplittableRandom RANDOM = new SplittableRandom();
//    private Thread parent;
//    private final Executor executor;
//    private final LinkedBlockingQueue<Runnable> networkSchedulerQueue = new LinkedBlockingQueue<>();
//    public static volatile GraphTracker INSTANCE;
//
//    private GraphTracker(){
//        networks = new HashMap<>();
//        executor = new ThreadPoolExecutor(8, 8, 0L, TimeUnit.MILLISECONDS, networkSchedulerQueue);
//        INSTANCE = this;
//    }
//
//    public static void init(Thread main){
//        new GraphTracker<>();
//        INSTANCE.parent = main;
//        INSTANCE.setName("LFF Transaction Thread");
//        INSTANCE.setDaemon(true);
//        INSTANCE.start();
//    }
//
//    public static SplittableRandom getRandom() {
//        return RANDOM;
//    }
//
//    public String createNetwork(T graphType){
//        String id = generateNetworkId();
//        networks.put(id, (T) graphType.create());
//        return id;
//    }
//
//    public void deleteNetwork(String id){
//        if(networks.get(id).validateDeletion())
//            networks.remove(id);
//    }
//
//    public void forceDeleteNetwork(String id){
//        networks.remove(id);
//    }
//
//    public boolean mergeNetworks(String idSurvivor, String idSacrifice){
//        T graphA = networks.get(idSurvivor);
//        T graphB = networks.get(idSacrifice);
//        if(graphA == null || graphB == null)
//            return false;
//
//        graphB.nodeStream().forEach(e -> ((PrimitiveNode) e).setNetwork(graphA));
//        forceDeleteNetwork(idSacrifice);
//
//        return true;
//    }
//
//    public void splitNetwork(String id){
//    }
//
//    private String generateNetworkId(){
//        String key;
//
//        while (true){
//            key = RANDOM.ints(48, 123).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(16).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
//            if(!networks.containsKey(key))
//                return key;
//        }
//    }
//
//    @Override
//    public void run() {
//        LFLog.info(INSTANCE.getName() + " has been successfully initiated!");
//        while(parent.isAlive()){
//            if(System.currentTimeMillis() % 50 == 0){
//                networks.values().forEach(executor::execute);
//                if(networkSchedulerQueue.size() > 16 * networks.size()){
//                    LFLog.error("NETWORK UPDATES ARE "+networkSchedulerQueue.size()+" CYCLES BEHIND, ATTEMPTING TO CATCH UP");
//                    networkSchedulerQueue.clear();
//                }
//            }
//        }
//    }
}
