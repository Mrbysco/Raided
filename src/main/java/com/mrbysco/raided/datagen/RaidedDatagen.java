package com.mrbysco.raided.datagen;

import com.mrbysco.raided.datagen.client.RaidedItemModelsProvider;
import com.mrbysco.raided.datagen.client.RaidedLanguageProvider;
import com.mrbysco.raided.datagen.client.RaidedSoundProvider;
import com.mrbysco.raided.datagen.server.RaidedLootProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class RaidedDatagen {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();
		PackOutput packOutput = generator.getPackOutput();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new RaidedLootProvider(packOutput, event.getLookupProvider()));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new RaidedLanguageProvider(packOutput));
			generator.addProvider(event.includeClient(), new RaidedItemModelsProvider(packOutput, helper));
			generator.addProvider(event.includeClient(), new RaidedSoundProvider(packOutput, helper));
		}
	}
}
