package com.mrbysco.raided.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.entity.projectiles.LightningProjectile;
import net.minecraft.client.model.ShulkerBulletModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class LightningProjectileRenderer extends EntityRenderer<LightningProjectile> {
	private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Raided.MOD_ID, "textures/entity/projectile/lightning.png");
	private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(TEXTURE_LOCATION);
	private final ShulkerBulletModel<LightningProjectile> model;

	public LightningProjectileRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new ShulkerBulletModel<>(context.bakeLayer(ClientHandler.LIGHTNING_PROJECTILE));
	}

	protected int getBlockLightLevel(LightningProjectile projectile, BlockPos pos) {
		return 15;
	}

	public void render(LightningProjectile projectile, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		poseStack.pushPose();
		float f = Mth.rotlerp(projectile.yRotO, projectile.getYRot(), partialTicks);
		float f1 = Mth.lerp(partialTicks, projectile.xRotO, projectile.getXRot());
		float f2 = (float) projectile.tickCount + partialTicks;
		poseStack.translate(0.0D, (double) 0.15F, 0.0D);
		poseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.sin(f2 * 0.1F) * 180.0F));
		poseStack.mulPose(Vector3f.XP.rotationDegrees(Mth.cos(f2 * 0.1F) * 180.0F));
		poseStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.sin(f2 * 0.15F) * 360.0F));
		poseStack.scale(-0.5F, -0.5F, 0.5F);
		this.model.setupAnim(projectile, 0.0F, 0.0F, 0.0F, f, f1);
		VertexConsumer vertexconsumer = bufferSource.getBuffer(this.model.renderType(TEXTURE_LOCATION));
		this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		poseStack.scale(1.5F, 1.5F, 1.5F);
		VertexConsumer vertexConsumer = bufferSource.getBuffer(RENDER_TYPE);
		this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.15F);
		poseStack.popPose();
		super.render(projectile, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
	}

	public ResourceLocation getTextureLocation(LightningProjectile projectile) {
		return TEXTURE_LOCATION;
	}
}
