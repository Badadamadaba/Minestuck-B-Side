package com.mcastudios.minestuck.world.lands.title;

import com.mcastudios.minestuck.block.MSBlocks;
import com.mcastudios.minestuck.player.EnumAspect;
import com.mcastudios.minestuck.util.MSSoundEvents;
import com.mcastudios.minestuck.world.biome.LandBiomeSetType;
import com.mcastudios.minestuck.world.biome.LandBiomeType;
import com.mcastudios.minestuck.world.gen.LandGenSettings;
import com.mcastudios.minestuck.world.gen.feature.FeatureModifier;
import com.mcastudios.minestuck.world.gen.feature.MSPlacedFeatures;
import com.mcastudios.minestuck.world.gen.structure.blocks.StructureBlockRegistry;
import com.mcastudios.minestuck.world.lands.LandBiomeGenBuilder;
import com.mcastudios.minestuck.world.lands.LandProperties;
import com.mcastudios.minestuck.world.lands.terrain.TerrainLandType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PulseLandType extends TitleLandType
{
	public static final String PULSE = "minestuck.pulse";
	public static final String BLOOD = "minestuck.blood";
	
	public PulseLandType()
	{
		super(EnumAspect.BLOOD);
	}
	
	@Override
	public String[] getNames()
	{
		return new String[]{PULSE, BLOOD};
	}
	
	@Override
	public void registerBlocks(StructureBlockRegistry registry)
	{
		registry.setBlock("structure_wool_2", Blocks.RED_WOOL);
		registry.setBlock("carpet", Blocks.BROWN_CARPET);
		
		registry.setBlock("ocean", MSBlocks.BLOOD);
		registry.setBlock("river", MSBlocks.BLOOD);
		registry.setBlock("slime", MSBlocks.COAGULATED_BLOOD);
	}
	
	@Override
	public void setProperties(LandProperties properties)
	{
		properties.mergeFogColor(new Vec3(0.8, 0, 0), 0.8F);
	}
	
	@Override
	public void setGenSettings(LandGenSettings settings)
	{
		settings.oceanThreshold = Math.max(settings.oceanThreshold, -0.4F);
	}
	
	@Override
	public void addBiomeGeneration(LandBiomeGenBuilder builder, StructureBlockRegistry blocks, LandBiomeSetType biomeSet)
	{
		builder.addModified(GenerationStep.Decoration.UNDERGROUND_ORES, MSPlacedFeatures.COAGULATED_BLOOD_DISK,
				FeatureModifier.withTargets(List.of(blocks.getBlockState("surface"), blocks.getBlockState("upper"))), LandBiomeType.ROUGH);
	}
	
	@Override
	public boolean isAspectCompatible(TerrainLandType otherType)
	{
		StructureBlockRegistry registry = new StructureBlockRegistry();
		otherType.registerBlocks(registry);
		return registry.getBlockState("ocean").getMaterial() != Material.LAVA;	//Lava is likely a too important part of the terrain aspect to be replaced
	}
	
	@Override
	public SoundEvent getBackgroundMusic()
	{
		return MSSoundEvents.MUSIC_PULSE.get();
	}
}