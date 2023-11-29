package com.mrbysco.raided.client;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.model.ElectromancerModel;
import com.mrbysco.raided.client.model.IncineratorModel;
import com.mrbysco.raided.client.model.InquisitorModel;
import com.mrbysco.raided.client.model.NecromancerModel;
import com.mrbysco.raided.client.model.SavagerModel;
import com.mrbysco.raided.client.model.projectile.LightningProjectileModel;
import com.mrbysco.raided.client.renderer.ElectromancerRenderer;
import com.mrbysco.raided.client.renderer.IncineratorRenderer;
import com.mrbysco.raided.client.renderer.InquisitorRenderer;
import com.mrbysco.raided.client.renderer.LightningProjectileRenderer;
import com.mrbysco.raided.client.renderer.NecromancerRenderer;
import com.mrbysco.raided.client.renderer.SavagerRenderer;
import com.mrbysco.raided.registry.RaidedRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ClientHandler {
	public static final ModelLayerLocation INQUISITOR = new ModelLayerLocation(new ResourceLocation(Raided.MOD_ID, "inquisitor"), "main");
	public static final ModelLayerLocation INCINERATOR = new ModelLayerLocation(new ResourceLocation(Raided.MOD_ID, "incinerator"), "main");
	public static final ModelLayerLocation SAVAGER = new ModelLayerLocation(new ResourceLocation(Raided.MOD_ID, "savager"), "main");
	public static final ModelLayerLocation NECROMANCER = new ModelLayerLocation(new ResourceLocation(Raided.MOD_ID, "necromancer"), "main");
	public static final ModelLayerLocation ELECTROMANCER = new ModelLayerLocation(new ResourceLocation(Raided.MOD_ID, "electromancer"), "main");

	public static final ModelLayerLocation LIGHTNING_PROJECTILE = new ModelLayerLocation(new ResourceLocation(Raided.MOD_ID, "lightning_projectile"), "main");

	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(RaidedRegistry.INQUISITOR.getEntityType(), InquisitorRenderer::new);
		event.registerEntityRenderer(RaidedRegistry.INCINERATOR.getEntityType(), IncineratorRenderer::new);
		event.registerEntityRenderer(RaidedRegistry.INCINERATOR_FIREBALL.get(), (context) -> new ThrownItemRenderer<>(context, 2.0F, true));
		event.registerEntityRenderer(RaidedRegistry.SAVAGER.getEntityType(), SavagerRenderer::new);
		event.registerEntityRenderer(RaidedRegistry.NECROMANCER.getEntityType(), NecromancerRenderer::new);
		event.registerEntityRenderer(RaidedRegistry.ELECTROMANCER.getEntityType(), ElectromancerRenderer::new);

		event.registerEntityRenderer(RaidedRegistry.LIGHTNING_PROJECTILE.get(), LightningProjectileRenderer::new);
	}

	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(INQUISITOR, InquisitorModel::createBodyLayer);
		event.registerLayerDefinition(INCINERATOR, IncineratorModel::createBodyLayer);
		event.registerLayerDefinition(SAVAGER, SavagerModel::createBodyLayer);
		event.registerLayerDefinition(NECROMANCER, NecromancerModel::createBodyLayer);
		event.registerLayerDefinition(ELECTROMANCER, ElectromancerModel::createBodyLayer);
		event.registerLayerDefinition(LIGHTNING_PROJECTILE, LightningProjectileModel::createBodyLayer);
	}
}
