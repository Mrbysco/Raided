package com.mrbysco.raided.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mrbysco.raided.Raided;
import com.mrbysco.raided.registry.RaidRegHelper;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
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
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

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
			generator.addProvider(event.includeServer(), new Loots(generator));
			generator.addProvider(event.includeServer(), new EntityTags(generator, helper));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new Language(generator));
			generator.addProvider(event.includeClient(), new ItemModels(generator, helper));
			generator.addProvider(event.includeClient(), new SoundProvider(generator, helper));
		}
	}

	private static class EntityTags extends EntityTypeTagsProvider {

		public EntityTags(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
			super(generator, Raided.MOD_ID, existingFileHelper);
		}

		@Override
		protected void addTags() {
			this.tag(EntityTypeTags.RAIDERS).add(
					RaidedRegistry.INQUISITOR.getEntityType(),
					RaidedRegistry.INCINERATOR.getEntityType(),
					RaidedRegistry.SAVAGER.getEntityType(),
					RaidedRegistry.NECROMANCER.getEntityType(),
					RaidedRegistry.ELECTROMANCER.getEntityType()
			);
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
				this.add(RaidedRegistry.INQUISITOR.getEntityType(), LootTable.lootTable());
				this.add(RaidedRegistry.INCINERATOR.getEntityType(), LootTable.lootTable());
				this.add(RaidedRegistry.SAVAGER.getEntityType(), LootTable.lootTable());
				this.add(RaidedRegistry.NECROMANCER.getEntityType(), LootTable.lootTable());
				this.add(RaidedRegistry.ELECTROMANCER.getEntityType(), LootTable.lootTable());
			}

			@Override
			protected Iterable<EntityType<?>> getKnownEntities() {
				Stream<EntityType<?>> entityTypeStream = RaidedRegistry.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
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
			addHelper(RaidedRegistry.INQUISITOR, "Inquisitor");
			addHelper(RaidedRegistry.INCINERATOR, "Incinerator");
			addHelper(RaidedRegistry.SAVAGER, "Savager");
			addHelper(RaidedRegistry.NECROMANCER, "Necromancer");
			addHelper(RaidedRegistry.ELECTROMANCER, "Electromancer");

			addSubtitle(RaidedRegistry.ELECROMANCER_PREPARE_CONVERSION.get(), "Electromancer prepares conversion");
		}

		private void addHelper(RaidRegHelper helper, String name) {
			add(helper.getEntityType(), name);
			addItem(helper.getSpawnEgg(), name + " Spawn Egg");
			addSubtitle(helper.getAmbient(), name + " mutters");
			addSubtitle(helper.getDeath(), name + " dies");
			addSubtitle(helper.getHurt(), name + " hurts");
			addSubtitle(helper.getCelebrate(), name + " cheers");
			if (helper.getCasting() != null)
				addSubtitle(helper.getCasting(), name + " casts spell");
		}

		public void addSubtitle(SoundEvent sound, String name) {
			String path = Raided.MOD_ID + ".subtitle." + sound.getLocation().getPath();
			this.add(path, name);
		}
	}

	private static class SoundProvider extends SoundDefinitionsProvider {
		public SoundProvider(DataGenerator generator, ExistingFileHelper helper) {
			super(generator, Raided.MOD_ID, helper);
		}

		@Override
		public void registerSounds() {
			addHelper(RaidedRegistry.INQUISITOR);
			addHelper(RaidedRegistry.INCINERATOR);
			addHelper(RaidedRegistry.SAVAGER);
			addHelper(RaidedRegistry.NECROMANCER);
			addHelper(RaidedRegistry.ELECTROMANCER);

			this.add(RaidedRegistry.ELECROMANCER_PREPARE_CONVERSION.get(), definition()
					.with(
							sound(new ResourceLocation("mob/evocation_illager/prepare_wololo")))
					.subtitle(modSubtitle(RaidedRegistry.ELECROMANCER_PREPARE_CONVERSION.getId()))
			);
		}

		private void addHelper(RaidRegHelper helper) {
			this.add(helper.getAmbient(), definition()
					.with(
							sound(new ResourceLocation("mob/evocation_illager/idle1")),
							sound(new ResourceLocation("mob/evocation_illager/idle2")),
							sound(new ResourceLocation("mob/evocation_illager/idle3")),
							sound(new ResourceLocation("mob/evocation_illager/idle4")))
					.subtitle(modSubtitle(helper.getAmbient().getLocation()))
			);
			this.add(helper.getDeath(), definition()
					.with(
							sound(new ResourceLocation("mob/evocation_illager/death1")),
							sound(new ResourceLocation("mob/evocation_illager/death2")))
					.subtitle(modSubtitle(helper.getDeath().getLocation()))
			);
			this.add(helper.getHurt(), definition()
					.with(
							sound(new ResourceLocation("mob/evocation_illager/hurt1")),
							sound(new ResourceLocation("mob/evocation_illager/hurt2")))
					.subtitle(modSubtitle(helper.getDeath().getLocation()))
			);
			this.add(helper.getCelebrate(), definition()
					.with(
							sound(new ResourceLocation("mob/evocation_illager/celebrate")),
							sound(new ResourceLocation("mob/evocation_illager/idle1")),
							sound(new ResourceLocation("mob/evocation_illager/idle2")))
					.subtitle(modSubtitle(helper.getCelebrate().getLocation()))
			);
			if (helper.getCasting() != null) {
				this.add(helper.getCasting(), definition()
						.with(
								sound(new ResourceLocation("mob/evocation_illager/cast1")),
								sound(new ResourceLocation("mob/evocation_illager/cast2")))
						.subtitle(modSubtitle(helper.getCasting().getLocation()))
				);
			}
		}

		public String modSubtitle(ResourceLocation id) {
			return Raided.MOD_ID + ".subtitle." + id.getPath();
		}
	}

	private static class ItemModels extends ItemModelProvider {
		public ItemModels(DataGenerator gen, ExistingFileHelper helper) {
			super(gen, Raided.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {
			for (RegistryObject<Item> item : RaidedRegistry.ITEMS.getEntries()) {
				if (item.get() instanceof SpawnEggItem) {
					withExistingParent(item.getId().getPath(), new ResourceLocation("item/template_spawn_egg"));
				}
			}
		}
	}
}
