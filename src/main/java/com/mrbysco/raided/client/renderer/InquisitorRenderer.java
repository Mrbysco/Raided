package com.mrbysco.raided.client.renderer;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.client.model.InquisitorModel;
import com.mrbysco.raided.client.state.InquisitorRenderState;
import com.mrbysco.raided.entity.Inquisitor;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.CrossbowItem;

import java.util.List;

public class InquisitorRenderer extends MobRenderer<Inquisitor, InquisitorRenderState, InquisitorModel> {
	private static final List<ResourceLocation> TEXTURES = List.of(
			ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "textures/entity/illager/inquisitor01.png"),
			ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "textures/entity/illager/inquisitor02.png"),
			ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "textures/entity/illager/inquisitor03.png"));

	public InquisitorRenderer(EntityRendererProvider.Context context) {
		super(context, new InquisitorModel(context.bakeLayer(ClientHandler.INQUISITOR)), 0.4F);
		this.addLayer(new ItemInHandLayer<>(this));
	}

	@Override
	public InquisitorRenderState createRenderState() {
		return new InquisitorRenderState();
	}

	@Override
	public void extractRenderState(Inquisitor inquisitor, InquisitorRenderState renderState, float partialTick) {
		super.extractRenderState(inquisitor, renderState, partialTick);
		ArmedEntityRenderState.extractArmedEntityRenderState(inquisitor, renderState, this.itemModelResolver);
		renderState.isRiding = inquisitor.isPassenger();
		renderState.mainArm = inquisitor.getMainArm();
		renderState.armPose = inquisitor.getArmPose();
		renderState.maxCrossbowChargeDuration = renderState.armPose == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE
				? CrossbowItem.getChargeDuration(inquisitor.getUseItem(), inquisitor)
				: 0;
		renderState.ticksUsingItem = inquisitor.getTicksUsingItem();
		renderState.attackAnim = inquisitor.getAttackAnim(partialTick);
		renderState.isAggressive = inquisitor.isAggressive();
		renderState.inquisitorType = inquisitor.getInquisitorType();
	}

	@Override
	public ResourceLocation getTextureLocation(InquisitorRenderState renderState) {
		return switch (renderState.inquisitorType) {
			case 1 -> TEXTURES.get(1);
			case 2 -> TEXTURES.get(2);
			default -> TEXTURES.get(0);
		};
	}
}
