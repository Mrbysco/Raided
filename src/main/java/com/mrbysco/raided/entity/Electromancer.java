package com.mrbysco.raided.entity;

import com.mrbysco.raided.entity.goal.WalkToRaiderGoal;
import com.mrbysco.raided.entity.projectiles.LightningProjectile;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Electromancer extends SpellcasterIllager {
	@Nullable
	private AbstractVillager witchificationTarget;

	@Nullable
	private Creeper creeperConversionTarget;

	@Nullable
	private Pig pigConversionTarget;

	public Electromancer(EntityType<? extends SpellcasterIllager> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new Electromancer.CastingSpellGoal());
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Player.class, 8.0F, 0.6D, 1.0D));
		this.goalSelector.addGoal(2, new RaiderOpenDoorGoal(this));
		this.goalSelector.addGoal(3, new HoldGroundAttackGoal(this, 10.0F));
		this.goalSelector.addGoal(4, new WalkToRaiderGoal(this, 1.0D, 4.0F, 8.0F));
		this.goalSelector.addGoal(6, new Electromancer.CreeperConversionGoal());
		this.goalSelector.addGoal(7, new Electromancer.PigConversionGoal());
		this.goalSelector.addGoal(8, new Electromancer.WitchificationSpellGoal());
		this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
		this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
		this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
		this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.MOVEMENT_SPEED, (double) 0.3125D)
				.add(Attributes.FOLLOW_RANGE, 12.0D)
				.add(Attributes.MAX_HEALTH, 22.0D)
				.add(Attributes.ATTACK_DAMAGE, 2.0D);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
	}

	@Override
	public IllagerArmPose getArmPose() {
		if (this.isCastingSpell()) {
			return IllagerArmPose.SPELLCASTING;
		} else {
			return this.isCelebrating() ? IllagerArmPose.CELEBRATING : IllagerArmPose.CROSSED;
		}
	}

	void setPigConversionTarget(@Nullable Pig pig) {
		this.pigConversionTarget = pig;
	}

	@Nullable
	Pig getPigConversionTarget() {
		return this.pigConversionTarget;
	}

	void setCreeperConversionTarget(@Nullable Creeper creeper) {
		this.creeperConversionTarget = creeper;
	}

	@Nullable
	Creeper getCreeperConversionTarget() {
		return this.creeperConversionTarget;
	}

	void setWitchificationTarget(@Nullable AbstractVillager villager) {
		this.witchificationTarget = villager;
	}

	@Nullable
	AbstractVillager getWitchificationTarget() {
		return this.witchificationTarget;
	}

	@Override
	public void applyRaidBuffs(ServerLevel serverLevel, int wave, boolean unused) {

	}

	@Override
	protected SoundEvent getAmbientSound() {
		return RaidedRegistry.ELECTROMANCER.getAmbient();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return RaidedRegistry.ELECTROMANCER.getDeath();
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource p_33306_) {
		return RaidedRegistry.ELECTROMANCER.getHurt();
	}

	@Override
	protected SoundEvent getCastingSoundEvent() {
		return RaidedRegistry.ELECTROMANCER.getCasting();
	}

	@Override
	public SoundEvent getCelebrateSound() {
		return RaidedRegistry.ELECTROMANCER.getCelebrate();
	}

	class CastingSpellGoal extends SpellcasterIllager.SpellcasterCastingSpellGoal {
		public void tick() {
			if (Electromancer.this.getTarget() != null) {
				Electromancer.this.getLookControl().setLookAt(Electromancer.this.getTarget(), (float) Electromancer.this.getMaxHeadYRot(), (float) Electromancer.this.getMaxHeadXRot());
			} else if (Electromancer.this.getWitchificationTarget() != null) {
				Electromancer.this.getLookControl().setLookAt(Electromancer.this.getWitchificationTarget(), (float) Electromancer.this.getMaxHeadYRot(), (float) Electromancer.this.getMaxHeadXRot());
			} else if (Electromancer.this.getCreeperConversionTarget() != null) {
				Electromancer.this.getLookControl().setLookAt(Electromancer.this.getCreeperConversionTarget(), (float) Electromancer.this.getMaxHeadYRot(), (float) Electromancer.this.getMaxHeadXRot());
			}

		}
	}

	public abstract class BoltUseSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
		protected void createBoltEntity(LivingEntity target) {
			LightningProjectile lightningProjectile = new LightningProjectile(Electromancer.this.level(), Electromancer.this, Vec3.ZERO);
			double d1 = target.getX() - Electromancer.this.getX();
			double d2 = target.getY(0.5D) - (Electromancer.this.getY(0.5D) + 2);
			double d3 = target.getZ() - Electromancer.this.getZ();
			double d4 = Math.sqrt(d1 * d1 + d3 * d3) * (double) 0.2F;
			lightningProjectile.shoot(d1, d2 + d4, d3, 1.6F, 0);
			lightningProjectile.setTarget(target.getUUID());
			Electromancer.this.level().addFreshEntity(lightningProjectile);
		}
	}

	public class CreeperConversionGoal extends BoltUseSpellGoal {
		private final TargetingConditions conversionTargeting = TargetingConditions.forNonCombat().range(16.0D).selector((livingEntity, level) -> {
			return !((Creeper) livingEntity).isPowered();
		});

		public boolean canUse() {
			if (Electromancer.this.getTarget() != null) {
				return false;
			} else if (Electromancer.this.isCastingSpell()) {
				return false;
			} else if (Electromancer.this.tickCount < this.nextAttackTickCount) {
				return false;
			} else if (!EventHooks.canEntityGrief(getServerLevel(Electromancer.this.level()), Electromancer.this)) {
				return false;
			} else {
				List<Creeper> list = getServerLevel(Electromancer.this.level()).getNearbyEntities(Creeper.class, this.conversionTargeting, Electromancer.this, Electromancer.this.getBoundingBox().inflate(16.0D, 4.0D, 16.0D));
				if (list.isEmpty()) {
					return false;
				} else {
					Electromancer.this.setCreeperConversionTarget(list.get(Electromancer.this.random.nextInt(list.size())));
					return true;
				}
			}
		}

		public boolean canContinueToUse() {
			return Electromancer.this.getCreeperConversionTarget() != null && this.attackWarmupDelay > 0;
		}

		public void stop() {
			super.stop();
			Electromancer.this.setCreeperConversionTarget((Creeper) null);
		}

		protected void performSpellCasting() {
			Creeper creeper = Electromancer.this.getCreeperConversionTarget();
			if (creeper != null && creeper.isAlive()) {
				createBoltEntity(creeper);
			}
		}

		protected int getCastWarmupTime() {
			return 80;
		}

		protected int getCastingTime() {
			return 80;
		}

		protected int getCastingInterval() {
			return 240;
		}

		protected SoundEvent getSpellPrepareSound() {
			return RaidedRegistry.ELECROMANCER_PREPARE_CONVERSION.get();
		}

		protected SpellcasterIllager.IllagerSpell getSpell() {
			return SpellcasterIllager.IllagerSpell.WOLOLO;
		}
	}

	public class PigConversionGoal extends BoltUseSpellGoal {
		private final TargetingConditions conversionTargeting = TargetingConditions.forNonCombat().range(16.0D).selector((livingEntity, level) -> {
			return ((Pig) livingEntity).isAlive();
		});

		public boolean canUse() {
			if (Electromancer.this.getTarget() != null) {
				return false;
			} else if (Electromancer.this.isCastingSpell()) {
				return false;
			} else if (Electromancer.this.tickCount < this.nextAttackTickCount) {
				return false;
			} else if (!EventHooks.canEntityGrief(getServerLevel(Electromancer.this.level()), Electromancer.this)) {
				return false;
			} else {
				List<Pig> list = getServerLevel(Electromancer.this.level()).getNearbyEntities(Pig.class, this.conversionTargeting, Electromancer.this, Electromancer.this.getBoundingBox().inflate(16.0D, 4.0D, 16.0D));
				if (list.isEmpty()) {
					return false;
				} else {
					Electromancer.this.setPigConversionTarget(list.get(Electromancer.this.random.nextInt(list.size())));
					return true;
				}
			}
		}

		public boolean canContinueToUse() {
			return Electromancer.this.getPigConversionTarget() != null && this.attackWarmupDelay > 0;
		}

		public void stop() {
			super.stop();
			Electromancer.this.setPigConversionTarget((Pig) null);
		}

		protected void performSpellCasting() {
			Pig pig = Electromancer.this.getPigConversionTarget();
			if (pig != null && pig.isAlive()) {
				createBoltEntity(pig);
			}
		}

		protected int getCastWarmupTime() {
			return 120;
		}

		protected int getCastingTime() {
			return 120;
		}

		protected int getCastingInterval() {
			return 320;
		}

		protected SoundEvent getSpellPrepareSound() {
			return RaidedRegistry.ELECROMANCER_PREPARE_CONVERSION.get();
		}

		protected SpellcasterIllager.IllagerSpell getSpell() {
			return SpellcasterIllager.IllagerSpell.WOLOLO;
		}
	}

	public class WitchificationSpellGoal extends BoltUseSpellGoal {
		private final TargetingConditions conversionTargeting = TargetingConditions.forNonCombat().range(16.0D).selector((livingEntity, level) -> {
			return !(livingEntity instanceof WanderingTrader);
		});

		public boolean canUse() {
			if (Electromancer.this.getTarget() != null) {
				return false;
			} else if (Electromancer.this.isCastingSpell()) {
				return false;
			} else if (Electromancer.this.tickCount < this.nextAttackTickCount) {
				return false;
			} else if (!EventHooks.canEntityGrief(getServerLevel(Electromancer.this.level()), Electromancer.this)) {
				return false;
			} else {
				List<AbstractVillager> list = getServerLevel(Electromancer.this.level()).getNearbyEntities(AbstractVillager.class, this.conversionTargeting, Electromancer.this, Electromancer.this.getBoundingBox().inflate(16.0D, 4.0D, 16.0D));
				if (list.isEmpty()) {
					return false;
				} else {
					Electromancer.this.setWitchificationTarget(list.get(Electromancer.this.random.nextInt(list.size())));
					return true;
				}
			}
		}

		public boolean canContinueToUse() {
			return Electromancer.this.getWitchificationTarget() != null && this.attackWarmupDelay > 0;
		}

		public void stop() {
			super.stop();
			Electromancer.this.setWitchificationTarget((AbstractVillager) null);
		}

		protected void performSpellCasting() {
			AbstractVillager abstractVillager = Electromancer.this.getWitchificationTarget();
			if (!Electromancer.this.level().isClientSide && abstractVillager != null && abstractVillager.isAlive()) {
				createBoltEntity(abstractVillager);
			}
		}

		protected int getCastWarmupTime() {
			return 240;
		}

		protected int getCastingTime() {
			return 240;
		}

		protected int getCastingInterval() {
			return 512;
		}

		protected SoundEvent getSpellPrepareSound() {
			return RaidedRegistry.ELECROMANCER_PREPARE_CONVERSION.get();
		}

		protected SpellcasterIllager.IllagerSpell getSpell() {
			return SpellcasterIllager.IllagerSpell.WOLOLO;
		}
	}
}
