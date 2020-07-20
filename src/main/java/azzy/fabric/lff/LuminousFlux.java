package azzy.fabric.lff;


import azzy.fabric.lff.structures.BoundNode;
import azzy.fabric.lff.structures.GraphTracker;
import azzy.fabric.lff.structures.NetworkBlock;
import azzy.fabric.lff.structures.Registry;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.util.thread.ThreadExecutor;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class LuminousFlux implements ModInitializer {

	public static final String MOD_ID = "luminousflux";
	public static final Logger LFLog = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        Registry.init();
	}
}
