package com.mcastudios.minestuck.block;

import com.mcastudios.minestuck.item.MSItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ZillystoneBlock extends Block
{
	public ZillystoneBlock(Properties properties)
	{
		super(properties);
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
	{
		ItemStack handStack = player.getItemInHand(handIn);
		ItemStack chisel = new ItemStack(MSItems.MOONSTONE_CHISEL.get());
		
		if(ItemStack.isSame(handStack, chisel))
		{
			if(!level.isClientSide)
			{
				level.playSound(null, pos, SoundEvents.VILLAGER_WORK_MASON, SoundSource.BLOCKS, 1.0F, 1.0F);
				
				ItemStack newStack = MSItems.ZILLYSTONE_SHARD.get().getDefaultInstance();
				player.addItem(newStack);
			}
			
			return InteractionResult.SUCCESS;
		} else
		{
			return InteractionResult.FAIL;
		}
	}
}
