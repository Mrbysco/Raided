package com.mrbysco.raided.client.renderer;

import com.mrbysco.raided.Raided;
import com.mrbysco.raided.client.ClientHandler;
import com.mrbysco.raided.client.model.InquisitorModel;
import com.mrbysco.raided.entity.Inquisitor;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class InquisitorRenderer extends MobRenderer<Inquisitor, InquisitorModel<Inquisitor>> {
    private static final List<ResourceLocation> TEXTURES = List.of(
            new ResourceLocation(Raided.MOD_ID, "textures/entity/illager/inquisitor01.png"),
            new ResourceLocation(Raided.MOD_ID, "textures/entity/illager/inquisitor02.png"),
            new ResourceLocation(Raided.MOD_ID, "textures/entity/illager/inquisitor03.png"));

    public InquisitorRenderer(EntityRendererProvider.Context context) {
        super(context, new InquisitorModel(context.bakeLayer(ClientHandler.INQUISITOR)), 0.4F);
        this.addLayer(new ItemInHandLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Inquisitor inquisitor) {
        return switch (inquisitor.getInquisitorType()) {
            default -> TEXTURES.get(0);
            case 1 -> TEXTURES.get(1);
            case 2 -> TEXTURES.get(2);
        };
    }
}
