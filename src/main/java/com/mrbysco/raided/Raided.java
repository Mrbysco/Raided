package com.mrbysco.raided;

import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.config.RaidedConfig;
import com.mrbysco.raided.handler.EventHandler;
import com.mrbysco.raided.registry.RaidedRegistry;
import com.mrbysco.raided.registry.RaidedSetup;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod(Raided.MOD_ID)
public class Raided {
	public static final String MOD_ID = "raided";
	public static final Logger LOGGER = LogManager.getLogger();

	public Raided() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, RaidedConfig.commonSpec);
		eventBus.register(RaidedConfig.class);

		eventBus.addListener(this::setup);
		eventBus.addListener(this::addTabContents);

		RaidedRegistry.ITEMS.register(eventBus);
		RaidedRegistry.ENTITY_TYPES.register(eventBus);
		RaidedRegistry.SOUND_EVENTS.register(eventBus);

		MinecraftForge.EVENT_BUS.register(new EventHandler());

		eventBus.addListener(RaidedSetup::registerEntityAttributes);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::registerEntityRenders);
			eventBus.addListener(ClientHandler::registerLayerDefinitions);
		});
	}

	private void setup(final FMLCommonSetupEvent event) {
		RaidedSetup.initializeRaiderTypes();
	}

	private void addTabContents(final CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
			List<ItemStack> stacks = RaidedRegistry.ITEMS.getEntries().stream().filter(reg -> reg.get() instanceof SpawnEggItem).map(reg -> new ItemStack(reg.get())).toList();
			event.acceptAll(stacks);
		}
	}
}
