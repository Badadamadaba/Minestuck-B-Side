package com.mcastudios.minestuck.item;

import com.mcastudios.minestuck.util.MSSoundEvents;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GrimoireItem extends Item
{
	
	public GrimoireItem(Properties properties)
	{
		super(properties);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn)
	{
		playerIn.level.playSound(playerIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), MSSoundEvents.ITEM_GRIMOIRE_USE.get(), SoundSource.AMBIENT, 0.5F, 0.8F);
		if(level.isClientSide)
			playerIn.sendMessage(new TranslatableComponent(getDescriptionId() + ".message"), Util.NIL_UUID);
		return super.use(level, playerIn, handIn);
	}
}