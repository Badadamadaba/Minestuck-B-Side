package com.mcastudios.minestuck.client.renderer.entity.frog;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mcastudios.minestuck.Minestuck;
import com.mcastudios.minestuck.client.model.FrogModel;
import com.mcastudios.minestuck.client.model.MSModelLayers;
import com.mcastudios.minestuck.entity.FrogEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class FrogRenderer extends MobRenderer<FrogEntity, FrogModel<FrogEntity>>
{

	public FrogRenderer(EntityRendererProvider.Context context)
	{
		super(context, new FrogModel<>(context.bakeLayer(MSModelLayers.FROG)), 0.5f);
		this.addLayer(new FrogSkinLayer(this, context.getModelSet()));
		this.addLayer(new FrogEyesLayer(this, context.getModelSet()));
		this.addLayer(new FrogBellyLayer(this, context.getModelSet()));
	}

	@Override
	public void render(FrogEntity frog, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
		poseStack.scale(frog.getFrogSize(), frog.getFrogSize(), frog.getFrogSize());
		super.render(frog, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(FrogEntity entity)
	{
		return new ResourceLocation(Minestuck.MOD_ID, "textures/entity/frog/base.png");
	}
	
	protected boolean shouldShowName(FrogEntity entity)
    {
        return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
    }
	

}
