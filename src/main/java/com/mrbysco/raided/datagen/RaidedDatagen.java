package com.mrbysco.raided.datagen;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.datagen.client.RaidedItemModelsProvider;
import com.mrbysco.raided.datagen.client.RaidedLanguageProvider;
import com.mrbysco.raided.datagen.client.RaidedSoundProvider;
import com.mrbysco.raided.datagen.server.RaidedEntityTypeTagsProvider;
import com.mrbysco.raided.datagen.server.RaidedLootProvider;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

@EventBusSubscriber
public class RaidedDatagen {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		event.createReloadableRegistryObjects(
				new RegistrySetBuilder()
						.add(Registries.LOOT_TABLE, RaidedLootProvider.create()),
				Set.of(Raided.MOD_ID));

		event.createProvider(RaidedEntityTypeTagsProvider::new);
		event.createProvider(RaidedLanguageProvider::new);
		event.createProvider(RaidedItemModelsProvider::new);
		event.createProvider(RaidedSoundProvider::new);
	}
}
