package com.mcastudios.minestuck.blockentity.machine;

import com.mcastudios.minestuck.player.PlayerIdentifier;

public interface IOwnable
{
	void setOwner(PlayerIdentifier identifier);
	PlayerIdentifier getOwner();
}
