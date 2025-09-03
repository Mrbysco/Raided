package com.mrbysco.raided.datagen.client;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.registry.RaidRegHelper;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class RaidedSoundProvider extends SoundDefinitionsProvider {
	public RaidedSoundProvider(PackOutput packOutput) {
		super(packOutput, Raided.MOD_ID);
	}

	@Override
	public void registerSounds() {
		addHelper(RaidedRegistry.INQUISITOR);
		addHelper(RaidedRegistry.INCINERATOR);
		addHelper(RaidedRegistry.SAVAGER);
		addHelper(RaidedRegistry.NECROMANCER);
		addHelper(RaidedRegistry.ELECTROMANCER);

		this.add(RaidedRegistry.ELECROMANCER_PREPARE_CONVERSION.get(), definition()
				.with(
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/prepare_wololo")))
				.subtitle(modSubtitle(RaidedRegistry.ELECROMANCER_PREPARE_CONVERSION.getId()))
		);
	}

	private void addHelper(RaidRegHelper helper) {
		this.add(helper.getAmbient(), definition()
				.with(
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/idle1")),
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/idle2")),
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/idle3")),
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/idle4")))
				.subtitle(modSubtitle(helper.getAmbient().location()))
		);
		this.add(helper.getDeath(), definition()
				.with(
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/death1")),
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/death2")))
				.subtitle(modSubtitle(helper.getDeath().location()))
		);
		this.add(helper.getHurt(), definition()
				.with(
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/hurt1")),
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/hurt2")))
				.subtitle(modSubtitle(helper.getDeath().location()))
		);
		this.add(helper.getCelebrate(), definition()
				.with(
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/celebrate")),
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/idle1")),
						sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/idle2")))
				.subtitle(modSubtitle(helper.getCelebrate().location()))
		);
		if (helper.getCasting() != null) {
			this.add(helper.getCasting(), definition()
					.with(
							sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/cast1")),
							sound(ResourceLocation.withDefaultNamespace("mob/evocation_illager/cast2")))
					.subtitle(modSubtitle(helper.getCasting().location()))
			);
		}
	}

	public String modSubtitle(ResourceLocation id) {
		return Raided.MOD_ID + ".subtitle." + id.getPath();
	}
}
