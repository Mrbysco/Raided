package com.mrbysco.raided.datagen.client;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;

public class RaidedItemModelsProvider extends ModelProvider {
	public RaidedItemModelsProvider(PackOutput packOutput) {
		super(packOutput, Raided.MOD_ID);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		for (DeferredHolder<Item, ? extends Item> item : RaidedRegistry.ITEMS.getEntries()) {
			if (item.get() instanceof SpawnEggItem) {
				itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
			}
		}
	}
}
