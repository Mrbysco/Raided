package com.mrbysco.raided.registry;

import com.mrbysco.raided.Raided;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.UnaryOperator;

public class RaidRegHelper<T extends Raider> {
	protected final String name;
	protected final DeferredHolder<EntityType<?>, EntityType<T>> entityType;
	protected final DeferredItem<SpawnEggItem> spawnEgg;

	protected DeferredHolder<SoundEvent, SoundEvent> AMBIENT;
	protected DeferredHolder<SoundEvent, SoundEvent> DEATH;
	protected DeferredHolder<SoundEvent, SoundEvent> HURT;
	protected DeferredHolder<SoundEvent, SoundEvent> CELEBRATE;
	protected DeferredHolder<SoundEvent, SoundEvent> CASTING;

	/**
	 * @return The registry name of the raider
	 */
	@NotNull
	public String getName() {
		return name;
	}

	/**
	 * @return The entity type registry object of the raider.
	 */
	public EntityType<? extends T> getEntityType() {
		return entityType.get();
	}

	/**
	 * @return The spawn egg item registry object of the raider.
	 */
	public DeferredItem<SpawnEggItem> getSpawnEgg() {
		return spawnEgg;
	}

	/**
	 * @return The Ambient SoundEvent of the raider.
	 */
	public SoundEvent getAmbient() {
		return AMBIENT == null ? null : AMBIENT.get();
	}

	/**
	 * @return The Death SoundEvent of the raider.
	 */
	public SoundEvent getDeath() {
		return DEATH == null ? null : DEATH.get();
	}

	/**
	 * @return The Hurt SoundEvent of the raider.
	 */
	public SoundEvent getHurt() {
		return HURT == null ? null : HURT.get();
	}

	/**
	 * @return The Celebrate SoundEvent of the raider.
	 */
	public SoundEvent getCelebrate() {
		return CELEBRATE == null ? null : CELEBRATE.get();
	}

	/**
	 * @return The Celebrate SoundEvent of the raider.
	 */
	public SoundEvent getCasting() {
		return CASTING == null ? null : CASTING.get();
	}

	public RaidRegHelper(String name, EntityType.EntityFactory<T> entityFactory, MobCategory mobCategory, UnaryOperator<EntityType.Builder<T>> builder, boolean casting) {
		this.name = name;
		this.entityType = RaidedRegistry.ENTITIES.registerEntityType(name, entityFactory, mobCategory, builder);
		this.spawnEgg = RaidedRegistry.ITEMS.registerItem(name + "_spawn_egg", (properties) -> new SpawnEggItem(this.entityType.get(), properties));

		this.AMBIENT = RaidedRegistry.SOUND_EVENTS.register("entity." + name + ".ambient", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "entity." + name + ".ambient")));
		this.DEATH = RaidedRegistry.SOUND_EVENTS.register("entity." + name + ".death", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "entity." + name + ".death")));
		this.HURT = RaidedRegistry.SOUND_EVENTS.register("entity." + name + ".hurt", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "entity." + name + ".hurt")));
		this.CELEBRATE = RaidedRegistry.SOUND_EVENTS.register("entity." + name + ".celebrate", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "entity." + name + ".celebrate")));
		if (casting) {
			this.CASTING = RaidedRegistry.SOUND_EVENTS.register("entity." + name + ".casting", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "entity." + name + ".casting")));
		}
	}

	public RaidRegHelper(String name, EntityType.EntityFactory<T> entityFactory, MobCategory mobCategory, UnaryOperator<EntityType.Builder<T>> builder) {
		this(name, entityFactory, mobCategory, builder, false);
	}
}
