package com.mrbysco.raided.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.client.model.projectile.LightningProjectileModel;
import com.mrbysco.raided.client.state.LightningProjectileRenderState;
import com.mrbysco.raided.entity.projectiles.LightningProjectile;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class LightningProjectileRenderer extends EntityRenderer<LightningProjectile, LightningProjectileRenderState> {
	private static final Identifier TEXTURE_LOCATION = Raided.modLoc("textures/entity/projectile/lightning.png");
	private static final RenderType RENDER_TYPE = RenderTypes.entityTranslucent(TEXTURE_LOCATION);
	private final LightningProjectileModel model;

	public LightningProjectileRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new LightningProjectileModel(context.bakeLayer(ClientHandler.LIGHTNING_PROJECTILE));
	}

	protected int getBlockLightLevel(LightningProjectile projectile, BlockPos pos) {
		return 15;
	}

	@Override
	public void submit(LightningProjectileRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
		poseStack.pushPose();
		float f = renderState.ageInTicks;
		poseStack.translate(0.0F, 0.15F, 0.0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(f * 0.1F) * 180.0F));
		poseStack.mulPose(Axis.XP.rotationDegrees(Mth.cos(f * 0.1F) * 180.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(f * 0.15F) * 360.0F));
		poseStack.scale(-0.5F, -0.5F, 0.5F);
		this.model.setupAnim(renderState);
		nodeCollector.submitModel(this.model, renderState, poseStack, this.model.renderType(TEXTURE_LOCATION), renderState.lightCoords, OverlayTexture.NO_OVERLAY, -1, null);
		poseStack.scale(1.5F, 1.5F, 1.5F);
		nodeCollector.submitModel(this.model, renderState, poseStack, this.model.renderType(TEXTURE_LOCATION), renderState.lightCoords, OverlayTexture.NO_OVERLAY, 654311423, null);

		poseStack.popPose();
		super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
	}

	@Override
	public LightningProjectileRenderState createRenderState() {
		return new LightningProjectileRenderState();
	}

	@Override
	public void extractRenderState(LightningProjectile projectile, LightningProjectileRenderState renderState, float partialTick) {
		super.extractRenderState(projectile, renderState, partialTick);
		renderState.yRot = projectile.getYRot(partialTick);
		renderState.xRot = projectile.getXRot(partialTick);
	}
}
