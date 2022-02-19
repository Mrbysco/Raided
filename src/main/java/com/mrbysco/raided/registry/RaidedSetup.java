package com.mrbysco.raided.registry;

import com.mrbysco.raided.entity.Incinerator;
import com.mrbysco.raided.entity.Inquisitor;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

public class RaidedSetup {
	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(RaidedRegistry.INQUISITOR.get(), Inquisitor.createAttributes().build());
		event.put(RaidedRegistry.INCINERATOR.get(), Incinerator.createAttributes().build());
	}
}
