package com.mcastudios.minestuck.world.lands.title;

import com.mcastudios.minestuck.player.EnumAspect;
import com.mcastudios.minestuck.util.MSSoundEvents;
import com.mcastudios.minestuck.world.biome.LandBiomeSetType;
import com.mcastudios.minestuck.world.biome.LandBiomeType;
import com.mcastudios.minestuck.world.gen.feature.MSPlacedFeatures;
import com.mcastudios.minestuck.world.gen.structure.blocks.StructureBlockRegistry;
import com.mcastudios.minestuck.world.lands.LandBiomeGenBuilder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BucketsLandType extends TitleLandType    //Yes, buckets
{
	public static final String BUCKETS = "minestuck.buckets";
	
	public BucketsLandType()
	{
		super(EnumAspect.SPACE);
	}
	
	@Override
	public String[] getNames()
	{
		return new String[]{BUCKETS};
	}
	
	@Override
	public void registerBlocks(StructureBlockRegistry registry)
	{
		registry.setBlock("structure_wool_2", Blocks.BLUE_WOOL);
		registry.setBlock("carpet", Blocks.BLACK_CARPET);
	}
	
	@Override
	public void addBiomeGeneration(LandBiomeGenBuilder builder, StructureBlockRegistry blocks, LandBiomeSetType biomeSet)
	{
		builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MSPlacedFeatures.BUCKET, LandBiomeType.anyExcept(LandBiomeType.OCEAN));
	}
	
	@Override
	public SoundEvent getBackgroundMusic()
	{
		return MSSoundEvents.MUSIC_BUCKETS.get();
	}
}