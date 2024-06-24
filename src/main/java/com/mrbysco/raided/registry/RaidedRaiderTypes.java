package com.mrbysco.raided.registry;

import com.mrbysco.raided.config.RaidedConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.List;
import java.util.function.Supplier;

public class RaidedRaiderTypes {
	public static final EnumProxy<Raid.RaiderType> INQUISITOR = new EnumProxy<>(Raid.RaiderType.class, getRaider(RaidedRegistry.INQUISITOR.getEntityType()), getWaves(RaidedConfig.COMMON.inquisitorSpawnsPerWave.get()));
	public static final EnumProxy<Raid.RaiderType> INCINERATOR = new EnumProxy<>(Raid.RaiderType.class, getRaider(RaidedRegistry.INCINERATOR.getEntityType()), getWaves(RaidedConfig.COMMON.incineratorSpawnsPerWave.get()));
	public static final EnumProxy<Raid.RaiderType> SAVAGER = new EnumProxy<>(Raid.RaiderType.class, getRaider(RaidedRegistry.SAVAGER.getEntityType()), getWaves(RaidedConfig.COMMON.savagerSpawnsPerWave.get()));
	public static final EnumProxy<Raid.RaiderType> NECROMANCER = new EnumProxy<>(Raid.RaiderType.class, getRaider(RaidedRegistry.NECROMANCER.getEntityType()), getWaves(RaidedConfig.COMMON.necromancerSpawnsPerWave.get()));
	public static final EnumProxy<Raid.RaiderType> ELECTROMANCER = new EnumProxy<>(Raid.RaiderType.class, getRaider(RaidedRegistry.ELECTROMANCER.getEntityType()), getWaves(RaidedConfig.COMMON.electromancerSpawnsPerWave.get()));

	public static Supplier<EntityType<?>> getRaider(EntityType<?> type) {
		return () -> type;
	}

	public static int[] getWaves(List<? extends Integer> listValue) {
		int[] intArray = new int[9];
		for (int i = 0; i < listValue.size(); i++) {
			intArray[i] = listValue.get(i);
		}

		return intArray;
	}
}
