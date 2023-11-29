package com.mrbysco.raided.registry;

import com.mrbysco.raided.Raided;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class RaidRegHelper<T extends Raider> {
	protected final String name;
	protected final Supplier<EntityType<? extends T>> entityType;
	protected final DeferredItem<DeferredSpawnEggItem> spawnEgg;

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
	public DeferredItem<DeferredSpawnEggItem> getSpawnEgg() {
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

	public RaidRegHelper(String name, EntityType.Builder<T> builder, int backgroundColor, int highlightColor, boolean casting) {
		this.name = name;
		this.entityType = RaidedRegistry.ENTITY_TYPES.register(name, () -> builder.build(name));
		this.spawnEgg = RaidedRegistry.ITEMS.register(name + "_spawn_egg", () -> new DeferredSpawnEggItem(this.entityType, backgroundColor, highlightColor,
				(new Item.Properties())));

		this.AMBIENT = RaidedRegistry.SOUND_EVENTS.register("entity." + name + ".ambient", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Raided.MOD_ID, "entity." + name + ".ambient")));
		this.DEATH = RaidedRegistry.SOUND_EVENTS.register("entity." + name + ".death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Raided.MOD_ID, "entity." + name + ".death")));
		this.HURT = RaidedRegistry.SOUND_EVENTS.register("entity." + name + ".hurt", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Raided.MOD_ID, "entity." + name + ".hurt")));
		this.CELEBRATE = RaidedRegistry.SOUND_EVENTS.register("entity." + name + ".celebrate", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Raided.MOD_ID, "entity." + name + ".celebrate")));
		if (casting) {
			this.CASTING = RaidedRegistry.SOUND_EVENTS.register("entity." + name + ".casting", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Raided.MOD_ID, "entity." + name + ".casting")));
		}
	}

	public RaidRegHelper(String name, EntityType.Builder<T> builder, int backgroundColor, int highlightColor) {
		this(name, builder, backgroundColor, highlightColor, false);
	}
}
