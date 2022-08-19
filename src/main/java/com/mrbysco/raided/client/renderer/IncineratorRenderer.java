package com.mrbysco.raided.client.renderer;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.client.model.IncineratorModel;
import com.mrbysco.raided.client.renderer.layers.GlowLayer;
import com.mrbysco.raided.entity.Incinerator;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class IncineratorRenderer extends MobRenderer<Incinerator, IncineratorModel<Incinerator>> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Raided.MOD_ID, "textures/entity/illager/incinerator.png");
	private static final ResourceLocation EYES_TEXTURE = new ResourceLocation(Raided.MOD_ID, "textures/entity/illager/layer/eyes_incinerator.png");

	public IncineratorRenderer(EntityRendererProvider.Context context) {
		super(context, new IncineratorModel(context.bakeLayer(ClientHandler.INCINERATOR)), 0.7F);
		this.addLayer(new ItemInHandLayer(this));
		this.addLayer(new GlowLayer(this, EYES_TEXTURE));
	}

	@Override
	public ResourceLocation getTextureLocation(Incinerator incinerator) {
		return TEXTURE;
	}
}
