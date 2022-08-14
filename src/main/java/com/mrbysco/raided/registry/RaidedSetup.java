package com.mrbysco.raided.registry;

import com.mrbysco.raided.config.RaidedConfig;
import com.mrbysco.raided.entity.Incinerator;
import com.mrbysco.raided.entity.Inquisitor;
import com.mrbysco.raided.entity.Savager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class RaidedSetup {
	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(RaidedRegistry.INQUISITOR.get(), Inquisitor.createAttributes().build());
		event.put(RaidedRegistry.INCINERATOR.get(), Incinerator.createAttributes().build());
		event.put(RaidedRegistry.SAVAGER.get(), Savager.createAttributes().build());
	}

	public static void initializeRaiderTypes() {
		if (RaidedConfig.COMMON.spawnInquisitor.get())
			registerRaiderTypes(RaidedRegistry.INQUISITOR.get(), RaidedConfig.COMMON.inquisitorSpawnsPerWave.get());
		if (RaidedConfig.COMMON.spawnIncinerator.get())
			registerRaiderTypes(RaidedRegistry.INCINERATOR.get(), RaidedConfig.COMMON.incineratorSpawnsPerWave.get());
		if (RaidedConfig.COMMON.spawnSavager.get())
			registerRaiderTypes(RaidedRegistry.SAVAGER.get(), RaidedConfig.COMMON.savagerSpawnsPerWave.get());
	}

	public static void registerRaiderTypes(EntityType<? extends Raider> type, List<? extends Integer> listValue) {
		int[] intArray = new int[9];
		for (int i = 0; i < listValue.size(); i++) {
			intArray[i] = listValue.get(i);
		}

		Raid.RaiderType.create(ForgeRegistries.ENTITIES.getKey(type).toString(), type, intArray);
	}
}
