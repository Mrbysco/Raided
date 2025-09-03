package com.mrbysco.raided.datagen.server;

import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RaidedLootProvider extends LootTableProvider {
	public RaidedLootProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(packOutput, Set.of(), List.of(
				new SubProviderEntry(RaidedLootTables::new, LootContextParamSets.ENTITY)
		), lookupProvider);
	}

	public static class RaidedLootTables extends EntityLootSubProvider {
		protected RaidedLootTables(HolderLookup.Provider provider) {
			super(FeatureFlags.REGISTRY.allFlags(), provider);
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
			return RaidedRegistry.ENTITIES.getEntries().stream().map(Supplier::get);
		}
	}

	@Override
	protected void validate(WritableRegistry<LootTable> writableregistry, ValidationContext validationcontext, ProblemReporter.Collector problemreporter$collector) {
		super.validate(writableregistry, validationcontext, problemreporter$collector);
	}
}
