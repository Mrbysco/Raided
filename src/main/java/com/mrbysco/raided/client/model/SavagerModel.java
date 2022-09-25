package com.mrbysco.raided.client.model;

import com.mrbysco.raided.entity.Savager;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class SavagerModel<T extends Savager> extends HierarchicalModel<T> implements HeadedModel {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart upperBody;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart body;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart tail;

	public SavagerModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.upperBody = root.getChild("upperBody");
		this.leftFrontLeg = root.getChild("leftFrontLeg");
		this.rightFrontLeg = root.getChild("rightFrontLeg");
		this.body = root.getChild("body");
		this.rightHindLeg = root.getChild("rightHindLeg");
		this.leftHindLeg = root.getChild("leftHindLeg");
		this.tail = root.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().create()
						.texOffs(0, 0).addBox(-3.0F, -5.0F, -4.0F, 6.0F, 8.0F, 4.0F)
						.texOffs(0, 12).addBox(-1.0F, 0.0F, -6.0F, 2.0F, 4.0F, 2.0F),
				PartPose.offset(0.0F, 13.0F, -6.0F));

		head.addOrReplaceChild("ear_left_r1", CubeListBuilder.create()
						.texOffs(8, 13).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 1.0F),
				PartPose.offsetAndRotation(1.5F, -5.0F, -1.0F, 0.5199F, 0.0653F, -0.1135F));

		head.addOrReplaceChild("ear_right_r1", CubeListBuilder.create()
						.texOffs(8, 13).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 1.0F),
				PartPose.offsetAndRotation(-1.5F, -5.0F, -1.0F, 0.5199F, -0.0653F, 0.1135F));

		PartDefinition upperBody = partdefinition.addOrReplaceChild("upperBody", CubeListBuilder.create(),
				PartPose.offset(0.0F, 13.0F, -2.0F));

		upperBody.addOrReplaceChild("neck_chain_r1", CubeListBuilder.create()
						.texOffs(20, 14).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 2.0F, 2.0F),
				PartPose.offsetAndRotation(0.0F, -4.0F, -1.0F, -0.7854F, 0.0F, 0.0F));

		upperBody.addOrReplaceChild("neck_r1", CubeListBuilder.create()
						.texOffs(20, 0).addBox(-4.0F, -6.0F, -4.5F, 8.0F, 6.0F, 8.0F),
				PartPose.offsetAndRotation(0.0F, -0.5F, 1.0F, 1.5708F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("leftFrontLeg", CubeListBuilder.create()
						.texOffs(0, 18).mirror().addBox(0.5F, 0.0F, -4.5F, 3.0F, 11.0F, 3.0F).mirror(false),
				PartPose.offset(0.0F, 13.0F, -2.0F));

		partdefinition.addOrReplaceChild("rightFrontLeg", CubeListBuilder.create()
						.texOffs(0, 18).addBox(-3.5F, 0.0F, -4.5F, 3.0F, 11.0F, 3.0F),
				PartPose.offset(0.0F, 13.0F, -2.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(),
				PartPose.offset(0.0F, 13.0F, -1.0F));

		body.addOrReplaceChild("body_r1", CubeListBuilder.create()
						.texOffs(20, 14).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 9.0F, 6.0F),
				PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 1.5708F, 0.0F, 0.0F));

		partdefinition.addOrReplaceChild("rightHindLeg", CubeListBuilder.create()
						.texOffs(0, 18).addBox(-3.5F, 0.0F, 5.5F, 3.0F, 11.0F, 3.0F),
				PartPose.offset(0.0F, 13.0F, -1.0F));

		partdefinition.addOrReplaceChild("leftHindLeg", CubeListBuilder.create()
						.texOffs(0, 18).mirror().addBox(0.5F, 0.0F, 5.5F, 3.0F, 11.0F, 3.0F).mirror(false),
				PartPose.offset(0.0F, 13.0F, -1.0F));

		partdefinition.addOrReplaceChild("tail", CubeListBuilder.create()
						.texOffs(12, 22).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F),
				PartPose.offsetAndRotation(0.0F, 13.0F, 7.0F, 1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	public void prepareMobModel(T savager, float limbSwing, float limbSwingAmount, float partialTick) {
		this.tail.yRot = 0.0F;
//		this.body.xRot = ((float) Math.PI / 2F);
//		this.upperBody.xRot = this.body.xRot;
		this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
		this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.5F * limbSwingAmount;
		this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.5F * limbSwingAmount;
		this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 0.5F * limbSwingAmount;
	}

	@Override
	public void setupAnim(T savager, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * ((float) Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
		this.tail.xRot = ageInTicks;
	}
}
