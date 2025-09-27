package com.mrbysco.raided.datagen.server;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class RaidedEntityTypeTagsProvider extends EntityTypeTagsProvider {

	public RaidedEntityTypeTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider,
	                                    @Nullable net.neoforged.neoforge.common.data.ExistingFileHelper existingFileHelper) {
		super(packOutput, provider, Raided.MOD_ID, existingFileHelper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(EntityTypeTags.RAIDERS).add(
				RaidedRegistry.INQUISITOR.getEntityType(),
				RaidedRegistry.INCINERATOR.getEntityType(),
				RaidedRegistry.SAVAGER.getEntityType(),
				RaidedRegistry.NECROMANCER.getEntityType(),
				RaidedRegistry.ELECTROMANCER.getEntityType()
		);
	}
}
