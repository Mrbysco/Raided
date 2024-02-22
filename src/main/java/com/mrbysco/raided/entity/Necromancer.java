package com.mrbysco.raided.entity;

import com.mrbysco.raided.entity.goal.WalkToRaiderGoal;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;

public class Necromancer extends AbstractIllager {
	private static final EntityDataAccessor<Boolean> HEALING = SynchedEntityData.defineId(Necromancer.class, EntityDataSerializers.BOOLEAN);

	protected int healingTickCount;

	public Necromancer(EntityType<? extends AbstractIllager> entityType, Level level) {
		super(entityType, level);
	}

	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6D, 1.0D));
		this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
		this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
		this.goalSelector.addGoal(4, new WalkToRaiderGoal(this, 1.0D, 4.0F, 8.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, (double) 0.25F)
				.add(Attributes.FOLLOW_RANGE, 32.0D)
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(HEALING, false);
	}

	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.healingTickCount = tag.getInt("HealingTicks");
	}

	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("HealingTicks", this.healingTickCount);
	}

	public boolean isHealing() {
		if (this.level.isClientSide) {
			return this.entityData.get(HEALING);
		} else {
			return this.healingTickCount > 0;
		}
	}

	public void setIsHealing(boolean value) {
		this.entityData.set(HEALING, value);
	}

	@Override
	public IllagerArmPose getArmPose() {
		if (this.isHealing()) {
			return IllagerArmPose.SPELLCASTING;
		} else {
			return this.isCelebrating() ? IllagerArmPose.CELEBRATING : IllagerArmPose.CROSSED;
		}
	}

	public boolean isAlliedTo(Entity entity) {
		if (super.isAlliedTo(entity)) {
			return true;
		} else if (entity instanceof LivingEntity && ((LivingEntity) entity).getMobType() == MobType.ILLAGER) {
			return this.getTeam() == null && entity.getTeam() == null;
		} else {
			return false;
		}
	}

	@Override
	public void applyRaidBuffs(int wave, boolean unused) {

	}

	protected void customServerAiStep() {
		super.customServerAiStep();
		if (this.healingTickCount > 0) {
			--this.healingTickCount;
		} else {
			this.setIsHealing(false);
		}
	}

	public boolean canHeal() {
		return this.healingTickCount == 0;
	}

	public void healMember(Raider raider) {
		float halfHealth = raider.getMaxHealth() / 2;
		raider.heal(halfHealth);
		this.hurt(DamageSource.MAGIC, (float) Math.ceil((halfHealth / 5)));
		this.setIsHealing(true);
		this.healingTickCount = 30;
	}

	@Override
	public boolean canBeLeader() {
		return false;
	}

	protected SoundEvent getAmbientSound() {
		return RaidedRegistry.NECROMANCER.getAmbient();
	}

	protected SoundEvent getDeathSound() {
		return RaidedRegistry.NECROMANCER.getDeath();
	}

	protected SoundEvent getHurtSound(DamageSource p_33306_) {
		return RaidedRegistry.NECROMANCER.getHurt();
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return RaidedRegistry.NECROMANCER.getCelebrate();
	}
}
