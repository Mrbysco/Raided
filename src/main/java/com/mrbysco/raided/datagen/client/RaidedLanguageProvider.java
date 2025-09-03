package com.mrbysco.raided.datagen.client;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.registry.RaidRegHelper;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.jetbrains.annotations.Nullable;

public class RaidedLanguageProvider extends LanguageProvider {
	public RaidedLanguageProvider(PackOutput packOutput) {
		super(packOutput, Raided.MOD_ID, "en_us");
	}

	@Override
	protected void addTranslations() {
		addHelper(RaidedRegistry.INQUISITOR, "Inquisitor");
		addHelper(RaidedRegistry.INCINERATOR, "Incinerator");
		addHelper(RaidedRegistry.SAVAGER, "Savager");
		addHelper(RaidedRegistry.NECROMANCER, "Necromancer");
		addHelper(RaidedRegistry.ELECTROMANCER, "Electromancer");

		addSubtitle(RaidedRegistry.ELECROMANCER_PREPARE_CONVERSION.get(), "Electromancer prepares conversion");

		addConfig("General", "General", "General settings");
		addConfig("inquisitorSpawnsPerWave", "Inquisitor Spawns Per Wave", "The spawns per wave for the Inquisitor before bonus spawns are applied (Must always have 8 values!)");
		addConfig("incineratorSpawnsPerWave", "Incinerator Spawns Per Wave", "The spawns per wave for the Incinerator before bonus spawns are applied (Must always have 8 values!)");
		addConfig("savagerSpawnsPerWave", "Savager Spawns Per Wave", "The spawns per wave for the Savager before bonus spawns are applied (Must always have 8 values!)");
		addConfig("necromancerSpawnsPerWave", "Necromancer Spawns Per Wave", "The spawns per wave for the Necromancer before bonus spawns are applied (Must always have 8 values!)");
		addConfig("electromancerSpawnsPerWave", "Electromancer Spawns Per Wave", "The spawns per wave for the Electromancer before bonus spawns are applied (Must always have 8 values!)");
	}

	private void addHelper(RaidRegHelper<?> helper, String name) {
		add(helper.getEntityType(), name);
		addItem(helper.getSpawnEgg(), name + " Spawn Egg");
		addSubtitle(helper.getAmbient(), name + " mutters");
		addSubtitle(helper.getDeath(), name + " dies");
		addSubtitle(helper.getHurt(), name + " hurts");
		addSubtitle(helper.getCelebrate(), name + " cheers");
		if (helper.getCasting() != null)
			addSubtitle(helper.getCasting(), name + " casts spell");
	}

	/**
	 * Add a subtitle to a sound event
	 *
	 * @param sound The sound event registry object
	 * @param text  The subtitle text
	 */
	public void addSubtitle(SoundEvent sound, String text) {
		String path = Raided.MOD_ID + ".subtitle." + sound.getLocation().getPath();
		this.add(path, text);
	}

	/**
	 * Add the translation for a config entry
	 *
	 * @param path        The path of the config entry
	 * @param name        The name of the config entry
	 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
	 */
	private void addConfig(String path, String name, @Nullable String description) {
		this.add(Raided.MOD_ID + ".configuration." + path, name);
		if (description != null && !description.isEmpty())
			this.add(Raided.MOD_ID + ".configuration." + path + ".tooltip", description);
	}
}
