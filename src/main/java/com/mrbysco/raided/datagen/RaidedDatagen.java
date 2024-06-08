package com.mrbysco.raided.datagen;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.registry.RaidRegHelper;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class RaidedDatagen {

	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper helper = event.getExistingFileHelper();
		PackOutput packOutput = generator.getPackOutput();

		if (event.includeServer()) {
			generator.addProvider(event.includeServer(), new Loots(packOutput, event.getLookupProvider()));
		}
		if (event.includeClient()) {
			generator.addProvider(event.includeClient(), new Language(packOutput));
			generator.addProvider(event.includeClient(), new ItemModels(packOutput, helper));
			generator.addProvider(event.includeClient(), new SoundProvider(packOutput, helper));
		}
	}

	private static class Loots extends LootTableProvider {
		public Loots(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
			super(packOutput, Set.of(), List.of(
					new SubProviderEntry(RaidedLootTables::new, LootContextParamSets.ENTITY)
			), lookupProvider);
		}

		public static class RaidedLootTables extends EntityLootSubProvider {
			protected RaidedLootTables() {
				super(FeatureFlags.REGISTRY.allFlags());
			}

			@Override
			public void generate() {
				this.add(RaidedRegistry.INQUISITOR.getEntityType(), LootTable.lootTable());
				this.add(RaidedRegistry.INCINERATOR.getEntityType(), LootTable.lootTable());
				this.add(RaidedRegistry.SAVAGER.getEntityType(), LootTable.lootTable());
				this.add(RaidedRegistry.NECROMANCER.getEntityType(), LootTable.lootTable());
				this.add(RaidedRegistry.ELECTROMANCER.getEntityType(), LootTable.lootTable());
			}

			@Override
			protected Stream<EntityType<?>> getKnownEntityTypes() {
				return RaidedRegistry.ENTITY_TYPES.getEntries().stream().map(Supplier::get);
			}
		}

		@Override
		protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
			super.validate(writableregistry, validationcontext, problemreporter$collector);
		}
	}

	private static class Language extends LanguageProvider {
		public Language(PackOutput packOutput) {
			super(packOutput, Raided.MOD_ID, "en_us");
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
		public SoundProvider(PackOutput packOutput, ExistingFileHelper helper) {
			super(packOutput, Raided.MOD_ID, helper);
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
		public ItemModels(PackOutput packOutput, ExistingFileHelper helper) {
			super(packOutput, Raided.MOD_ID, helper);
		}

		@Override
		protected void registerModels() {
			for (DeferredHolder<Item, ? extends Item> item : RaidedRegistry.ITEMS.getEntries()) {
				if (item.get() instanceof SpawnEggItem) {
					withExistingParent(item.getId().getPath(), new ResourceLocation("item/template_spawn_egg"));
				}
			}
		}
	}
}
