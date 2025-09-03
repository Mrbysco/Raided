package com.mrbysco.raided.client.renderer;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.client.model.ElectromancerModel;
import com.mrbysco.raided.client.state.ElectromancerRenderState;
import com.mrbysco.raided.entity.Electromancer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.CrossbowItem;

public class ElectromancerRenderer extends MobRenderer<Electromancer, ElectromancerRenderState, ElectromancerModel> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "textures/entity/illager/electromancer.png");

	public ElectromancerRenderer(EntityRendererProvider.Context context) {
		super(context, new ElectromancerModel(context.bakeLayer(ClientHandler.ELECTROMANCER)), 0.4F);
		this.addLayer(new ItemInHandLayer<>(this));
	}

	@Override
	public ElectromancerRenderState createRenderState() {
		return new ElectromancerRenderState();
	}

	@Override
	public void extractRenderState(Electromancer electromancer, ElectromancerRenderState renderState, float partialTick) {
		super.extractRenderState(electromancer, renderState, partialTick);
		ArmedEntityRenderState.extractArmedEntityRenderState(electromancer, renderState, this.itemModelResolver);
		renderState.isRiding = electromancer.isPassenger();
		renderState.mainArm = electromancer.getMainArm();
		renderState.armPose = electromancer.getArmPose();
		renderState.maxCrossbowChargeDuration = renderState.armPose == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE
				? CrossbowItem.getChargeDuration(electromancer.getUseItem(), electromancer)
				: 0;
		renderState.ticksUsingItem = electromancer.getTicksUsingItem();
		renderState.attackAnim = electromancer.getAttackAnim(partialTick);
		renderState.isAggressive = electromancer.isAggressive();
	}

	@Override
	public ResourceLocation getTextureLocation(ElectromancerRenderState renderState) {
		return TEXTURE;
	}
}
