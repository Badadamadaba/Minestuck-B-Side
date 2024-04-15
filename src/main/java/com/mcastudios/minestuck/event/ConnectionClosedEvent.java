package com.mcastudios.minestuck.event;

import com.mcastudios.minestuck.skaianet.SburbConnection;
import com.mcastudios.minestuck.skaianet.Session;
import net.minecraft.server.MinecraftServer;

public class ConnectionClosedEvent extends SburbEvent
{
	private final ConnectionCreatedEvent.ConnectionType connectionType;
	
	public ConnectionClosedEvent(MinecraftServer mcServer, SburbConnection connection, Session session, ConnectionCreatedEvent.ConnectionType connectionType)
	{
		super(mcServer, connection, session);
		this.connectionType = connectionType;
	}
	
	public ConnectionCreatedEvent.ConnectionType getConnectionType()
	{
		return connectionType;
	}
}
