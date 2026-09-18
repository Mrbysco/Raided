package com.mrbysco.raided.datagen.server;

import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class RaidedLootProvider {

	public static SingleRegistryBootstrap<LootTable> create() {
		return new LootTableProvider(
				BuiltInLootTables.all(),
				List.of(
						new LootTableProvider.SubProviderEntry(RaidedLootTables::new, LootContextParamSets.ENTITY)
				)
		);
	}

	public static class RaidedLootTables extends EntityLootSubProvider {
		protected RaidedLootTables(LootTableSubProvider.Context context) {
			super(FeatureFlags.REGISTRY.allFlags(), context);
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
}
