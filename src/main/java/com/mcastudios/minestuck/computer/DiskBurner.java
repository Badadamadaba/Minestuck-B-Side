package com.mcastudios.minestuck.computer;

import com.mcastudios.minestuck.blockentity.ComputerBlockEntity;
import com.mcastudios.minestuck.network.MSPacketHandler;
import com.mcastudios.minestuck.network.computer.*;

import java.util.ArrayList;

public class DiskBurner extends ButtonListProgram
{
	public static final String NAME = "minestuck.program.disk_burner.name";
	public static final String BURN_SERVER_DISK = "minestuck.program.disk_burner.burn_server_disk";
	public static final String BURN_CLIENT_DISK = "minestuck.program.disk_burner.burn_client_disk";
	public static final String CHOOSE = "minestuck.program.disk_burner.choose";
	
	@Override
	public ArrayList<UnlocalizedString> getStringList(ComputerBlockEntity be)
	{
		ArrayList<UnlocalizedString> list = new ArrayList<>();
		list.add(new UnlocalizedString(CHOOSE));
		
		if(be != null && be.hasAllCode() && be.blankDisksStored > 0)
		{
			list.add(new UnlocalizedString(BURN_SERVER_DISK));
			list.add(new UnlocalizedString(BURN_CLIENT_DISK));
		}
		
		return list;
	}
	
	@Override
	public void onButtonPressed(ComputerBlockEntity be, String buttonName, Object[] data)
	{
		if(buttonName.equals(BURN_CLIENT_DISK))
		{
			MSPacketHandler.sendToServer(BurnDiskPacket.create(be, 0));
		} else if(buttonName.equals(BURN_SERVER_DISK))
		{
			MSPacketHandler.sendToServer(BurnDiskPacket.create(be, 1));
		}
	}
	
	@Override
	public String getName()
	{
		return NAME;
	}
}