package com.mrbysco.raided.client.renderer.layers;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractIllager;

public class GlowLayer<T extends AbstractIllager> extends EyesLayer<T, EntityModel<T>> {
	private final RenderType RENDER_TYPE;

	public GlowLayer(RenderLayerParent<T, EntityModel<T>> layerParent, ResourceLocation eyeTexture) {
		super(layerParent);
		this.RENDER_TYPE = RenderType.eyes(eyeTexture);
	}

	@Override
	public RenderType renderType() {
		return RENDER_TYPE;
	}
}
