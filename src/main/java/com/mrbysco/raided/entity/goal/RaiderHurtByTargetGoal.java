package com.mrbysco.raided.entity.goal;

import com.mrbysco.raided.entity.Savager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.raid.Raider;

import java.util.EnumSet;

public class RaiderHurtByTargetGoal extends TargetGoal {
	private final Savager savager;
	private LivingEntity ownerLastHurtBy;
	private Raider raider;
	private int timestamp;

	public RaiderHurtByTargetGoal(Savager savager) {
		super(savager, false);
		this.savager = savager;
		this.setFlags(EnumSet.of(Goal.Flag.TARGET));
	}

	public boolean canUse() {
		if (this.savager.tickCount % 10 != 0) {
			for (Raider raider : savager.level.getEntitiesOfClass(Raider.class, savager.getBoundingBox().inflate(4.0D), (entity) -> entity.isAlive() && !(entity instanceof Savager))) {
				this.ownerLastHurtBy = raider.getLastHurtByMob();
				int i = raider.getLastHurtByMobTimestamp();
				this.raider = raider;
				return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
			}
		}
		return false;
	}

	public void start() {
		this.mob.setTarget(this.ownerLastHurtBy);
		if (raider != null) {
			this.timestamp = raider.getLastHurtByMobTimestamp();
		}

		super.start();
	}
}