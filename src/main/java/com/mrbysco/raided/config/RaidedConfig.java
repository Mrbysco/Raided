package com.mrbysco.raided.config;

import com.mrbysco.raided.Raided;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class RaidedConfig {
	protected static final Integer[] inquisitorSpawns = new Integer[]{0, 0, 0, 3, 0, 0, 0, 0};
	protected static final Integer[] incineratorSpawns = new Integer[]{0, 0, 0, 0, 1, 0, 0, 1};
	protected static final Integer[] savagerSpawns = new Integer[]{0, 0, 0, 2, 0, 0, 1, 0};
	protected static final Integer[] necromancerSpawns = new Integer[]{0, 0, 1, 0, 0, 1, 0, 0};
	protected static final Integer[] electromancerSpawns = new Integer[]{0, 1, 0, 2, 0, 1, 0, 0};

	public static class Common {
		public final ConfigValue<List<? extends Integer>> inquisitorSpawnsPerWave;
		public final ConfigValue<List<? extends Integer>> incineratorSpawnsPerWave;
		public final ConfigValue<List<? extends Integer>> savagerSpawnsPerWave;
		public final ConfigValue<List<? extends Integer>> necromancerSpawnsPerWave;
		public final ConfigValue<List<? extends Integer>> electromancerSpawnsPerWave;

		Common(ModConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("General");

			inquisitorSpawnsPerWave = builder
					.comment("The spawns per wave for the Inquisitor before bonus spawns are applied (Must always have 8 values!)")
					.defineList("inquisitorSpawnsPerWave", () -> List.of(inquisitorSpawns), () -> 0, o -> (o instanceof Integer amount) && amount >= 0);

			incineratorSpawnsPerWave = builder
					.comment("The spawns per wave for the Incinerator before bonus spawns are applied (Must always have 8 values!)")
					.defineList("incineratorSpawnsPerWave", () -> List.of(incineratorSpawns), () -> 0, o -> (o instanceof Integer amount) && amount >= 0);

			savagerSpawnsPerWave = builder
					.comment("The spawns per wave for the Savager before bonus spawns are applied (Must always have 8 values!)")
					.defineList("savagerSpawnsPerWave", () -> List.of(savagerSpawns), () -> 0, o -> (o instanceof Integer amount) && amount >= 0);

			necromancerSpawnsPerWave = builder
					.comment("The spawns per wave for the Necromancer before bonus spawns are applied (Must always have 8 values!)")
					.defineList("necromancerSpawnsPerWave", () -> List.of(necromancerSpawns), () -> 0, o -> (o instanceof Integer amount) && amount >= 0);

			electromancerSpawnsPerWave = builder
					.comment("The spawns per wave for the Electromancer before bonus spawns are applied (Must always have 8 values!)")
					.defineList("electromancerSpawnsPerWave", () -> List.of(electromancerSpawns), () -> 0, o -> (o instanceof Integer amount) && amount >= 0);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
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
					validateValue(COMMON.savagerSpawnsPerWave, savagerSpawns) ||
					validateValue(COMMON.necromancerSpawnsPerWave, necromancerSpawns) ||
					validateValue(COMMON.electromancerSpawnsPerWave, electromancerSpawns)) {
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
