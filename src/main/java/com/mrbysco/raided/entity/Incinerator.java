package com.mrbysco.raided.entity;

import com.mrbysco.raided.entity.projectiles.IncineratorFireball;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
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

public class Incinerator extends AbstractIllager implements RangedAttackMob {
	private static final EntityDataAccessor<Boolean> THROWING_DATA = SynchedEntityData.defineId(Incinerator.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> WEAPON_TYPE_DATA = SynchedEntityData.defineId(Incinerator.class, EntityDataSerializers.INT);

	private boolean animationPlay;
	private int animationTimer;
	private int switchHealth;

	private final RangedAttackGoal rangedAttackGoal = new RangedAttackGoal(this, 1.0D, 60, 15.0F);
	private final MeleeAttackGoal incineratorMeleeGoal = new IncineratorMeleeAttackGoal(this);

	public Incinerator(EntityType<? extends AbstractIllager> entityType, Level level) {
		super(entityType, level);

		switchHealth = 0;
		animationPlay = false;
		animationTimer = 0;
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(2, new HoldGroundAttackGoal(this, 10.0F));
		this.goalSelector.addGoal(3, new RangedAttackGoal(this, 1.0D, 20, 40, 8.0F));
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.75D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 15.0F));
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(THROWING_DATA, false);
		builder.define(WEAPON_TYPE_DATA, 0);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, (double) 0.275F)
				.add(Attributes.MAX_HEALTH, 30.0D)
				.add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 1.0D)
				.add(Attributes.ARMOR, 2.0D)
				.add(Attributes.FOLLOW_RANGE, 32.0D)
				.add(Attributes.STEP_HEIGHT, 1.0F);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("WeaponType", getWeaponType());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains("WeaponType")) {
			int typeID = tag.getInt("WeaponType");
			setWeaponType(typeID);
		}
	}

	public int getWeaponType() {
		return entityData.get(WEAPON_TYPE_DATA);
	}

	private void setWeaponType(int type) {
		entityData.set(WEAPON_TYPE_DATA, type);
	}

	public boolean isThrowing() {
		return entityData.get(THROWING_DATA);
	}

	private void setThrowing(boolean throwing) {
		entityData.set(THROWING_DATA, throwing);
	}

	@Override
	public void performRangedAttack(LivingEntity target, float distanceFactor) {
		this.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F / (random.nextFloat() * 0.4F + 0.8F));
		double d0 = target.getX() - getX();
		double d1 = target.getBoundingBox().minY + target.getBbHeight() / 2.0D - (getY() + getBbHeight() / 2.0D);
		double d2 = target.getZ() - getZ();
		double f1 = Mth.sqrt(distanceFactor) * 0.5D;

		for (int i = 0; i < 3; ++i) {
			IncineratorFireball fireball = new IncineratorFireball(this.level(), this, d0 + random.nextGaussian() * f1, d1, d2 + random.nextGaussian() * f1);
			fireball.setPos(fireball.getX(), fireball.getY() + getBbHeight() / 2.0D + 0.5D, fireball.getZ());
			this.level().addFreshEntity(fireball);
		}

		setThrowing(true);
		animationPlay = true;
		animationTimer = 0;
	}

	@Override
	public boolean doHurtTarget(Entity entity) {
		if (super.doHurtTarget(entity)) {
			entity.setRemainingFireTicks(20 * 4);
		}
		return true;
	}

	@Override
	public boolean isAlliedTo(Entity entity) {
		if (super.isAlliedTo(entity)) {
			return true;
		} else if (entity instanceof LivingEntity && ((LivingEntity) entity).getType().is(EntityTypeTags.ILLAGER)) {
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
			return IllagerArmPose.ATTACKING;
		} else {
			return this.isCelebrating() ? IllagerArmPose.CELEBRATING : IllagerArmPose.CROSSED;
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

	@Override
	protected SoundEvent getAmbientSound() {
		return RaidedRegistry.INCINERATOR.getAmbient();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return RaidedRegistry.INCINERATOR.getDeath();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_33306_) {
		return RaidedRegistry.INCINERATOR.getHurt();
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return RaidedRegistry.INCINERATOR.getCelebrate();
	}

	@Override
	@Nullable
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, MobSpawnType spawnType,
	                                    @Nullable SpawnGroupData groupData) {
		groupData = super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, groupData);

		this.setCanPickUpLoot(random.nextFloat() < 0.55F * difficultyInstance.getSpecialMultiplier());
		setCombatGoal();

		return groupData;
	}

	private void setCombatGoal() {
		ItemStack itemstack = getMainHandItem();
		if (itemstack.isEmpty()) {
			goalSelector.removeGoal(incineratorMeleeGoal);
			goalSelector.addGoal(1, rangedAttackGoal);

			this.setThrowing(false);
			animationPlay = false;
			animationTimer = 0;
		} else {
			goalSelector.removeGoal(rangedAttackGoal);
			goalSelector.addGoal(1, incineratorMeleeGoal);
		}
	}

	@Override
	public void tick() {
		if ((getHealth() < getMaxHealth() * 0.75F) && (switchHealth == 0)) {
			if (getWeaponType() == 0) {
				if (random.nextInt(4) == 0) {
					setWeaponType(2);
				} else {
					setWeaponType(1);
				}
			}

			setCombatGoal();
			switchHealth = 1;
		}

		if ((getHealth() > getMaxHealth() * 0.75F) && (switchHealth == 1)) {
			setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			setCombatGoal();
			switchHealth = 0;
		}

		if (animationPlay) {
			if (animationTimer != 20) {
				animationTimer += 1;
			} else {
				setCombatGoal();
				animationPlay = false;
			}
		}

		super.tick();
	}

	static class IncineratorMeleeAttackGoal extends MeleeAttackGoal {
		public IncineratorMeleeAttackGoal(Incinerator inquisitor) {
			super(inquisitor, 1.0D, false);
		}
	}
}
