package com.mcastudios.minestuck.world.lands.terrain;

import com.mcastudios.minestuck.entity.MSEntityTypes;
import com.mcastudios.minestuck.world.gen.structure.blocks.StructureBlockRegistry;

public class NullTerrainLandType extends TerrainLandType
{
	
	public NullTerrainLandType()
	{
		super(new Builder(MSEntityTypes.SALAMANDER).unavailable().names("null")
				.fogColor(1, 1, 1));
	}
	
	@Override
	public void registerBlocks(StructureBlockRegistry blocks)
	{
	
	}
}