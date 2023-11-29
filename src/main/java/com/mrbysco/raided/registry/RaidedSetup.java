package com.mrbysco.raided.registry;

import com.mrbysco.raided.config.RaidedConfig;
import com.mrbysco.raided.entity.Electromancer;
import com.mrbysco.raided.entity.Incinerator;
import com.mrbysco.raided.entity.Inquisitor;
import com.mrbysco.raided.entity.Necromancer;
import com.mrbysco.raided.entity.Savager;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

import java.util.List;

public class RaidedSetup {
	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(RaidedRegistry.INQUISITOR.getEntityType(), Inquisitor.createAttributes().build());
		event.put(RaidedRegistry.INCINERATOR.getEntityType(), Incinerator.createAttributes().build());
		event.put(RaidedRegistry.SAVAGER.getEntityType(), Savager.createAttributes().build());
		event.put(RaidedRegistry.NECROMANCER.getEntityType(), Necromancer.createAttributes().build());
		event.put(RaidedRegistry.ELECTROMANCER.getEntityType(), Electromancer.createAttributes().build());
	}

	public static void initializeRaiderTypes() {
		if (RaidedConfig.COMMON.spawnInquisitor.get())
			registerRaiderTypes(RaidedRegistry.INQUISITOR.getEntityType(), RaidedConfig.COMMON.inquisitorSpawnsPerWave.get());
		if (RaidedConfig.COMMON.spawnIncinerator.get())
			registerRaiderTypes(RaidedRegistry.INCINERATOR.getEntityType(), RaidedConfig.COMMON.incineratorSpawnsPerWave.get());
		if (RaidedConfig.COMMON.spawnSavager.get())
			registerRaiderTypes(RaidedRegistry.SAVAGER.getEntityType(), RaidedConfig.COMMON.savagerSpawnsPerWave.get());
		if (RaidedConfig.COMMON.spawnNecromancer.get())
			registerRaiderTypes(RaidedRegistry.NECROMANCER.getEntityType(), RaidedConfig.COMMON.necromancerSpawnsPerWave.get());
		if (RaidedConfig.COMMON.spawnElectromancer.get())
			registerRaiderTypes(RaidedRegistry.ELECTROMANCER.getEntityType(), RaidedConfig.COMMON.electromancerSpawnsPerWave.get());
	}

	public static void registerRaiderTypes(EntityType<? extends Raider> type, List<? extends Integer> listValue) {
		int[] intArray = new int[9];
		for (int i = 0; i < listValue.size(); i++) {
			intArray[i] = listValue.get(i);
		}

		ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(type);
		if (key != null) {
			Raid.RaiderType.create(key.toString(), type, intArray);
		}
	}
}
