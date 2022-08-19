package com.mrbysco.raided.client.renderer;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.client.model.NecromancerModel;
import com.mrbysco.raided.entity.Necromancer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class NecromancerRenderer extends MobRenderer<Necromancer, NecromancerModel> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Raided.MOD_ID, "textures/entity/illager/necromancer.png");

	public NecromancerRenderer(EntityRendererProvider.Context context) {
		super(context, new NecromancerModel(context.bakeLayer(ClientHandler.NECROMANCER)), 0.4F);
		this.addLayer(new ItemInHandLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Necromancer necromancer) {
		return TEXTURE;
	}
}
