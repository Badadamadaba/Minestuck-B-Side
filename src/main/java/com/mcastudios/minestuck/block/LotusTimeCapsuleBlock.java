package com.mcastudios.minestuck.block;

import com.mcastudios.minestuck.block.machine.MachineMultiblock;
import com.mcastudios.minestuck.block.machine.MultiMachineBlock;
import com.mcastudios.minestuck.util.CustomVoxelShape;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class LotusTimeCapsuleBlock extends MultiMachineBlock
{
	protected final Map<Direction, VoxelShape> shape;
	
	public LotusTimeCapsuleBlock(MachineMultiblock machine, CustomVoxelShape shape, Properties properties)
	{
		super(machine, properties);
		this.shape = shape.createRotatedShapes();
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
	{
		return shape.get(state.getValue(FACING));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirror)
	{
		Direction direction = state.getValue(FACING);
		if(mirror != Mirror.NONE)
		{
			boolean clockwise = (mirror == Mirror.LEFT_RIGHT) ^ (direction.getAxis() == Direction.Axis.X); //fixes generation issue
			if(clockwise)
				return state.setValue(FACING, direction.getClockWise());
			else
				return state.setValue(FACING, direction.getCounterClockWise());
		}
		return state;
	}
}