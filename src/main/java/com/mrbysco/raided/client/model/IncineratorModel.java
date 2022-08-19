package com.mrbysco.raided.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrbysco.raided.entity.Incinerator;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

public class IncineratorModel<T extends Incinerator> extends HierarchicalModel<T> implements ArmedModel, HeadedModel {
	private static final int STICK_COUNT = 4;

	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart leftArmLower;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;

	private final ModelPart blazeHead;
	private final ModelPart tankRope1;
	private final ModelPart tankRope2;
	private final ModelPart[] blazeSticks;

	public IncineratorModel(ModelPart modelPart) {
		this.root = modelPart.getChild("root");
		this.head = root.getChild("head");
		this.hat = head.getChild("hat");
		this.body = root.getChild("body");
		this.leftArm = root.getChild("left_arm");
		this.leftArmLower = leftArm.getChild("left_arm_lower");
		this.rightArm = root.getChild("right_arm");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");

		ModelPart tank = root.getChild("tank");
		this.blazeHead = tank.getChild("blaze_head");

		this.tankRope1 = root.getChild("tank_rope1");
		this.tankRope2 = tankRope1.getChild("tank_rope2");

		this.blazeSticks = new ModelPart[4];
		Arrays.setAll(this.blazeSticks, (index) -> tank.getChild(getStickName(index)));
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -27.0F, 0.0F));

		head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -4.0F));

		head.addOrReplaceChild("cigar", CubeListBuilder.create().texOffs(56, 0).addBox(-0.5F, -0.5F, -4.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -1.0F, -4.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 18).addBox(-7.0F, -27.0F, -3.5F, 14.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		body.addOrReplaceChild("layer", CubeListBuilder.create().texOffs(0, 42).addBox(-7.5F, -0.5F, -4.0F, 15.0F, 20.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -27.0F, 0.0F));

		body.addOrReplaceChild("gauge", CubeListBuilder.create().texOffs(46, 42).addBox(-2.5F, -2.5F, -1.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -21.5F, -3.5F));

		PartDefinition leftArm = root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(70, 27).addBox(0.0F, -2.0F, -3.5F, 5.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -25.0F, 0.0F));

		leftArm.addOrReplaceChild("left_arm_lower", CubeListBuilder.create().texOffs(70, 54).addBox(-2.51F, 0.0F, -7.0F, 5.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 6.0F, 3.5F));

		leftArm.addOrReplaceChild("flamethrower1", CubeListBuilder.create().texOffs(114, 32).addBox(-2.51F, 2.0F, 0.0F, 5.0F, 10.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 6.0F, 3.5F));

		leftArm.addOrReplaceChild("flamethrower2", CubeListBuilder.create().texOffs(114, 47).addBox(-1.5F, 5.0F, 1.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 6.0F, 3.5F));

		leftArm.addOrReplaceChild("flamethrower3", CubeListBuilder.create().texOffs(114, 60).addBox(-1.0F, 8.0F, -4.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 6.0F, 3.5F));

		leftArm.addOrReplaceChild("flamethrower4", CubeListBuilder.create().texOffs(114, 66).addBox(2.49F, 3.0F, 1.0F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 6.0F, 3.5F));

		PartDefinition rightArm = root.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-7.0F, -25.0F, 0.0F));

		rightArm.addOrReplaceChild("right_arm_r1", CubeListBuilder.create().texOffs(70, 27).addBox(-5.0F, -3.0F, -3.5F, 5.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		rightArm.addOrReplaceChild("right_arm_lower", CubeListBuilder.create().texOffs(70, 54).addBox(-2.49F, 0.0F, -7.0F, 5.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 6.0F, 3.5F));

		root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 25).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, -10.0F, 0.0F));

		root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 25).addBox(-3.5F, 0.0F, -3.5F, 7.0F, 10.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -10.0F, 0.0F));

		PartDefinition tank = root.addOrReplaceChild("tank", CubeListBuilder.create().texOffs(94, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, 4.5F));

		tank.addOrReplaceChild("tank2", CubeListBuilder.create().texOffs(94, 32).addBox(0.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, 8.0F));

		tank.addOrReplaceChild("tank3", CubeListBuilder.create().texOffs(94, 48).addBox(-2.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.0F, 8.0F));

		tank.addOrReplaceChild("tank4", CubeListBuilder.create().texOffs(94, 64).addBox(-1.0F, -4.0F, -3.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -3.0F, 8.0F, 0.0F, 0.0F, -0.7854F));

		tank.addOrReplaceChild("blaze_head", CubeListBuilder.create().texOffs(158, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 8.0F));

		PartDefinition tankRope1 = root.addOrReplaceChild("tank_rope1", CubeListBuilder.create().texOffs(114, 72).addBox(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -16.0F, 12.5F));

		tankRope1.addOrReplaceChild("tank_rope2", CubeListBuilder.create().texOffs(114, 72).addBox(0.0F, -1.0F, -2.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, 1.0F));

		CubeListBuilder stickCube = CubeListBuilder.create()
				.texOffs(158, 16).addBox(-1.0F, -4.0F, 6.0F, 2.0F, 8.0F, 2.0F);

		for (int i = 0; i < STICK_COUNT; ++i) {
			tank.addOrReplaceChild(getStickName(i), stickCube, PartPose.offset(0, 0, 8));
		}

		return LayerDefinition.create(meshdefinition, 256, 128);
	}

	private static String getStickName(int index) {
		return "stick" + index;
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		ItemStack itemstack = entityIn.getItemBySlot(EquipmentSlot.HEAD);

		// head
		head.yRot = netHeadYaw / 57.295776F;
		head.xRot = headPitch / 57.295776F;

		// arms
		if (itemstack.isEmpty()) {
			leftArm.xRot = 0.0F;
			rightArm.xRot = Mth.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount * 0.5F;

			leftArm.zRot = 0.0F;
			rightArm.zRot = 0.0F;

			if (attackTime > -9990.0F) {
				holdingMelee();
			}

			rightArm.zRot -= (Mth.cos(ageInTicks * 0.09F) * 0.025F + 0.025F);
			rightArm.xRot -= Mth.sin(ageInTicks * 0.067F) * 0.025F;
			leftArm.xRot = leftArm.xRot - degToRad(15);
			leftArm.xRot = -degToRad(30);
			leftArm.yRot = 0.0F;

			tankRope1.yRot = degToRad(45);
			tankRope1.zRot = degToRad(90);
			tankRope2.yRot = degToRad(90);
		}

		if (entityIn.isThrowing()) {
			animationThrow();
		}

		// legs
		leftLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;
		rightLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.8F * limbSwingAmount;
		leftLeg.yRot = 0.0F;
		rightLeg.yRot = 0.0F;
		leftLeg.zRot = 0.0F;
		rightLeg.zRot = 0.0F;

		// blaze
		float f = ageInTicks * (float) Math.PI * -0.1F;

		for (int i = 0; i < 4; ++i) {
			blazeSticks[i].y = 2.0F + Mth.cos(((float) (i * 2) + ageInTicks) * 0.25F);
			blazeSticks[i].x = Mth.cos(f) * 6.0F;
			blazeSticks[i].z = Mth.sin(f) * 6.0F;
			++f;
		}

		blazeHead.yRot = netHeadYaw * 0.017453292F;
		blazeHead.xRot = headPitch * 0.017453292F;

		if (entityIn.isPassenger()) {
			rightLeg.xRot += -((float) Math.PI / 5F);
			leftLeg.xRot = -1.4137167F;
			leftLeg.yRot = ((float) Math.PI / 10F);
			leftLeg.zRot = 0.07853982F;
			rightLeg.xRot = -1.4137167F;
			rightLeg.yRot = -((float) Math.PI / 10F);
			rightLeg.zRot = -0.07853982F;
		}
	}

	public void holdingMelee() {
		float f6;
		float f7;

		f6 = 1.0F - attackTime;
		f6 *= f6;
		f6 *= f6;
		f6 = 1.0F - f6;
		f7 = Mth.sin(f6 * (float) Math.PI);
		float f8 = Mth.sin(attackTime * (float) Math.PI) * -(head.xRot - 0.7F) * 0.75F;

		leftArm.xRot = (float) ((double) leftArm.xRot - ((double) f7 * 1.2D + (double) f8));
		leftArm.xRot += (body.yRot * 2.0F);
		leftArm.zRot = (Mth.sin(attackTime * (float) Math.PI) * -0.4F);
	}

	private void animationThrow() {
		leftArm.yRot = -degToRad(30);
		leftArmLower.xRot = -degToRad(60);

		tankRope1.yRot = -degToRad(60);
		tankRope1.zRot = -degToRad(90);
		tankRope2.yRot = -degToRad(60);
	}

	protected float degToRad(float degrees) {
		return degrees * (float) Math.PI / 180;
	}

	@Override
	public ModelPart root() {
		return root;
	}

	private ModelPart getArm(HumanoidArm arm) {
		return arm == HumanoidArm.LEFT ? this.leftArm : this.rightArm;
	}

	public ModelPart getHead() {
		return this.head;
	}

	public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
		this.getArm(arm).translateAndRotate(poseStack);
	}
}