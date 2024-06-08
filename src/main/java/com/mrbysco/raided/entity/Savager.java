package com.mrbysco.raided.entity;

import com.mrbysco.raided.entity.goal.RaiderHurtByTargetGoal;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class Savager extends Raider {
	private static final Predicate<LivingEntity> IS_TAMED = (livingEntity) -> livingEntity instanceof TamableAnimal tamableAnimal &&
			tamableAnimal.isAlive() && !(tamableAnimal.getOwner() instanceof Raider);

	public Savager(EntityType<? extends Raider> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
		this.targetSelector.addGoal(1, new RaiderHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, TamableAnimal.class, true, IS_TAMED));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MAX_HEALTH, 16.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.FOLLOW_RANGE, 24.0D);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
	}

	@Override
	public void applyRaidBuffs(int wave, boolean unused) {

	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, MobSpawnType spawnType,
	                                    @Nullable SpawnGroupData groupData) {
		groupData = super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, groupData);

		this.setCanPickUpLoot(false);

		return groupData;
	}

	@Override
	public boolean canBeLeader() {
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return RaidedRegistry.SAVAGER.getAmbient();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return RaidedRegistry.SAVAGER.getDeath();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_33306_) {
		return RaidedRegistry.SAVAGER.getHurt();
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return RaidedRegistry.SAVAGER.getCelebrate();
	}
}
