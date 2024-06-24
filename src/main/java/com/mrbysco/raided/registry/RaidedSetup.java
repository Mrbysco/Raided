package com.mrbysco.raided.registry;

import com.mrbysco.raided.entity.Electromancer;
import com.mrbysco.raided.entity.Incinerator;
import com.mrbysco.raided.entity.Inquisitor;
import com.mrbysco.raided.entity.Necromancer;
import com.mrbysco.raided.entity.Savager;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

public class RaidedSetup {
	public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
		event.put(RaidedRegistry.INQUISITOR.getEntityType(), Inquisitor.createAttributes().build());
		event.put(RaidedRegistry.INCINERATOR.getEntityType(), Incinerator.createAttributes().build());
		event.put(RaidedRegistry.SAVAGER.getEntityType(), Savager.createAttributes().build());
		event.put(RaidedRegistry.NECROMANCER.getEntityType(), Necromancer.createAttributes().build());
		event.put(RaidedRegistry.ELECTROMANCER.getEntityType(), Electromancer.createAttributes().build());
	}
}
