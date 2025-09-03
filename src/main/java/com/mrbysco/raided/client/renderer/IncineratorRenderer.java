package com.mrbysco.raided.client.renderer;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.client.model.IncineratorModel;
import com.mrbysco.raided.client.renderer.layers.GlowLayer;
import com.mrbysco.raided.client.state.IncineratorRenderState;
import com.mrbysco.raided.entity.Incinerator;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;

public class IncineratorRenderer extends MobRenderer<Incinerator, IncineratorRenderState, IncineratorModel> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "textures/entity/illager/incinerator.png");
	private static final ResourceLocation EYES_TEXTURE = ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "textures/entity/illager/layer/eyes_incinerator.png");

	public IncineratorRenderer(EntityRendererProvider.Context context) {
		super(context, new IncineratorModel(context.bakeLayer(ClientHandler.INCINERATOR)), 0.7F);
		this.addLayer(new ItemInHandLayer<>(this));
		this.addLayer(new GlowLayer<>(this, EYES_TEXTURE));
	}

	@Override
	public IncineratorRenderState createRenderState() {
		return new IncineratorRenderState();
	}

	@Override
	public void extractRenderState(Incinerator incinerator, IncineratorRenderState renderState, float partialTicks) {
		super.extractRenderState(incinerator, renderState, partialTicks);
		renderState.headStack = incinerator.getItemBySlot(EquipmentSlot.HEAD);
		renderState.throwing = incinerator.isThrowing();
	}

	@Override
	public ResourceLocation getTextureLocation(IncineratorRenderState renderState) {
		return TEXTURE;
	}
}
