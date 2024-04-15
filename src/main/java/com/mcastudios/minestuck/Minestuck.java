package com.mcastudios.minestuck;

import com.mcastudios.minestuck.advancements.MSCriteriaTriggers;
import com.mcastudios.minestuck.block.MSBlocks;
import com.mcastudios.minestuck.client.ClientProxy;
import com.mcastudios.minestuck.command.argument.*;
import com.mcastudios.minestuck.computer.ProgramData;
import com.mcastudios.minestuck.computer.editmode.DeployList;
import com.mcastudios.minestuck.effects.MSEffects;
import com.mcastudios.minestuck.entity.MSEntityTypes;
import com.mcastudios.minestuck.entity.consort.ConsortDialogue;
import com.mcastudios.minestuck.entry.ComputerBlockProcess;
import com.mcastudios.minestuck.entry.EntryProcess;
import com.mcastudios.minestuck.entry.RSEntryBlockProcess;
import com.mcastudios.minestuck.entry.TransportalizerBlockProcess;
import com.mcastudios.minestuck.fluid.MSFluids;
import com.mcastudios.minestuck.inventory.MSMenuTypes;
import com.mcastudios.minestuck.inventory.captchalogue.ModusTypes;
import com.mcastudios.minestuck.item.MSBannerPatternItem;
import com.mcastudios.minestuck.item.MSItems;
import com.mcastudios.minestuck.alchemy.GristTypes;
import com.mcastudios.minestuck.alchemy.generator.recipe.InterpreterSerializers;
import com.mcastudios.minestuck.item.crafting.MSRecipeTypes;
import com.mcastudios.minestuck.item.loot.MSLootTables;
import com.mcastudios.minestuck.network.MSPacketHandler;
import com.mcastudios.minestuck.player.KindAbstratusList;
import com.mcastudios.minestuck.blockentity.MSBlockEntityTypes;
import com.mcastudios.minestuck.util.MSSoundEvents;
import com.mcastudios.minestuck.world.gen.MSDensityFunctions;
import com.mcastudios.minestuck.world.gen.MSNoiseParameters;
import com.mcastudios.minestuck.world.gen.MSSurfaceRules;
import com.mcastudios.minestuck.world.gen.MSWorldGenTypes;
import com.mcastudios.minestuck.world.gen.feature.*;
import com.mcastudios.minestuck.world.gen.structure.*;
import com.mcastudios.minestuck.world.lands.LandTypes;
import net.minecraft.commands.synchronization.ArgumentSerializer;
import net.minecraft.commands.synchronization.ArgumentTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

import static com.mcastudios.minestuck.Minestuck.MOD_ID;

@Mod(MOD_ID)
public class Minestuck
{
	public static final String MOD_NAME = "Minestuck";
	public static final String MOD_ID = "minestuck";
	
	public Minestuck()
	{
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MinestuckConfig.commonSpec);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, MinestuckConfig.clientSpec);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MinestuckConfig.serverSpec);
		
		GeckoLib.initialize();
		
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		MSBlocks.REGISTER.register(eventBus);
		MSItems.REGISTER.register(eventBus);
		MSFluids.REGISTER.register(eventBus);
		MSBlockEntityTypes.REGISTER.register(eventBus);
		MSEntityTypes.REGISTER.register(eventBus);
		MSMenuTypes.REGISTER.register(eventBus);
		GristTypes.GRIST_TYPES.register(eventBus);
		MSEffects.REGISTER.register(eventBus);
		MSSoundEvents.REGISTER.register(eventBus);
		LandTypes.TERRAIN_REGISTER.register(eventBus);
		LandTypes.TITLE_REGISTER.register(eventBus);
		InterpreterSerializers.REGISTER.register(eventBus);
		MSRecipeTypes.RECIPE_TYPE_REGISTER.register(eventBus);
		MSRecipeTypes.SERIALIZER_REGISTER.register(eventBus);
		MSLootTables.CONDITION_REGISTER.register(eventBus);
		MSLootTables.FUNCTION_REGISTER.register(eventBus);
		MSLootTables.ENTRY_REGISTER.register(eventBus);
		MSLootTables.MODIFIER_REGISTER.register(eventBus);
		ModusTypes.REGISTER.register(eventBus);
		
		MSNoiseParameters.REGISTER.register(eventBus);
		MSDensityFunctions.REGISTER.register(eventBus);
		MSFeatures.REGISTER.register(eventBus);
		MSCFeatures.REGISTER.register(eventBus);
		MSPlacedFeatures.REGISTER.register(eventBus);
		
		MSStructurePieces.REGISTER.register(eventBus);
		MSStructures.REGISTER.register(eventBus);
		MSConfiguredStructures.REGISTER.register(eventBus);
		MSStructurePlacements.REGISTER.register(eventBus);
		MSStructureSets.REGISTER.register(eventBus);
		
		MSStructureProcessorTypes.REGISTER.register(eventBus);
		MSSurfaceRules.REGISTER.register(eventBus);
		MSWorldGenTypes.REGISTER.register(eventBus);
	}
	
	/**
	 * Common setup, which happens in parallel with other mods.
	 * Only do thread-safe setup, such as internal setup of thread-safe registering.
	 */
	private void setup(final FMLCommonSetupEvent event)
	{
		event.enqueueWork(this::mainThreadSetup);
		
		//register channel handler
		MSPacketHandler.setupChannel();
	}
	
	/**
	 * Handles any setup that is not thread-safe, and thus need to happen on the main thread.
	 * Typically meant for registering stuff.
	 */
	private void mainThreadSetup()
	{
		MSCriteriaTriggers.register();
		MSEntityTypes.registerPlacements();
		
		ConsortDialogue.init();
		
		KindAbstratusList.registerTypes();
		DeployList.registerItems();
		
		ProgramData.registerProgram(0, new ItemStack(MSItems.CLIENT_DISK.get()), ProgramData::onClientClosed);
		ProgramData.registerProgram(1, new ItemStack(MSItems.SERVER_DISK.get()), ProgramData::onServerClosed);
		
		EntryProcess.addBlockProcessing(new ComputerBlockProcess());
		EntryProcess.addBlockProcessing(new TransportalizerBlockProcess());
		if(ModList.get().isLoaded("refinedstorage"))
			EntryProcess.addBlockProcessing(new RSEntryBlockProcess());
		
		ArgumentTypes.register("minestuck:grist_type", GristTypeArgument.class, GristTypeArgument.SERIALIZER);
		ArgumentTypes.register("minestuck:grist_set", GristSetArgument.class, GristSetArgument.SERIALIZER);
		ArgumentTypes.register("minestuck:terrain_land", TerrainLandTypeArgument.class, TerrainLandTypeArgument.SERIALIZER);
		ArgumentTypes.register("minestuck:title_land", TitleLandTypeArgument.class, TitleLandTypeArgument.SERIALIZER);
		ArgumentTypes.register("minestuck:land_type_pair", LandTypePairArgument.class, LandTypePairArgument.SERIALIZER);
		ArgumentTypes.register("minestuck:title", TitleArgument.class, TitleArgument.SERIALIZER);
		//noinspection unchecked,rawtypes
		ArgumentTypes.register("minestuck:list", ListArgument.class, (ArgumentSerializer) ListArgument.SERIALIZER);
		
	}
	
	private void clientSetup(final FMLClientSetupEvent event)
	{
		ClientProxy.init();
	}
}