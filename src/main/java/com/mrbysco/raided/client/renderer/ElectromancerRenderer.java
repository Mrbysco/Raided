package com.mrbysco.raided.client.renderer;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.client.model.ElectromancerModel;
import com.mrbysco.raided.client.model.InquisitorModel;
import com.mrbysco.raided.entity.Electromancer;
import com.mrbysco.raided.entity.Inquisitor;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class ElectromancerRenderer extends MobRenderer<Electromancer, ElectromancerModel> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Raided.MOD_ID, "textures/entity/illager/electromancer.png");

	public ElectromancerRenderer(EntityRendererProvider.Context context) {
		super(context, new ElectromancerModel(context.bakeLayer(ClientHandler.ELECTROMANCER)), 0.4F);
		this.addLayer(new ItemInHandLayer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Electromancer electromancer) {
		return TEXTURE;
	}
}
