package com.mrbysco.raided.client.renderer;

import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.client.model.SavagerModel;
import com.mrbysco.raided.entity.Savager;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SavagerRenderer extends MobRenderer<Savager, SavagerModel<Savager>> {
	private static final ResourceLocation SAVAGER_LOCATION = new ResourceLocation("textures/entity/illager/savager.png");

	public SavagerRenderer(EntityRendererProvider.Context context) {
		super(context, new SavagerModel<>(context.bakeLayer(ClientHandler.SAVAGER)), 0.5F);
	}

	protected float getBob(Savager p_116528_, float p_116529_) {
		return 1.5393804F;
	}

	public ResourceLocation getTextureLocation(Savager savager) {
		return SAVAGER_LOCATION;
	}
}