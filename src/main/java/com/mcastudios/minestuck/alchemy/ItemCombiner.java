package com.mcastudios.minestuck.alchemy;

import net.minecraft.world.Container;

public interface ItemCombiner extends Container
{
	CombinationMode getMode();
}