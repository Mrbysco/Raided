package com.mrbysco.raided.datagen.client;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.registry.RaidRegHelper;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.LanguageProvider;

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
	}

	private void addHelper(RaidRegHelper helper, String name) {
		add(helper.getEntityType(), name);
		addItem(helper.getSpawnEgg(), name + " Spawn Egg");
		addSubtitle(helper.getAmbient(), name + " mutters");
		addSubtitle(helper.getDeath(), name + " dies");
		addSubtitle(helper.getHurt(), name + " hurts");
		addSubtitle(helper.getCelebrate(), name + " cheers");
		if (helper.getCasting() != null)
			addSubtitle(helper.getCasting(), name + " casts spell");
	}

	public void addSubtitle(SoundEvent sound, String name) {
		String path = Raided.MOD_ID + ".subtitle." + sound.getLocation().getPath();
		this.add(path, name);
	}
}
