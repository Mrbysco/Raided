package com.mrbysco.raided.entity.projectiles;

import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.network.NetworkHooks;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.network.PlayMessages;

import javax.annotation.Nullable;
import java.util.UUID;

public class LightningProjectile extends AbstractHurtingProjectile {
	@Nullable
	private UUID targetId;

	public LightningProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
		super(entityType, level);
	}

	public LightningProjectile(Level level, LivingEntity shooter, double offsetX, double offsetY, double offsetZ) {
		super(RaidedRegistry.LIGHTNING_PROJECTILE.get(), shooter, offsetX, offsetY, offsetZ, level);
		this.moveTo(shooter.getX(), shooter.getY() + 2, shooter.getZ(), this.getYRot(), this.getXRot());
	}

	public LightningProjectile(Level level, double x, double y, double z, double offsetX, double offsetY, double offsetZ) {
		super(RaidedRegistry.LIGHTNING_PROJECTILE.get(), x, y, z, offsetX, offsetY, offsetZ, level);
	}

	public LightningProjectile(PlayMessages.SpawnEntity spawnEntity, Level level) {
		this(RaidedRegistry.LIGHTNING_PROJECTILE.get(), level);
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.ELECTRIC_SPARK;
	}

	public void setTarget(@Nullable UUID targetId) {
		this.targetId = targetId;
	}

	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		if (this.targetId != null) {
			tag.putUUID("Target", this.targetId);
		}
	}

	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.hasUUID("Target")) {
			this.targetId = tag.getUUID("Target");
		}
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level().isClientSide) {
			this.discard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		Entity entity = hitResult.getEntity();
		if (this.targetId != null && entity.getUUID().equals(this.targetId)) {
			convertEntity(entity);
		} else {
			entity.hurt(damageSources().lightningBolt(), 2.0F);
		}
	}

	private void convertEntity(Entity entity) {
		if (!this.level().isClientSide) {
			if (entity instanceof Creeper creeper) {
				if (creeper.isAlive() && !creeper.isPowered()) {
					LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
					bolt.setDamage(0);
					creeper.thunderHit((ServerLevel) this.level(), bolt);
				}
			} else if (entity instanceof Pig pig) {
				if (pig.isAlive()) {
					LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
					bolt.setDamage(0);
					pig.thunderHit((ServerLevel) this.level(), bolt);
				}
			} else if (entity instanceof AbstractVillager abstractVillager && !(abstractVillager instanceof WanderingTrader)) {
				if (abstractVillager.isAlive()) {
					ServerLevel level = (ServerLevel) this.level();
					if (this.level().getDifficulty() != Difficulty.PEACEFUL && EventHooks.canLivingConvert(abstractVillager, EntityType.WITCH, (timer) -> {
					})) {
						Witch witch = EntityType.WITCH.create(level);
						witch.moveTo(abstractVillager.getX(), abstractVillager.getY(), abstractVillager.getZ(), abstractVillager.getYRot(), abstractVillager.getXRot());
						witch.finalizeSpawn(level, level.getCurrentDifficultyAt(witch.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData) null, (CompoundTag) null);
						witch.setNoAi(abstractVillager.isNoAi());
						if (abstractVillager.hasCustomName()) {
							witch.setCustomName(abstractVillager.getCustomName());
							witch.setCustomNameVisible(abstractVillager.isCustomNameVisible());
						}

						witch.setPersistenceRequired();
						EventHooks.onLivingConvert(abstractVillager, witch);
						level.addFreshEntityWithPassengers(witch);
						if (abstractVillager instanceof Villager villager) {
							villager.releaseAllPois();
						}
						abstractVillager.discard();
					}
				}
			}
		}
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		return !(entity instanceof Raider) && super.canCollideWith(entity);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return (Packet<ClientGamePacketListener>) NetworkHooks.getEntitySpawningPacket(this);
	}
}