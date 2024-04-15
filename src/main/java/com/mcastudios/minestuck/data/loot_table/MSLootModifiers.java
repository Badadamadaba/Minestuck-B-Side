package com.mcastudios.minestuck.data.loot_table;

import com.mcastudios.minestuck.Minestuck;
import com.mcastudios.minestuck.item.MSItemTypes;
import com.mcastudios.minestuck.item.loot.GristLootModifier;
import com.mcastudios.minestuck.item.loot.MSLootTables;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.CanToolPerformAction;

public class MSLootModifiers extends GlobalLootModifierProvider
{
	public MSLootModifiers(DataGenerator gen)
	{
		super(gen, Minestuck.MOD_ID);
	}
	
	@Override
	protected void start()
	{
		add("grist_mining", MSLootTables.GRIST_MODIFIER.get(), new GristLootModifier(
				new LootItemCondition[]{new CanToolPerformAction(MSItemTypes.GRIST_HARVEST)}, 1));
	}
}
