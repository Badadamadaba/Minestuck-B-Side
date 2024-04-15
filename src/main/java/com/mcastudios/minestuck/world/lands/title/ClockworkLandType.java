package com.mcastudios.minestuck.world.lands.title;

import com.mcastudios.minestuck.player.EnumAspect;
import com.mcastudios.minestuck.util.MSSoundEvents;
import com.mcastudios.minestuck.world.biome.LandBiomeSetType;
import com.mcastudios.minestuck.world.biome.LandBiomeType;
import com.mcastudios.minestuck.world.gen.feature.MSPlacedFeatures;
import com.mcastudios.minestuck.world.gen.structure.blocks.StructureBlockRegistry;
import com.mcastudios.minestuck.world.lands.LandBiomeGenBuilder;
import com.mcastudios.minestuck.world.lands.LandProperties;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.phys.Vec3;

public class ClockworkLandType extends TitleLandType
{
	public static final String CLOCKWORK = "minestuck.clockwork";
	public static final String GEARS = "minestuck.gears";
	
	public ClockworkLandType()
	{
		super(EnumAspect.TIME);
	}
	
	@Override
	public String[] getNames()
	{
		return new String[]{CLOCKWORK, GEARS};
	}
	
	@Override
	public void registerBlocks(StructureBlockRegistry registry)
	{
		registry.setBlock("structure_wool_2", Blocks.LIGHT_GRAY_WOOL);
		registry.setBlock("carpet", Blocks.RED_CARPET);
	}
	
	@Override
	public void setProperties(LandProperties properties)
	{
		properties.mergeFogColor(new Vec3(0.5, 0.5, 0.5), 0.5F);
	}
	
	@Override
	public void addBiomeGeneration(LandBiomeGenBuilder builder, StructureBlockRegistry blocks, LandBiomeSetType biomeSet)
	{
		builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MSPlacedFeatures.COG, LandBiomeType.ROUGH);
		builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MSPlacedFeatures.UNCOMMON_COG, LandBiomeType.anyExcept(LandBiomeType.ROUGH));
		
		builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MSPlacedFeatures.FLOOR_COG, LandBiomeType.OCEAN);
		builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MSPlacedFeatures.UNCOMMON_FLOOR_COG, LandBiomeType.anyExcept(LandBiomeType.OCEAN));
	}
	
	@Override
	public SoundEvent getBackgroundMusic()
	{
		return MSSoundEvents.MUSIC_CLOCKWORK.get();
	}
}