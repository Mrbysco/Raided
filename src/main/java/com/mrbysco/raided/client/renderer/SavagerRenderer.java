package com.mrbysco.raided.client.renderer;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.client.model.SavagerModel;
import com.mrbysco.raided.client.state.SavagerRenderState;
import com.mrbysco.raided.entity.Savager;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SavagerRenderer extends MobRenderer<Savager, SavagerRenderState, SavagerModel> {
	private static final ResourceLocation SAVAGER_LOCATION = ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "textures/entity/illager/savager.png");

	public SavagerRenderer(EntityRendererProvider.Context context) {
		super(context, new SavagerModel(context.bakeLayer(ClientHandler.SAVAGER)), 0.5F);
	}

	@Override
	public SavagerRenderState createRenderState() {
		return new SavagerRenderState();
	}

	@Override
	public void extractRenderState(Savager savager, SavagerRenderState renderState, float partialTicks) {
		super.extractRenderState(savager, renderState, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(SavagerRenderState renderState) {
		return SAVAGER_LOCATION;
	}
}