package com.mrbysco.raided;

import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.registry.RaidedRegistry;
import com.mrbysco.raided.registry.RaidedSetup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Raided.MOD_ID)
public class Raided {
    public static final String MOD_ID = "raided";
    public static final Logger LOGGER = LogManager.getLogger();

    public Raided() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        RaidedRegistry.ITEMS.register(eventBus);
        RaidedRegistry.ENTITIES.register(eventBus);
        RaidedRegistry.SOUND_EVENTS.register(eventBus);

        eventBus.addListener(RaidedSetup::registerEntityAttributes);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            eventBus.addListener(ClientHandler::registerEntityRenders);
            eventBus.addListener(ClientHandler::registerLayerDefinitions);
        });
    }
}
