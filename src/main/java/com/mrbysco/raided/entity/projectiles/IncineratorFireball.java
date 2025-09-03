package com.mrbysco.raided.entity.projectiles;

import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class IncineratorFireball extends SmallFireball {
	public IncineratorFireball(EntityType<? extends IncineratorFireball> entityType, Level level) {
		super(entityType, level);
	}

	public IncineratorFireball(Level level, LivingEntity shooter, Vec3 movement) {
		super(level, shooter, movement);
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
	protected void onHitEntity(EntityHitResult hitResult) {
		if (this.level() instanceof ServerLevel serverlevel) {
			Entity entity = hitResult.getEntity();
			if (!(entity instanceof Raider) && !entity.fireImmune()) {
				Entity owner = this.getOwner();
				int i = entity.getRemainingFireTicks();
				entity.igniteForTicks(4);
				DamageSource fireball = this.damageSources().fireball(this, owner);
				boolean flag = entity.hurtServer(serverlevel, fireball, 5.0F);
				if (!flag) {
					entity.igniteForTicks(i);
				} else if (owner instanceof LivingEntity) {
					EnchantmentHelper.doPostAttackEffects(serverlevel, entity, fireball);
				}
			}
		}
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
	}

	@Override
	public boolean canBeCollidedWith(@Nullable Entity entity) {
		return false;
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
		return false;
	}
}
