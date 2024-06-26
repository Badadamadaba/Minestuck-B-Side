package com.mcastudios.minestuck.item;

import com.mcastudios.minestuck.block.CassettePlayerBlock;
import com.mcastudios.minestuck.block.EnumCassetteType;
import com.mcastudios.minestuck.block.MSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CassetteItem extends RecordItem
{
	public final EnumCassetteType cassetteID;
	
	public CassetteItem(int comparatorValueIn, EnumCassetteType cassetteName, Properties builder)
	{
		super(comparatorValueIn, cassetteName::getSoundEvent, builder);
		this.cassetteID = cassetteName;
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context)
	{
		Level level = context.getLevel();
		BlockPos blockpos = context.getClickedPos();
		BlockState blockstate = level.getBlockState(blockpos);
		if(blockstate.getBlock() == MSBlocks.CASSETTE_PLAYER.get() && blockstate.getValue(CassettePlayerBlock.CASSETTE) == EnumCassetteType.NONE && blockstate.getValue(CassettePlayerBlock.OPEN))
		{
			ItemStack itemstack = context.getItemInHand();
			if(!level.isClientSide)
			{
				(MSBlocks.CASSETTE_PLAYER.get()).insertCassette(level, blockpos, blockstate, itemstack);
				itemstack.shrink(1);
			}
			
			return InteractionResult.SUCCESS;
		} else
		{
			return InteractionResult.PASS;
		}
	}
}
