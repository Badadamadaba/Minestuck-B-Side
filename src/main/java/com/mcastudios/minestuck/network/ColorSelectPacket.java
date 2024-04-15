package com.mcastudios.minestuck.network;

import com.mcastudios.minestuck.util.ColorHandler;
import com.mcastudios.minestuck.player.PlayerSavedData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class ColorSelectPacket implements PlayToServerPacket
{
	private final int colorIndex;
	
	public ColorSelectPacket(int colorIndex)
	{
		this.colorIndex = colorIndex;
	}
	
	@Override
	public void encode(FriendlyByteBuf buffer)
	{
		buffer.writeInt(colorIndex);
	}
	
	public static ColorSelectPacket decode(FriendlyByteBuf buffer)
	{
		int color = buffer.readInt();
		
		return new ColorSelectPacket(color);
	}
	
	@Override
	public void execute(ServerPlayer player)
	{
		PlayerSavedData.getData(player).trySetColor(ColorHandler.getColor(colorIndex));
	}
}