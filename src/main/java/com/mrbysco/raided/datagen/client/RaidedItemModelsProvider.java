package com.mrbysco.raided.datagen.client;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class RaidedItemModelsProvider extends ItemModelProvider {
	public RaidedItemModelsProvider(PackOutput packOutput, ExistingFileHelper helper) {
		super(packOutput, Raided.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
		for (DeferredHolder<Item, ? extends Item> item : RaidedRegistry.ITEMS.getEntries()) {
			if (item.get() instanceof SpawnEggItem) {
				withExistingParent(item.getId().getPath(), ResourceLocation.withDefaultNamespace("item/template_spawn_egg"));
			}
		}
	}
}
