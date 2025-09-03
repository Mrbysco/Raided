package com.mrbysco.raided.registry;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.entity.Electromancer;
import com.mrbysco.raided.entity.Incinerator;
import com.mrbysco.raided.entity.Inquisitor;
import com.mrbysco.raided.entity.Necromancer;
import com.mrbysco.raided.entity.Savager;
import com.mrbysco.raided.entity.projectiles.IncineratorFireball;
import com.mrbysco.raided.entity.projectiles.LightningProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RaidedRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Raided.MOD_ID);
	public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(Raided.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Raided.MOD_ID);

	public static final RaidRegHelper<Inquisitor> INQUISITOR = new RaidRegHelper<>("inquisitor",
			Inquisitor::new, MobCategory.MONSTER, (builder) -> builder
			.sized(0.6F, 1.95F).clientTrackingRange(8));

	public static final RaidRegHelper<Incinerator> INCINERATOR = new RaidRegHelper<>("incinerator",
			Incinerator::new, MobCategory.MONSTER, (builder) -> builder
			.sized(1.4F, 2.2F).fireImmune().clientTrackingRange(8));

	public static final Supplier<EntityType<IncineratorFireball>> INCINERATOR_FIREBALL = ENTITIES.registerEntityType("incinerator_fireball",
			IncineratorFireball::new, MobCategory.MISC, (builder) ->
					builder.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10).noLootTable());

	public static final RaidRegHelper<Savager> SAVAGER = new RaidRegHelper<>("savager",
			Savager::new, MobCategory.MONSTER, (builder) -> builder
			.sized(0.6F, 0.85F).clientTrackingRange(10));

	public static final RaidRegHelper<Necromancer> NECROMANCER = new RaidRegHelper<>("necromancer",
			Necromancer::new, MobCategory.MONSTER, (builder) -> builder
			.sized(0.6F, 1.95F).clientTrackingRange(8));

	public static final RaidRegHelper<Electromancer> ELECTROMANCER = new RaidRegHelper<>("electromancer",
			Electromancer::new, MobCategory.MONSTER, (builder) -> builder
			.sized(0.6F, 1.95F).clientTrackingRange(8), true);

	public static final Supplier<EntityType<LightningProjectile>> LIGHTNING_PROJECTILE = ENTITIES.registerEntityType("lightning",
			LightningProjectile::new, MobCategory.MISC, (builder) -> builder
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10).noLootTable());

	public static final DeferredHolder<SoundEvent, SoundEvent> ELECROMANCER_PREPARE_CONVERSION = SOUND_EVENTS.register("entity.electromancer.prepare_conversion", () ->
			SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "entity.electromancer.prepare_conversion")));

}
