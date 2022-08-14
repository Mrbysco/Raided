package com.mrbysco.raided.config;

import com.mrbysco.raided.Raided;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class RaidedConfig {
	protected static final Integer[] inquisitorSpawns = new Integer[]{0, 0, 0, 3, 0, 0, 0, 0};
	protected static final Integer[] incineratorSpawns = new Integer[]{0, 0, 0, 0, 1, 0, 0, 1};
	protected static final Integer[] savagerSpawns = new Integer[]{0, 0, 0, 2, 0, 0, 1, 0};

	public static class Common {
		public final BooleanValue spawnInquisitor;
		public final ConfigValue<List<? extends Integer>> inquisitorSpawnsPerWave;
		public final BooleanValue spawnIncinerator;
		public final ConfigValue<List<? extends Integer>> incineratorSpawnsPerWave;
		public final BooleanValue spawnSavager;
		public final ConfigValue<List<? extends Integer>> savagerSpawnsPerWave;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("General");

			spawnInquisitor = builder
					.comment("Enable Inquisitor spawning during raids based on the 'inquisitorSpawnsPerWave' setup")
					.define("spawnInquisitor", true);

			inquisitorSpawnsPerWave = builder
					.comment("The spawns per wave for the Inquisitor before bonus spawns are applied (Must always have 8 values!)")
					.defineList(List.of("inquisitorSpawnsPerWave"), () -> List.of(inquisitorSpawns), o -> (o instanceof Integer amount) && amount >= 0);

			spawnIncinerator = builder
					.comment("Enable Incinerator spawning during raids based on the 'incineratorSpawnsPerWave' setup")
					.define("spawnIncinerator", true);

			incineratorSpawnsPerWave = builder
					.comment("The spawns per wave for the Incinerator before bonus spawns are applied (Must always have 8 values!)")
					.defineList(List.of("incineratorSpawnsPerWave"), () -> List.of(incineratorSpawns), o -> (o instanceof Integer amount) && amount >= 0);

			spawnSavager = builder
					.comment("Enable Savager spawning during raids based on the 'savagerSpawnsPerWave' setup")
					.define("spawnSavager", true);

			savagerSpawnsPerWave = builder
					.comment("The spawns per wave for the Savager before bonus spawns are applied (Must always have 8 values!)")
					.defineList(List.of("savagerSpawnsPerWave"), () -> List.of(savagerSpawns), o -> (o instanceof Integer amount) && amount >= 0);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}


	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		Raided.LOGGER.debug("Loaded Raided's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		Raided.LOGGER.debug("Raided's config just got changed on the file system!");
		if (configEvent.getConfig().getModId().equals(Raided.MOD_ID)) {
			if (validateValue(COMMON.inquisitorSpawnsPerWave, inquisitorSpawns) ||
					validateValue(COMMON.incineratorSpawnsPerWave, incineratorSpawns) ||
					validateValue(COMMON.savagerSpawnsPerWave, savagerSpawns)) {
				configEvent.getConfig().save();
			}
		}
	}

	public static boolean validateValue(ConfigValue<List<? extends Integer>> configValue, Integer[] defaults) {
		if (configValue.get().size() != defaults.length) {
			Raided.LOGGER.error("'{}' is not the correct length, resetting to default. There must always be {} values in the list while it had {}",
					configValue.getPath().get(0), defaults.length, configValue.get().size());
			configValue.set(List.of(incineratorSpawns));
			return true;
		}
		return false;
	}
}
