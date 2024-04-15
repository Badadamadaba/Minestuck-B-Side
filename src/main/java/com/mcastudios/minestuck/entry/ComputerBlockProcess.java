package com.mcastudios.minestuck.entry;

import com.mcastudios.minestuck.skaianet.SkaianetHandler;
import com.mcastudios.minestuck.blockentity.ComputerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class ComputerBlockProcess implements EntryBlockProcessing
{
	@Override
	public void copyOver(ServerLevel oldWorld, BlockPos oldPos, ServerLevel newWorld, BlockPos newPos, BlockState state, @Nullable BlockEntity oldBE, @Nullable BlockEntity newBE)
	{
		if(oldBE instanceof ComputerBlockEntity && newBE instanceof ComputerBlockEntity)
			SkaianetHandler.get(oldWorld).movingComputer((ComputerBlockEntity) oldBE, (ComputerBlockEntity) newBE);
	}
}