package com.mcastudios.minestuck.inventory.captchalogue;

import com.mcastudios.minestuck.item.MSItems;
import com.mcastudios.minestuck.alchemy.AlchemyHelper;
import com.mcastudios.minestuck.player.PlayerSavedData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;

public class QueueStackModus extends StackModus
{
	
	public QueueStackModus(ModusType<? extends QueueStackModus> type, PlayerSavedData savedData, LogicalSide side)
	{
		super(type, savedData, side);
	}
	
	@Override
	public ItemStack getItem(ServerPlayer player, int id, boolean asCard)
	{
		if(id == CaptchaDeckHandler.EMPTY_CARD)
		{
			if(list.size() < size)
			{
				size--;
				markDirty();
				return new ItemStack(MSItems.CAPTCHA_CARD.get());
			} else return ItemStack.EMPTY;
		}
		
		if(list.isEmpty())
			return ItemStack.EMPTY;
		
		if(id == CaptchaDeckHandler.EMPTY_SYLLADEX)
		{
			for(ItemStack item : list)
				CaptchaDeckHandler.launchAnyItem(player, item);
			list.clear();
			markDirty();
			return ItemStack.EMPTY;
		}
		
		if(asCard)
		{
			size--;
			markDirty();
			return AlchemyHelper.createCard(id == 0 ? list.removeFirst() : list.removeLast(), false);
		}
		else
		{
			markDirty();
			return id == 0 ? list.removeFirst() : list.removeLast();
		}
	}
}