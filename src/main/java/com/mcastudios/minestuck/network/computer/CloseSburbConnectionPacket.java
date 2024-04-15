package com.mcastudios.minestuck.network.computer;

import com.mcastudios.minestuck.network.PlayToServerPacket;
import com.mcastudios.minestuck.skaianet.SkaianetHandler;
import com.mcastudios.minestuck.blockentity.ComputerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class CloseSburbConnectionPacket implements PlayToServerPacket
{
	private final BlockPos pos;
	private final boolean isClient;
	
	private CloseSburbConnectionPacket(BlockPos pos, boolean isClient)
	{
		this.pos = pos;
		this.isClient = isClient;
	}
	
	public static CloseSburbConnectionPacket asClient(ComputerBlockEntity be)
	{
		return new CloseSburbConnectionPacket(be.getBlockPos(), true);
	}
	
	public static CloseSburbConnectionPacket asServer(ComputerBlockEntity be)
	{
		return new CloseSburbConnectionPacket(be.getBlockPos(), false);
	}
	
	@Override
	public void encode(FriendlyByteBuf buffer)
	{
		buffer.writeBlockPos(pos);
		buffer.writeBoolean(isClient);
	}
	
	public static CloseSburbConnectionPacket decode(FriendlyByteBuf buffer)
	{
		BlockPos computer = buffer.readBlockPos();
		boolean isClient = buffer.readBoolean();
		return new CloseSburbConnectionPacket(computer, isClient);
	}
	
	@Override
	public void execute(ServerPlayer player)
	{
		ComputerBlockEntity.forNetworkIfPresent(player, pos, computer -> {
			if(isClient)
				SkaianetHandler.get(player.server).closeClientConnection(computer);
			else SkaianetHandler.get(player.server).closeServerConnection(computer);
		});
	}
}