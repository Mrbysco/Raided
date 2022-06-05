package com.mrbysco.raided.client;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.model.IncineratorModel;
import com.mrbysco.raided.client.model.InquisitorModel;
import com.mrbysco.raided.client.model.SavagerModel;
import com.mrbysco.raided.client.renderer.IncineratorRenderer;
import com.mrbysco.raided.client.renderer.InquisitorRenderer;
import com.mrbysco.raided.client.renderer.SavagerRenderer;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ClientHandler {
	public static final ModelLayerLocation INQUISITOR = new ModelLayerLocation(new ResourceLocation(Raided.MOD_ID, "inquisitor"), "main");
	public static final ModelLayerLocation INCINERATOR = new ModelLayerLocation(new ResourceLocation(Raided.MOD_ID, "incinerator"), "main");
	public static final ModelLayerLocation SAVAGER = new ModelLayerLocation(new ResourceLocation(Raided.MOD_ID, "savager"), "main");

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(RaidedRegistry.INQUISITOR.get(), InquisitorRenderer::new);
		event.registerEntityRenderer(RaidedRegistry.INCINERATOR.get(), IncineratorRenderer::new);
		event.registerEntityRenderer(RaidedRegistry.INCINERATOR_FIREBALL.get(), (context) -> new ThrownItemRenderer<>(context, 2.0F, true));
		event.registerEntityRenderer(RaidedRegistry.SAVAGER.get(), SavagerRenderer::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(INQUISITOR, InquisitorModel::createBodyLayer);
		event.registerLayerDefinition(INCINERATOR, IncineratorModel::createBodyLayer);
		event.registerLayerDefinition(SAVAGER, SavagerModel::createBodyLayer);
	}
}
