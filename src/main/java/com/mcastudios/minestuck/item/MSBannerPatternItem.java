package com.mcastudios.minestuck.item;

import com.mcastudios.minestuck.Minestuck;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Minestuck.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MSBannerPatternItem
{
	public static final BannerPattern VOID_PATTERN = BannerPattern.create("void_pattern", "minecraft:msu_void", "msvop");
	public static final BannerPattern LIGHT_PATTERN = BannerPattern.create("light_pattern", "minecraft:msu_light", "mslip");
	public static final BannerPattern SPACE_PATTERN = BannerPattern.create("space_pattern", "minecraft:msu_space", "msspp");
	public static final BannerPattern TIME_PATTERN = BannerPattern.create("time_pattern", "minecraft:msu_time", "mstip");
	public static final BannerPattern MIND_PATTERN = BannerPattern.create("mind_pattern", "minecraft:msu_mind", "msmip");
	public static final BannerPattern HEART_PATTERN = BannerPattern.create("heart_pattern", "minecraft:msu_heart", "mshep");
	public static final BannerPattern RAGE_PATTERN = BannerPattern.create("rage_pattern", "minecraft:msu_rage", "msrap");
	public static final BannerPattern HOPE_PATTERN = BannerPattern.create("hope_pattern", "minecraft:msu_hope", "mshop");
	public static final BannerPattern LIFE_PATTERN = BannerPattern.create("life_pattern", "minecraft:msu_life", "mslfp");
	public static final BannerPattern DOOM_PATTERN = BannerPattern.create("doom_pattern", "minecraft:msu_doom", "msdop");
	public static final BannerPattern BREATH_PATTERN = BannerPattern.create("breath_pattern", "minecraft:msu_breath", "msbrp");
	public static final BannerPattern BLOOD_PATTERN = BannerPattern.create("blood_pattern", "minecraft:msu_blood", "msblp");
	
	public static final BannerPattern MOON_PATTERN = BannerPattern.create("moon_pattern", "minecraft:msu_moon", "msmop");
}