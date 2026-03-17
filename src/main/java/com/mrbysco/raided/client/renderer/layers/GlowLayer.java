package com.mrbysco.raided.client.renderer.layers;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class GlowLayer<S extends LivingEntityRenderState, M extends EntityModel<S>> extends EyesLayer<S, M> {
	private final RenderType RENDER_TYPE;

	public GlowLayer(RenderLayerParent<S, M> layerParent, Identifier eyeTexture) {
		super(layerParent);
		this.RENDER_TYPE = RenderTypes.eyes(eyeTexture);
	}

	@Override
	public RenderType renderType() {
		return RENDER_TYPE;
	}
}
