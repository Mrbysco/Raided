package com.mrbysco.raided.entity;

import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class Inquisitor extends AbstractIllager {
	private static final EntityDataAccessor<Integer> INQUISITOR_TYPE = SynchedEntityData.defineId(Inquisitor.class, EntityDataSerializers.INT);

	public Inquisitor(EntityType<? extends AbstractIllager> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(2, new Raider.HoldGroundAttackGoal(this, 10.0F));
		this.goalSelector.addGoal(3, new Inquisitor.InquisitorMeleeAttackGoal(this));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.75D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 15.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(INQUISITOR_TYPE, 0);
	}

	public int getInquisitorType() {
		return this.entityData.get(INQUISITOR_TYPE);
	}

	public void setInquisitorType(int index) {
		this.entityData.set(INQUISITOR_TYPE, index);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, (double) 0.25F)
				.add(Attributes.FOLLOW_RANGE, 32.0D)
				.add(Attributes.MAX_HEALTH, 20.0D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("InquisitorType", this.getInquisitorType());
	}

	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setInquisitorType(tag.getInt("InquisitorType"));
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
	public boolean hurt(DamageSource source, float amount) {
		if (hasShield()) {
			Entity entity = source.getDirectEntity();
			return !(entity instanceof Projectile) ? super.hurt(source, amount) : false;
		}
		return super.hurt(source, amount);
	}

	private boolean hasShield() {
		ItemStack itemstack = this.getItemBySlot(EquipmentSlot.OFFHAND);
		return itemstack.getItem() instanceof ShieldItem;
	}

	@Override
	public IllagerArmPose getArmPose() {
		if (this.isAggressive()) {
			return AbstractIllager.IllagerArmPose.ATTACKING;
		} else {
			return this.isCelebrating() ? AbstractIllager.IllagerArmPose.CELEBRATING : AbstractIllager.IllagerArmPose.CROSSED;
		}
	}

	@Override
	public void applyRaidBuffs(int wave, boolean unused) {
		if (random.nextFloat() < 0.25F) {
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SHIELD));
		}
	}

	@Override
	public boolean canBeLeader() {
		return false;
	}

	protected SoundEvent getAmbientSound() {
		return RaidedRegistry.INQUISITOR.getAmbient();
	}

	protected SoundEvent getDeathSound() {
		return RaidedRegistry.INQUISITOR.getDeath();
	}

	protected SoundEvent getHurtSound(DamageSource p_33306_) {
		return RaidedRegistry.INQUISITOR.getHurt();
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return RaidedRegistry.INQUISITOR.getCelebrate();
	}

	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, MobSpawnType spawnType,
										@Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
		SpawnGroupData spawngroupdata = super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, groupData, tag);
		((GroundPathNavigation) this.getNavigation()).setCanOpenDoors(true);

		this.setInquisitorType(random.nextInt(3) + 1);

		this.populateDefaultEquipmentSlots(difficultyInstance);
		this.populateDefaultEquipmentEnchantments(difficultyInstance);
		this.setCanPickUpLoot(random.nextFloat() < 0.55F * difficultyInstance.getSpecialMultiplier());

		return spawngroupdata;
	}

	static class InquisitorMeleeAttackGoal extends MeleeAttackGoal {
		public InquisitorMeleeAttackGoal(Inquisitor inquisitor) {
			super(inquisitor, 1.0D, false);
		}

		protected double getAttackReachSqr(LivingEntity livingEntity) {
			if (this.mob.getVehicle() instanceof Ravager) {
				float f = this.mob.getVehicle().getBbWidth() - 0.1F;
				return (double) (f * 2.0F * f * 2.0F + livingEntity.getBbWidth());
			} else {
				return super.getAttackReachSqr(livingEntity);
			}
		}
	}
}
