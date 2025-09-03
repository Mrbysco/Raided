package com.mrbysco.raided.client.model.projectile;

import com.mrbysco.raided.client.state.LightningProjectileRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class LightningProjectileModel extends EntityModel<LightningProjectileRenderState> {
	private final ModelPart root;
	private final ModelPart main;

	public LightningProjectileModel(ModelPart root) {
		super(root);
		this.root = root;
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		partdefinition.addOrReplaceChild("main", CubeListBuilder.create()
						.texOffs(0, 0).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F)
						.texOffs(0, 10).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F)
						.texOffs(20, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F),
				PartPose.ZERO);
		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	@Override
	public void setupAnim(LightningProjectileRenderState renderState) {
		this.main.yRot = renderState.yRot * ((float) Math.PI / 180F);
		this.main.xRot = renderState.xRot * ((float) Math.PI / 180F);
	}
}
