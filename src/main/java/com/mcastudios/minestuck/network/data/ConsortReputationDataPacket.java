package com.mcastudios.minestuck.network.data;

import com.mcastudios.minestuck.network.PlayToClientPacket;
import com.mcastudios.minestuck.player.ClientPlayerData;
import net.minecraft.network.FriendlyByteBuf;

public class ConsortReputationDataPacket implements PlayToClientPacket
{
    private final int count;
    
    private ConsortReputationDataPacket(int count)
    {
        this.count = count;
    }
    
    public static ConsortReputationDataPacket create(int count)
    {
        return new ConsortReputationDataPacket(count);
    }
    
    @Override
    public void encode(FriendlyByteBuf buffer)
    {
        buffer.writeInt(count);
    }
    
    public static ConsortReputationDataPacket decode(FriendlyByteBuf buffer)
    {
        int count = buffer.readInt();
        return create(count);
    }
    
    @Override
    public void execute()
    {
        ClientPlayerData.handleDataPacket(this);
    }
    
    public int getCount()
    {
        return count;
    }
}
