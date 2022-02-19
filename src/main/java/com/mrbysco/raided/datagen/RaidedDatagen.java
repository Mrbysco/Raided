package com.mrbysco.raided.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mrbysco.raided.Raided;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RaidedDatagen {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();

		if (event.includeServer()) {
			generator.addProvider(new Loots(generator));
		}
		if (event.includeClient()) {
			generator.addProvider(new Language(generator));
			generator.addProvider(new ItemModels(generator, helper));
		}
	}

	private static class Loots extends LootTableProvider {
		public Loots(DataGenerator gen) {
			super(gen);
		}

		@Override
		protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootContextParamSet>> getTables() {
			return ImmutableList.of(
					Pair.of(RaidedLootTables::new, LootContextParamSets.ENTITY)
			);
		}

		public static class RaidedLootTables extends EntityLoot {

			@Override
			protected void addTables() {
				this.add(RaidedRegistry.INQUISITOR.get(), LootTable.lootTable());
				this.add(RaidedRegistry.INCINERATOR.get(), LootTable.lootTable());
			}

			@Override
			protected Iterable<EntityType<?>> getKnownEntities() {
				Stream<EntityType<?>> entityTypeStream = RaidedRegistry.ENTITIES.getEntries().stream().map(RegistryObject::get);
				return entityTypeStream::iterator;
			}
		}

		@Override
		protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationContext) {
			map.forEach((name, table) -> LootTables.validate(validationContext, name, table));
		}
	}

	private static class Language extends LanguageProvider {
		public Language(DataGenerator gen) {
			super(gen, Raided.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			addEntityType(RaidedRegistry.INQUISITOR, "Inquisitor");
			addEntityType(RaidedRegistry.INCINERATOR, "Incinerator");

			addItem(RaidedRegistry.INQUISITOR_SPAWN_EGG, "Inquisitor Spawn Egg");
			addItem(RaidedRegistry.INCINERATOR_SPAWN_EGG, "Incinerator Spawn Egg");
		}

		public void addSubtitle(RegistryObject<SoundEvent> sound, String name) {
			String path = Raided.MOD_ID + ".subtitle." + sound.getId().getPath();
			this.add(path, name);
		}
	}

	private static class SoundProvider extends SoundDefinitionsProvider{
		public SoundProvider(DataGenerator generator, ExistingFileHelper helper) {
			super(generator, Raided.MOD_ID, helper);
		}

		@Override
		public void registerSounds() {

		}

		public ResourceLocation modLoc(String name) {
			return new ResourceLocation(Raided.MOD_ID, name);
		}
	}

	private static class ItemModels extends ItemModelProvider {
		public ItemModels(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Raided.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {
			for(RegistryObject<Item> item : RaidedRegistry.ITEMS.getEntries()) {
				if(item.get() instanceof SpawnEggItem) {
					withExistingParent(item.get().getRegistryName().getPath(), new ResourceLocation("item/template_spawn_egg"));
				}
			}
		}
	}
}
