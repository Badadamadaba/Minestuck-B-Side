package com.mcastudios.minestuck.data.tag;

import com.mcastudios.minestuck.Minestuck;
import com.mcastudios.minestuck.util.MSTags;
import com.mcastudios.minestuck.world.gen.structure.MSConfiguredStructures;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class MSStructureTagsProvider extends TagsProvider<ConfiguredStructureFeature<?, ?>>
{
	public MSStructureTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(generator, BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, Minestuck.MOD_ID, existingFileHelper);
	}
	
	@Override
	protected void addTags()
	{
		this.tag(MSTags.Structures.SCANNER_LOCATED).add(MSConfiguredStructures.FROG_TEMPLE.get());
	}
	
	@Override
	public String getName()
	{
		return "Minestuck Structure Tags";
	}
}
