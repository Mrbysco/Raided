package com.mrbysco.raided.client.renderer;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.client.model.NecromancerModel;
import com.mrbysco.raided.client.state.NecromancerRenderState;
import com.mrbysco.raided.entity.Necromancer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.CrossbowItem;

public class NecromancerRenderer extends MobRenderer<Necromancer, NecromancerRenderState, NecromancerModel> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Raided.MOD_ID, "textures/entity/illager/necromancer.png");

	public NecromancerRenderer(EntityRendererProvider.Context context) {
		super(context, new NecromancerModel(context.bakeLayer(ClientHandler.NECROMANCER)), 0.4F);
		this.addLayer(new ItemInHandLayer<>(this));
	}

	@Override
	public NecromancerRenderState createRenderState() {
		return new NecromancerRenderState();
	}

	@Override
	public void extractRenderState(Necromancer necromancer, NecromancerRenderState renderState, float partialTick) {
		super.extractRenderState(necromancer, renderState, partialTick);
		ArmedEntityRenderState.extractArmedEntityRenderState(necromancer, renderState, this.itemModelResolver);
		renderState.isRiding = necromancer.isPassenger();
		renderState.mainArm = necromancer.getMainArm();
		renderState.armPose = necromancer.getArmPose();
		renderState.maxCrossbowChargeDuration = renderState.armPose == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE
				? CrossbowItem.getChargeDuration(necromancer.getUseItem(), necromancer)
				: 0;
		renderState.ticksUsingItem = necromancer.getTicksUsingItem();
		renderState.attackAnim = necromancer.getAttackAnim(partialTick);
		renderState.isAggressive = necromancer.isAggressive();
	}

	@Override
	public ResourceLocation getTextureLocation(NecromancerRenderState renderState) {
		return TEXTURE;
	}
}
