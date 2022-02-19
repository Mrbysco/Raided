package com.mrbysco.raided.registry;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.entity.Incinerator;
import com.mrbysco.raided.entity.Inquisitor;
import com.mrbysco.raided.entity.projectiles.IncineratorFireball;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RaidedRegistry {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Raided.MOD_ID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Raided.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Raided.MOD_ID);

	public static final RegistryObject<EntityType<Inquisitor>> INQUISITOR = ENTITIES.register("inquisitor",
			() -> register("inquisitor", EntityType.Builder.<Inquisitor>of(Inquisitor::new, MobCategory.MONSTER)
					.sized(0.6F, 1.95F).clientTrackingRange(8)));

	public static final RegistryObject<EntityType<Incinerator>> INCINERATOR = ENTITIES.register("incinerator",
			() -> register("incinerator", EntityType.Builder.<Incinerator>of(Incinerator::new, MobCategory.MONSTER)
					.sized(1.4F, 2.2F).fireImmune().clientTrackingRange(8)));

	public static final RegistryObject<EntityType<IncineratorFireball>> INCINERATOR_FIREBALL = ENTITIES.register("incinerator_fireball",
			() -> register("incinerator_fireball", EntityType.Builder.<IncineratorFireball>of(IncineratorFireball::new, MobCategory.MISC)
					.sized(0.3125F, 0.3125F).clientTrackingRange(4).updateInterval(10)
					.setCustomClientFactory(IncineratorFireball::new)));


	public static final RegistryObject<SoundEvent> INQUISITOR_CELEBRATE = SOUND_EVENTS.register("entity.inquisitor.celebrate", () ->
			new SoundEvent(new ResourceLocation(Raided.MOD_ID, "entity.inquisitor.celebrate")));
	public static final RegistryObject<SoundEvent> INCINERATOR_CELEBRATE = SOUND_EVENTS.register("entity.incinerator.celebrate", () ->
			new SoundEvent(new ResourceLocation(Raided.MOD_ID, "entity.inquisitor.incinerator")));

	public static final RegistryObject<Item> INQUISITOR_SPAWN_EGG = ITEMS.register("inquisitor_spawn_egg", () -> new ForgeSpawnEggItem(INQUISITOR::get, 0x959b9b, 0x3f3b37, itemBuilder()));
	public static final RegistryObject<Item> INCINERATOR_SPAWN_EGG = ITEMS.register("incinerator_spawn_egg", () -> new ForgeSpawnEggItem(INCINERATOR::get, 0x959b9b, 0x3f3b37, itemBuilder()));

	private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> builder) {
		return builder.build(id);
	}

	private static Item.Properties itemBuilder() {
		return new Item.Properties().tab(CreativeModeTab.TAB_MISC);
	}
}
