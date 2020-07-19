package azzy.fabric.lff;


import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.RunArgs;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import net.minecraft.util.registry.Registry;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class LuminousFlux implements ModInitializer {
	public static final String MOD_ID = "luminousflux";

	public static final Logger LFLog = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
	}
}
