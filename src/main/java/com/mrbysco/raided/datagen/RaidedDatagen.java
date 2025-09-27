package com.mrbysco.raided.datagen;

import com.mrbysco.raided.datagen.client.RaidedItemModelsProvider;
import com.mrbysco.raided.datagen.client.RaidedLanguageProvider;
import com.mrbysco.raided.datagen.client.RaidedSoundProvider;
import com.mrbysco.raided.datagen.server.RaidedEntityTypeTagsProvider;
import com.mrbysco.raided.datagen.server.RaidedLootProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber
public class RaidedDatagen {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

		generator.addProvider(true, new RaidedLootProvider(packOutput, lookupProvider));
		generator.addProvider(true, new RaidedEntityTypeTagsProvider(packOutput, lookupProvider));

		generator.addProvider(true, new RaidedLanguageProvider(packOutput));
		generator.addProvider(true, new RaidedItemModelsProvider(packOutput));
		generator.addProvider(true, new RaidedSoundProvider(packOutput));

	}
}
