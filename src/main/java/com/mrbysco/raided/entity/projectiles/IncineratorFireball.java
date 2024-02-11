package com.mrbysco.raided.entity.projectiles;

import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class IncineratorFireball extends SmallFireball {
	public IncineratorFireball(EntityType<? extends IncineratorFireball> entityType, Level level) {
		super(entityType, level);
	}

	public IncineratorFireball(Level level, LivingEntity shooter, double accelX, double accelY, double accelZ) {
		super(level, shooter, accelX, accelY, accelZ);
	}

	@Override
	public EntityType<?> getType() {
		return RaidedRegistry.INCINERATOR_FIREBALL.get();
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.SMOKE;
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		if (!this.level().isClientSide) {
			Entity entity = entityHitResult.getEntity();
			if (!(entity instanceof Raider) && !entity.fireImmune()) {
				Entity owner = this.getOwner();
				int i = entity.getRemainingFireTicks();
				entity.setSecondsOnFire(4);
				boolean flag = entity.hurt(damageSources().fireball(this, owner), 5.0F);
				if (!flag) {
					entity.setRemainingFireTicks(i);
				} else if (owner instanceof LivingEntity) {
					this.doEnchantDamageEffects((LivingEntity) owner, entity);
				}
			}
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		return false;
	}
}
