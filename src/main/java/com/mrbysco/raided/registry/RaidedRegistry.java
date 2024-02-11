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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RaidedRegistry {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Raided.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, Raided.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, Raided.MOD_ID);

	public static final RaidRegHelper<Inquisitor> INQUISITOR = new RaidRegHelper("inquisitor",
			EntityType.Builder.<Inquisitor>of(Inquisitor::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8), 0x959b9b, 0x3f3b37);

	public static final RaidRegHelper<Incinerator> INCINERATOR = new RaidRegHelper("incinerator",
			EntityType.Builder.<Incinerator>of(Incinerator::new, MobCategory.MONSTER)
					.sized(1.4F, 2.2F).fireImmune().clientTrackingRange(8), 0x959b9b, 0x3f3b37);

	public static final Supplier<EntityType<IncineratorFireball>> INCINERATOR_FIREBALL = ENTITY_TYPES.register("incinerator_fireball",
			() -> register("incinerator_fireball", EntityType.Builder.<IncineratorFireball>of(IncineratorFireball::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)));

	public static final RaidRegHelper<Savager> SAVAGER = new RaidRegHelper("savager",
			EntityType.Builder.<Savager>of(Savager::new, MobCategory.MONSTER)
					.sized(0.6F, 0.85F).clientTrackingRange(10), 0x959b9b, 0x3f3b37);

	public static final RaidRegHelper<Necromancer> NECROMANCER = new RaidRegHelper("necromancer",
			EntityType.Builder.<Necromancer>of(Necromancer::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8), 0x959b9b, 0x3f3b37);

	public static final RaidRegHelper<Electromancer> ELECTROMANCER = new RaidRegHelper("electromancer",
			EntityType.Builder.<Electromancer>of(Electromancer::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8), 0x959b9b, 0x3f3b37, true);

	public static final Supplier<EntityType<LightningProjectile>> LIGHTNING_PROJECTILE = ENTITY_TYPES.register("lightning",
			() -> register("lightning", EntityType.Builder.<LightningProjectile>of(LightningProjectile::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)));

	public static final DeferredHolder<SoundEvent, SoundEvent> ELECROMANCER_PREPARE_CONVERSION = SOUND_EVENTS.register("entity.electromancer.prepare_conversion", () ->
			SoundEvent.createVariableRangeEvent(new ResourceLocation(Raided.MOD_ID, "entity.electromancer.prepare_conversion")));

	private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return builder.build(id);
	}
}
