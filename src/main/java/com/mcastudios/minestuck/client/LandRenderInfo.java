package com.mcastudios.minestuck.client;

import com.mcastudios.minestuck.client.renderer.LandSkyRenderer;
import com.mcastudios.minestuck.world.lands.LandProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.world.phys.Vec3;

public class LandRenderInfo extends DimensionSpecialEffects
{
	public LandRenderInfo()
	{
		super(128.0F, true, DimensionSpecialEffects.SkyType.NORMAL, false, false);
		setSkyRenderHandler(new LandSkyRenderer());
	}
	
	@Override
	public Vec3 getBrightnessDependentFogColor(Vec3 biomeFogColor, float brightness)
	{
		LandProperties properties = ClientDimensionData.getProperties(Minecraft.getInstance().level);
		return properties != null ? properties.getFogColor() : biomeFogColor;
	}
	
	@Override
	public boolean isFoggyAt(int x, int z)
	{
		return false;
	}
}
