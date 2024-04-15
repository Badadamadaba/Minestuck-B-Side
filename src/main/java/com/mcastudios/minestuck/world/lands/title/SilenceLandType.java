package com.mcastudios.minestuck.world.lands.title;

import com.mcastudios.minestuck.player.EnumAspect;
import com.mcastudios.minestuck.util.MSSoundEvents;
import com.mcastudios.minestuck.world.biome.LandBiomeSetType;
import com.mcastudios.minestuck.world.biome.LandBiomeType;
import com.mcastudios.minestuck.world.gen.feature.MSPlacedFeatures;
import com.mcastudios.minestuck.world.gen.structure.blocks.StructureBlockRegistry;
import com.mcastudios.minestuck.world.lands.LandBiomeGenBuilder;
import com.mcastudios.minestuck.world.lands.LandProperties;
import com.mcastudios.minestuck.world.lands.terrain.TerrainLandType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.phys.Vec3;

public class SilenceLandType extends TitleLandType
{
	public static final String SILENCE = "minestuck.silence";
	
	public SilenceLandType()
	{
		super(EnumAspect.VOID);
	}
	
	@Override
	public String[] getNames()
	{
		return new String[]{SILENCE};
	}
	
	@Override
	public void registerBlocks(StructureBlockRegistry registry)
	{
		registry.setBlock("structure_wool_2", Blocks.BLACK_WOOL);
		registry.setBlock("carpet", Blocks.BLUE_CARPET);
		
		if(registry.isUsingDefault("torch"))
			registry.setBlock("torch", Blocks.REDSTONE_TORCH);
		if(registry.isUsingDefault("wall_torch"))
			registry.setBlock("wall_torch", Blocks.REDSTONE_WALL_TORCH);
	}
	
	@Override
	public void setProperties(LandProperties properties)
	{
		if(properties.biomes.getPrecipitation() == Biome.Precipitation.RAIN)
			properties.forceRain = LandProperties.ForceType.OFF;
		properties.skylightBase = Math.min(1/2F, properties.skylightBase);
		properties.mergeFogColor(new Vec3(0, 0, 0.1), 0.5F);
	}
	
	@Override
	public void addBiomeGeneration(LandBiomeGenBuilder builder, StructureBlockRegistry blocks, LandBiomeSetType biomeSet)
	{
		builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, MSPlacedFeatures.PUMPKIN, LandBiomeType.anyExcept(LandBiomeType.OCEAN));
	}
	
	@Override
	public boolean isAspectCompatible(TerrainLandType otherType)
	{
		LandProperties properties = LandProperties.createPartial(otherType);
		
		return properties.forceRain != LandProperties.ForceType.ON || properties.biomes.getPrecipitation() != Biome.Precipitation.RAIN;
	}
	
	@Override
	public SoundEvent getBackgroundMusic()
	{
		return MSSoundEvents.MUSIC_SILENCE.get();
	}
}