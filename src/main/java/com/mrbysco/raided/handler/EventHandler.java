package com.mrbysco.raided.handler;

import com.mrbysco.raided.entity.Necromancer;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

import java.util.List;

public class EventHandler {

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event) {
		final Level level = event.getEntity().level();
		if (!level.isClientSide && event.getEntity() instanceof Raider raider && raider.getType() != RaidedRegistry.NECROMANCER.getEntityType()) {
			if (level instanceof ServerLevel serverLevel) {
				List<Necromancer> necromancers = serverLevel.getNearbyEntities(Necromancer.class, TargetingConditions.forNonCombat(), raider,
						raider.getBoundingBox().inflate(10.0D, 8.0D, 10.0D));
				necromancers.removeIf(necromancer -> necromancer.canHeal() && necromancer.getCurrentRaid() != null && raider.getCurrentRaid() != null &&
						raider.getCurrentRaid() == necromancer.getCurrentRaid());
				if (!necromancers.isEmpty()) {
					necromancers.getFirst().healMember(raider);
					event.setCanceled(true);
				}
			}
		}
	}
}
