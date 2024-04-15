package com.mcastudios.minestuck.item.artifact;

import com.mcastudios.minestuck.entry.EntryProcess;
import com.mcastudios.minestuck.item.AlchemizedColored;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;

public abstract class CruxiteArtifactItem extends Item implements AlchemizedColored
{
	public CruxiteArtifactItem(Properties properties)
	{
		super(properties);
	}
	
	public void onArtifactActivated(ServerPlayer player)
	{
		EntryProcess process = new EntryProcess();
		process.onArtifactActivated(player);
	}
}