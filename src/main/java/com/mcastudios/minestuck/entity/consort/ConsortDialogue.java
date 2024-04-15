package com.mcastudios.minestuck.entity.consort;

import com.mcastudios.minestuck.entity.consort.EnumConsort.MerchantType;
import com.mcastudios.minestuck.entity.consort.MessageType.*;
import com.mcastudios.minestuck.item.loot.MSLootTables;
import com.mcastudios.minestuck.skaianet.SburbHandler;
import com.mcastudios.minestuck.util.MSTags;
import com.mcastudios.minestuck.world.MSDimensions;
import com.mcastudios.minestuck.world.lands.LandTypeConditions;
import com.mcastudios.minestuck.world.lands.LandTypePair;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

import static com.mcastudios.minestuck.item.loot.MSLootTables.CONSORT_FOOD_STOCK;
import static com.mcastudios.minestuck.item.loot.MSLootTables.CONSORT_GENERAL_STOCK;
import static com.mcastudios.minestuck.world.lands.LandTypeConditions.*;
import static com.mcastudios.minestuck.world.lands.LandTypes.*;

/**
 * Handles message registry, message selection and contains the main message
 * class, which combines conditioning and a MessageType
 *
 * @author Kirderf1
 */
public class ConsortDialogue
{
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final List<DialogueWrapper> messages = new LinkedList<>();
	
	/**
	 * Make sure to call after land registry
	 */
	public static void init()	//TODO Could likely be exported to a json format
	{
		// Wind
		addMessage("dad_wind").condition(titleLandByGroup(WIND));
		addMessage(new ChainMessage(new SingleMessage("pyre.1"), new SingleMessage("pyre.2"))).condition(titleLandByGroup(WIND)).consort(EnumConsort.SALAMANDER, EnumConsort.TURTLE);
		
		// Pulse
		addMessage("koolaid").condition(titleLandByGroup(PULSE)).consort(EnumConsort.SALAMANDER, EnumConsort.TURTLE);
		addMessage("murder_rain").condition(titleLandByGroup(PULSE));
		addMessage("swimming").condition(titleLandByGroup(PULSE)).consort(EnumConsort.IGUANA, EnumConsort.TURTLE);
		addMessage("blood_surprise").condition(titleLandByGroup(PULSE)).consort(EnumConsort.IGUANA, EnumConsort.NAKAGATOR);
		
		// Thunder
		addMessage("skeleton_horse").condition(titleLandByGroup(THUNDER));
		addMessage("blue_moon").condition(titleLandByGroup(THUNDER)).consort(EnumConsort.SALAMANDER, EnumConsort.IGUANA);
		addMessage("lightning_strike").condition(titleLandByGroup(THUNDER)).consort(EnumConsort.TURTLE);
		addMessage(new ChainMessage(new SingleMessage("reckoning.1"), new SingleMessage("reckoning.2"), new SingleMessage("reckoning.3", "consort_type"))).condition(titleLandByGroup(THUNDER));
		addMessage(new ChainMessage(new SingleMessage("thunder_death.1"), new SingleMessage("thunder_death.2"))).condition(titleLandByGroup(THUNDER)).condition(terrainLandByGroup(WOOD));
		addMessage("hardcore").condition(titleLandByGroup(THUNDER)).condition(terrainLandByGroup(HEAT));
		addMessage(new ChainMessage(new SingleMessage("thunder_throw.1"), new SingleMessage("thunder_throw.2"))).condition(titleLandByGroup(THUNDER)).consort(EnumConsort.TURTLE, EnumConsort.SALAMANDER);
		
		//Rabbits
		addMessage("bunny_birthday").condition(titleLandByGroup(RABBITS)).consort(EnumConsort.NAKAGATOR, EnumConsort.SALAMANDER);
		addMessage("rabbit_eating").condition(titleLandByGroup(RABBITS)).consort(EnumConsort.TURTLE, EnumConsort.SALAMANDER);
		addMessage("edgy_life_hatred").condition(titleLandByGroup(RABBITS)).consort(EnumConsort.IGUANA, EnumConsort.NAKAGATOR);
		addMessage("rabbit.food_shortage.1").condition(titleLandByGroup(RABBITS)).condition(terrainLandByGroup(SAND, SANDSTONE));
		addMessage(new ChainMessage(0, "rabbit.foodShortage.2", new SingleMessage("rabbit.food_shortage.1"), new SingleMessage("rabbit.food_shortage.2"))).condition(titleLandByGroup(RABBITS)).condition(terrainLandByGroup(ROCK));
		addMessage(new ChainMessage(0, "rabbit.food.1", new SingleMessage("rabbit.food.1"), new SingleMessage("rabbit.food.2a"))).condition(titleLandByGroup(RABBITS)).condition(terrainLandByGroup(ROCK, SANDSTONE));
		addMessage(new ChainMessage(0, "rabbit.food.2", new SingleMessage("rabbit.food.1"), new SingleMessage("rabbit.food.2a"), new SingleMessage("rabbit.food.3a"))).condition(titleLandByGroup(RABBITS)).condition(terrainLandByGroup(SAND));
		addMessage(new ChainMessage(0, "rabbit.food.3", new SingleMessage("rabbit.food.1"), new SingleMessage("rabbit.food.2b"))).condition(titleLandByGroup(RABBITS)).condition(terrainLandByGroup(WOOD, SHADE));
		
		//Monsters
		addMessage(new SingleMessage("pet_zombie")).condition(titleLandByGroup(MONSTERS)).consort(EnumConsort.NAKAGATOR, EnumConsort.SALAMANDER);
		addMessage("spider_raid").condition(titleLand(MONSTERS));
		addMessage("monstersona").condition(titleLandByGroup(MONSTERS)).consort(EnumConsort.IGUANA, EnumConsort.NAKAGATOR);
		
		//Towers
		addMessage("bug_treasure").condition(titleLandByGroup(TOWERS)).consort(EnumConsort.SALAMANDER, EnumConsort.IGUANA);
		addMessage("tower_gone").condition(titleLandByGroup(TOWERS)).consort(EnumConsort.TURTLE, EnumConsort.SALAMANDER);
		addMessage("no_tower_treasure").condition(titleLandByGroup(TOWERS)).consort(EnumConsort.IGUANA, EnumConsort.NAKAGATOR);
		
		//Thought
		addMessage("glass_books").condition(titleLandByGroup(THOUGHT)).consort(EnumConsort.TURTLE, EnumConsort.IGUANA);
		addMessage("book_food").condition(titleLandByGroup(THOUGHT)).consort(EnumConsort.SALAMANDER, EnumConsort.NAKAGATOR);
		addMessage("to_eat").condition(titleLandByGroup(THOUGHT)).consort(EnumConsort.IGUANA, EnumConsort.NAKAGATOR);
		
		//Cake
		addMessage("mystery_recipe").condition(titleLandByGroup(CAKE)).consort(EnumConsort.TURTLE, EnumConsort.NAKAGATOR);
		addMessage("cake_regen").condition(titleLandByGroup(CAKE)).consort(EnumConsort.TURTLE, EnumConsort.SALAMANDER);
		addMessage("cake_recipe").condition(titleLandByGroup(CAKE)).consort(EnumConsort.IGUANA, EnumConsort.SALAMANDER);
		addMessage("fire_cakes").condition(terrainLandByGroup(HEAT)).condition(titleLandByGroup(CAKE));
		addMessage("frosting").condition(titleLandByGroup(CAKE)).condition(terrainLandByGroup(FROST));
		
		//Clockwork
		addMessage("gear_technology").condition(titleLandByGroup(CLOCKWORK)).consort(EnumConsort.SALAMANDER, EnumConsort.IGUANA);
		addMessage("evil_gears").condition(titleLandByGroup(CLOCKWORK)).consort(EnumConsort.NAKAGATOR, EnumConsort.IGUANA);
		addMessage("ticking").condition(titleLandByGroup(CLOCKWORK)).consort(EnumConsort.TURTLE, EnumConsort.SALAMANDER);
		
		//Frogs
		addMessage("frog_creation").condition(titleLandByGroup(FROGS));
		addMessage("frog_location").condition(titleLandByGroup(FROGS));
		addMessage("frog_imitation").condition(titleLandByGroup(FROGS));
		addMessage(new ChainMessage(new SingleMessage("frog_variants.1"), new SingleMessage("frog_variants.2", "land_name"))).condition(titleLandByGroup(FROGS));
		addMessage("frog_hatred").condition(titleLandByGroup(FROGS));
		addMessage(new ChainMessage(new SingleMessage("grasshopper_fishing.1"), new SingleMessage("grasshopper_fishing.2"))).condition(titleLandByGroup(FROGS)).consort(EnumConsort.SALAMANDER, EnumConsort.IGUANA);
		addMessage("gay_frogs").condition(titleLandByGroup(FROGS)).condition(terrainLand(RAINBOW));
		addMessage("non_teleporting_frogs").condition(titleLandByGroup(FROGS)).condition(terrainLand(END));
		
		//Buckets
		addMessage("lewd_buckets").condition(titleLandByGroup(BUCKETS));
		addMessage("water_buckets").condition(titleLandByGroup(BUCKETS)).condition(terrainLandByGroup(SAND));
		addMessage("warm_buckets").condition(titleLandByGroup(BUCKETS)).condition(terrainLandByGroup(FROST));
		addMessage(new ChainMessage(new SingleMessage("oil_buckets.1"), new SingleMessage("oil_buckets.2"))).condition(titleLandByGroup(BUCKETS)).condition(terrainLandByGroup(SHADE));
		
		//Light
		addMessage("blindness").condition(titleLandByGroup(LIGHT));
		addMessage("doctors_inside").condition(titleLandByGroup(LIGHT)).consort(EnumConsort.TURTLE);
		addMessage("staring").condition(titleLandByGroup(LIGHT));
		addMessage(new ChainMessage(new SingleMessage("sunglasses.1"), new SingleMessage("sunglasses.2"))).condition(titleLandByGroup(LIGHT)).condition(terrainLandByGroup(HEAT));
		addMessage(new ChainMessage(new SingleMessage("bright_snow.1"), new SingleMessage("bright_snow.2"))).condition(titleLandByGroup(LIGHT)).condition(terrainLandByGroup(FROST));
		addMessage("glimmering_snow").condition(titleLandByGroup(LIGHT)).condition(terrainLandByGroup(FROST));
		addMessage("glimmering_sand").condition(titleLandByGroup(LIGHT)).condition(terrainLandByGroup(SAND));
		addMessage("light_pillars").condition(titleLandByGroup(LIGHT)).consort(EnumConsort.IGUANA, EnumConsort.TURTLE);
		
		//Silence
		addMessage("murder_silence").condition(titleLandByGroup(SILENCE)).consort(EnumConsort.NAKAGATOR, EnumConsort.SALAMANDER);
		addMessage("silent_underlings").condition(titleLandByGroup(SILENCE));
		addMessage(new ChainMessage(new SingleMessage("listening.1"), new SingleMessage("listening.2"))).condition(titleLandByGroup(SILENCE)).consort(EnumConsort.IGUANA, EnumConsort.SALAMANDER);
		addMessage("calmness").condition(titleLandByGroup(SILENCE)).consort(EnumConsort.TURTLE, EnumConsort.IGUANA);
		
		//Towers
		addMessage("climb_high").condition(titleLandByGroup(TOWERS, WIND)).consort(EnumConsort.IGUANA);
		addMessage(new ConditionedMessage((ConsortEntity consort2, ServerPlayer player2) -> consort2.getY() < 78, new ChainMessage(new SingleMessage("height_fear.towers.1"), new SingleMessage("height_fear.towers.2")),
				new SingleMessage("height_fear.panic"))).condition(titleLandByGroup(TOWERS)).consort(EnumConsort.TURTLE);
		addMessage(new ConditionedMessage((ConsortEntity consort1, ServerPlayer player1) -> consort1.getY() < 78, new ChainMessage(new SingleMessage("height_fear.rock.1"), new SingleMessage("height_fear.rock.2")),
				new SingleMessage("height_fear.panic"))).condition(titleLandByGroup(WIND)).consort(EnumConsort.TURTLE);
		
		//Shade
		addMessage(new ChainMessage(2, new SingleMessage("mush_farm.1"), new SingleMessage("mush_farm.2"), new SingleMessage("mush_farm.3"),
				new SingleMessage("mush_farm.4"), new SingleMessage("mush_farm.5"), new SingleMessage("mush_farm.6"),
				new SingleMessage("mush_farm.7"))).condition(terrainLandByGroup(SHADE));
		addMessage(new ChoiceMessage(new SingleMessage("mushroom_pizza"),
				new SingleMessage[]{new SingleMessage("mushroom_pizza.on"), new SingleMessage("mushroom_pizza.off")},
				new MessageType[]{new SingleMessage("mushroom_pizza.on.consort_reply"), new SingleMessage("mushroom_pizza.off.consort_reply")})).condition(terrainLandByGroup(SHADE));
		addMessage("fire_hazard").condition(terrainLandByGroup(SHADE)).condition(titleLandByGroup(THUNDER).negate());
		
		//Heat
		addMessage("getting_hot").condition(terrainLandByGroup(HEAT));
		addMessage("step_into_fire").condition(terrainLandByGroup(HEAT));
		addMessage("lava_crickets").condition(terrainLandByGroup(HEAT));
		
		//Wood
		addMessage("wood_treatments").condition(terrainLandByGroup(WOOD));
		addMessage(new ChainMessage(new SingleMessage("splinters.1"), new SingleMessage("splinters.2"))).condition(terrainLandByGroup(WOOD));
		
		//Sand & Sandstone
		addMessage("sand_surfing").condition(terrainLandByGroup(SAND));
		addMessage(new ChoiceMessage(new SingleMessage("camel"), new SingleMessage[]{new SingleMessage("camel.yes"), new SingleMessage("camel.no")},
				new MessageType[]{new SingleMessage("camel.no_camel"), new SingleMessage("camel.dancing_camel")})).condition(terrainLandByGroup(SAND));
		addMessage("knockoff").condition(terrainLandByGroup(SANDSTONE));
		addMessage(new ChainMessage(new SingleMessage("sandless.1", "denizen"), new SingleMessage("sandless.2"))).condition(terrainLandByGroup(SANDSTONE));
		addMessage("red_better").condition(terrainLand(RED_SAND, RED_SANDSTONE));
		addMessage("yellow_better").condition(terrainLand(SAND, SANDSTONE));
		
		//Frost
		addMessage(new ChainMessage(new SingleMessage("frozen.1"), new DescriptionMessage("frozen.2"))).condition(terrainLandByGroup(FROST));
		addMessage(new ChoiceMessage(new SingleMessage("fur_coat"), new SingleMessage[]{new SingleMessage("fur_coat.pay"), new SingleMessage("fur_coat.ignore")},
				new MessageType[]{new PurchaseMessage(MSLootTables.CONSORT_JUNK_REWARD, 100, new ChainMessage(1, new SingleMessage("fur_coat.grattitude"), new SingleMessage("thank_you"))),
						new SingleMessage("fur_coat.death")})).condition(terrainLandByGroup(FROST));
		addMessage("tent_protection").condition(terrainLandByGroup(FROST)).consortReq(ConsortEntity::hasRestriction);
		addMessage("all_ores").condition(terrainLandByGroup(ROCK));
		addMessage("rockfu", "land_name").condition(terrainLandByGroup(ROCK));
		addMessage("all_trees").condition(terrainLandByGroup(FOREST));
		addMessage("really_likes_trees").condition(terrainLandByGroup(FOREST));
		
		//Fungi
		addMessage(new ChainMessage(new SingleMessage("mycelium.1"), new SingleMessage("mycelium.2"))).condition(terrainLandByGroup(FUNGI));
		addMessage(new ChainMessage(new SingleMessage("adaptation.1"), new SingleMessage("adaptation.2"))).condition(terrainLandByGroup(FUNGI));
		addMessage("mushroom_curse", "denizen").condition(terrainLandByGroup(FUNGI));
		addMessage("jacket").condition(terrainLandByGroup(FUNGI));
		addMessage("mildew").condition(terrainLandByGroup(FUNGI));
		addMessage("fungus_destroyer", "player_title_land", "denizen").condition(terrainLandByGroup(FUNGI));
		
		//Rainbow Terrain
		addMessage("generic_green").condition(terrainLandByGroup(RAINBOW));
		addMessage("overwhelming_colors").condition(terrainLandByGroup(RAINBOW));
		addMessage("saw_rainbow").condition(terrainLandByGroup(RAINBOW));
		addMessage("sunglasses").condition(terrainLandByGroup(RAINBOW));
		addMessage("what_is_wool").condition(terrainLandByGroup(RAINBOW));
		addMessage("love_colors").condition(terrainLandByGroup(RAINBOW));
		addMessage(new ChainMessage(new SingleMessage("types_of_colors.1"), new SingleMessage("types_of_colors.2"), new SingleMessage("types_of_colors.3"),
				new SingleMessage("types_of_colors.4"), new SingleMessage("types_of_colors.5"), new SingleMessage("types_of_colors.6"), new SingleMessage("types_of_colors.7"), new SingleMessage("types_of_colors.8"),
				new SingleMessage("types_of_colors.9"), new SingleMessage("types_of_colors.10"), new SingleMessage("types_of_colors.11"), new SingleMessage("types_of_colors.12"), new SingleMessage("types_of_colors.13"),
				new SingleMessage("types_of_colors.14"), new SingleMessage("types_of_colors.15"), new SingleMessage("types_of_colors.16"), new SingleMessage("types_of_colors.17"), new SingleMessage("types_of_colors.18"))).condition(terrainLandByGroup(RAINBOW));
		
		//End Terrain
		addMessage("at_the_end").condition(terrainLandByGroup(END));
		addMessage("chorus_fruit").condition(terrainLandByGroup(END));
		addMessage("end_grass").condition(terrainLandByGroup(END));
		addMessage("grass_curse", "denizen").condition(terrainLandByGroup(END));
		addMessage("tall_grass").condition(terrainLandByGroup(END));
		addMessage("useless_elytra").condition(terrainLandByGroup(END));
		
		//Rain terrain
		addMessage("empty_ocean", "denizen").condition(terrainLandByGroup(RAIN));
		addMessage("forbidden_snack").condition(terrainLandByGroup(RAIN));
		addMessage("cotton_candy").condition(terrainLandByGroup(RAIN));
		addMessage("monsters_below").condition(terrainLandByGroup(RAIN));
		addMessage("keep_swimming").condition(terrainLandByGroup(RAIN));
		
		//Flora Terrain
		addMessage("battle_site").condition(terrainLandByGroup(FLORA));
		addMessage("blood_oceans").condition(terrainLandByGroup(FLORA));
		addMessage("giant_swords").condition(terrainLandByGroup(FLORA));
		addMessage(new ChainMessage(new SingleMessage("bloodberries.1"), new SingleMessage("bloodberries.2"))).condition(terrainLandByGroup(FLORA));
		addMessage("sharp_slide").condition(terrainLandByGroup(FLORA));
		addMessage(new ChainMessage(new SingleMessage("immortality_herb.1"), new SingleMessage("immortality_herb.2"), new ExplosionMessage("immortality_herb.3"))).condition(terrainLandByGroup(FLORA)).lockToConsort();
		addMessage(new ChainMessage(new SingleMessage("spices.1"), new SingleMessage("spices.2", "land_name"))).condition(terrainLandByGroup(FLORA));
		
		//Misc
		addMessage("denizen_mention").reqLand();
		addMessage("floating_island").consortReq(consort -> consort.distanceToSqr(new Vec3(consort.level.getLevelData().getXSpawn(), consort.level.getLevelData().getYSpawn(), consort.level.getLevelData().getZSpawn())) < 65536).reqLand();
		addMessage("ring_fishing").consort(EnumConsort.SALAMANDER, EnumConsort.IGUANA);
		addMessage("frog_walk").consort(EnumConsort.TURTLE);
		addMessage("delicious_hair").consort(EnumConsort.IGUANA);
		addMessage("lazy_king").condition(terrainLandByGroup(SHADE));
		addMessage("music_invention").consort(EnumConsort.NAKAGATOR, EnumConsort.SALAMANDER);
		addMessage("wyrm").consort(EnumConsort.TURTLE, EnumConsort.IGUANA);
		addMessage(new ConditionedMessage((consort, player) -> SburbHandler.hasEntered(player),
				new SingleMessage("heroic_stench"), new SingleMessage("leech_stench"))).reqLand();
		
		MessageType raps = new RandomMessage("rap_battles", RandomKeepResult.KEEP_CONSORT,
				new DelayMessage(new int[] {17, 17, 30},
					new SingleMessage("rap_battle.a1"), new SingleMessage("rap_battle.a2"),
					new SingleMessage("rap_battle.a3"), new SingleMessage("rap_battle.a4")
				), new DelayMessage(new int[] {25},
					new SingleMessage("rap_battle.b1"),new SingleMessage("rap_battle.b2"),
					new SingleMessage("rap_battle.b3"),new SingleMessage("rap_battle.b4")
				), new DelayMessage(new int[] {17},
					new SingleMessage("rap_battle.c1"),new SingleMessage("rap_battle.c2"),
					new SingleMessage("rap_battle.c3", "consort_sound"), new SingleMessage("rap_battle.c4")
				), new DelayMessage(new int[] {25, 20, 30},
					new SingleMessage("rap_battle.d1"),new SingleMessage("rap_battle.d2"),
					new SingleMessage("rap_battle.d3"),new SingleMessage("rap_battle.d4")
				), new DelayMessage(new int[] {17, 20, 30},
					new SingleMessage("rap_battle.e1"),new SingleMessage("rap_battle.e2"),
					new SingleMessage("rap_battle.e3"),new SingleMessage("rap_battle.e4")
				), new DelayMessage(new int[] {25},
					new SingleMessage("rap_battle.f1"),new SingleMessage("rap_battle.f2"),
					new SingleMessage("rap_battle.f3"),new SingleMessage("rap_battle.f4")));
		addMessage(new ChoiceMessage(false, new SingleMessage("rap_battle"),
				new SingleMessage[]
				{
					new SingleMessage("rap_battle.accept"),
					new SingleMessage("rap_battle.deny")
				},
				new MessageType[] {
					//If you accepted the challenge
					new ChoiceMessage(false,
							new DescriptionMessage(raps, "rap_battle.raps_desc"),
							new SingleMessage[] {
									new SingleMessage("rap_battle_school"),
									new SingleMessage("rap_battle_concede")
							},
							new MessageType[] {
									new DoubleMessage(new DescriptiveMessage("rap_battle_school.rap", "player_title", "land_name"),
											new SingleMessage("rap_battle_school.final", "consort_sound")).setSayFirstOnce(),
									new SingleMessage("rap_battle_concede.final", "consort_sound")
							}
					),
					//If you didn't accept the challenge
					new SingleMessage("rap_battle.deny_answer")
				}
			).setAcceptNull()
		).consort(EnumConsort.NAKAGATOR, EnumConsort.IGUANA);
		
		addMessage("useless_pogo");
		addMessage("await_hero", "land_name", "consort_types", "player_title_land").reqLand();
		addMessage(new ConditionedMessage("skaia", (ConsortEntity consort, ServerPlayer player) -> !consort.visitedSkaia, new SingleMessage("watch_skaia"),
				new ConditionedMessage((ConsortEntity consort, ServerPlayer player) -> MSDimensions.isSkaia(consort.level.dimension()),
						new SingleMessage("at_skaia.1", "consort_sound_2"), new SingleMessage("visited_skaia")))).consort(EnumConsort.SALAMANDER, EnumConsort.IGUANA, EnumConsort.NAKAGATOR).reqLand();
		addMessage(new ConditionedMessage("skaia_turtle", (ConsortEntity consort, ServerPlayer player) -> !consort.visitedSkaia, new SingleMessage("watch_skaia"),
				new ConditionedMessage((ConsortEntity consort, ServerPlayer player) -> MSDimensions.isSkaia(consort.level.dimension()),
						new SingleMessage("at_skaia.2"), new SingleMessage("visited_skaia")))).consort(EnumConsort.TURTLE).reqLand();
		
		addMessage(new SingleMessage("zazzerpan")).consort(EnumConsort.TURTLE);
		addMessage(new SingleMessage("texas_history", "land_name")).consort(EnumConsort.TURTLE);
		addMessage(new SingleMessage("disks")).consort(EnumConsort.IGUANA);
		addMessage(new SingleMessage("whoops")).consort(EnumConsort.IGUANA);
		addMessage(new SingleMessage("fourth_wall")).consort(EnumConsort.IGUANA);
		addMessage(new SingleMessage("hats")).consort(EnumConsort.SALAMANDER);
		addMessage(new SingleMessage("wwizard")).consort(EnumConsort.TURTLE);
		addMessage(new SingleMessage("stock_market")).consort(EnumConsort.NAKAGATOR);
		addMessage(new SingleMessage("identity", "player_title_land", "player_name_land")).consort(EnumConsort.SALAMANDER).reqLand();
		addMessage(new ChainMessage(0, new SingleMessage("college.1"), new SingleMessage("college.2")));
		addMessage(new ChainMessage(1, new SingleMessage("unknown.1"), new SingleMessage("unknown.2"))).consort(EnumConsort.TURTLE);
		addMessage(new ChainMessage(1, new SingleMessage("cult.1", "player_title"), new SingleMessage("cult.2"))).consort(EnumConsort.TURTLE, EnumConsort.SALAMANDER);
		
		addMessage(new ChoiceMessage(new DescriptionMessage("peppy_offer"),
				new SingleMessage[]{new SingleMessage("peppy_offer.buy"), new SingleMessage("peppy_offer.deny")},
				new MessageType[]{
						new PurchaseMessage(false, MSLootTables.CONSORT_JUNK_REWARD, 1000, -15, "purchase",
								new ChainMessage(1, new SingleMessage("peppy_offer.item"), new SingleMessage("peppy_offer.purchase"))),
						new ChoiceMessage(new SingleMessage("peppy_offer.next"),
								new SingleMessage[]{new SingleMessage("peppy_offer.deny_again"), new SingleMessage("peppy_offer.buy_2")},
								new MessageType[]{new SingleMessage("dots"),
										new PurchaseMessage(false, MSLootTables.CONSORT_JUNK_REWARD, 500, -35, "purchase",
												new SingleMessage("peppy_offer.purchase"))})})).type(MerchantType.SHADY).consort(EnumConsort.NAKAGATOR, EnumConsort.IGUANA);
		
		
		addMessage(new ChoiceMessage(true, new SingleMessage("title_presence", "player_title"),
				new SingleMessage[]{new SingleMessage("title_presence.iam", "player_title"), new SingleMessage("title_presence.agree")},
				new MessageType[]{new SingleMessage("title_presence.iam_answer", "consort_sound_2"), new SingleMessage("thanks")})).consort(EnumConsort.IGUANA, EnumConsort.SALAMANDER).reqLand();
		
		addMessage(new ChoiceMessage(new DescriptionMessage("shady_offer"),
				new SingleMessage[]
						{
								new SingleMessage("shady_offer.buy"),
								new SingleMessage("shady_offer.deny")
						},
				new MessageType[]{
						new PurchaseMessage(false, MSLootTables.CONSORT_JUNK_REWARD, 1000, -15, "purchase",
								new ChainMessage(1,
										new SingleMessage("shady_offer.item"),
										new SingleMessage("shady_offer.purchase")
								)
						),
						new ChoiceMessage(new SingleMessage("shady_offer.next", "consort_sound"),
								new SingleMessage[]
										{
												new SingleMessage("shady_offer.deny_again"),
												new SingleMessage("shady_offer.buy_2")
										},
								new MessageType[]
										{
												new SingleMessage("dots"),
												new PurchaseMessage(false, MSLootTables.CONSORT_JUNK_REWARD, 500, -35, "purchase",
														new SingleMessage("shady_offer.purchase")
												)
										}
						)
				}
		)).type(MerchantType.SHADY).consort(EnumConsort.SALAMANDER, EnumConsort.TURTLE);
		
		addMessage(new ChoiceMessage(true, new SingleMessage("denizen", "denizen"),
				new SingleMessage[]{new SingleMessage("denizen.what"), new SingleMessage("denizen.ask_alignment")},
				new MessageType[]{new SingleMessage("denizen.explain", "player_class_land"), new SingleMessage("denizen.alignment")})).consort(EnumConsort.SALAMANDER, EnumConsort.IGUANA, EnumConsort.TURTLE).reqLand();
		
		addMessage(new ItemRequirement(MSTags.Items.CONSORT_SNACKS, false, true, new SingleMessage("hungry"),
						new ChoiceMessage(new SingleMessage("hungry.ask_food", "nbt_item:hungry.item"),
								new SingleMessage[] { new SingleMessage("hungry.accept"), new SingleMessage("hungry.deny") },
								new MessageType[] { new GiveItemMessage("hungry.thanks", "hungry.item", 0, 15, new SingleMessage("hungry.thanks")),
										new SingleMessage("sadface") }))).consort(EnumConsort.SALAMANDER, EnumConsort.IGUANA);
		addMessage(new ItemRequirement("hungry2", MSTags.Items.CONSORT_SNACKS, false, true, false,
						new SingleMessage(
								"hungry"),
						new ChoiceMessage(
								new SingleMessage("hungry.ask_food",
										"nbt_item:hungry2.item"),
								new SingleMessage[] {
										new SingleMessage(
												"hungry.accept"),
										new SingleMessage("hungry.deny") },
								new MessageType[] { new GiveItemMessage("hungry.thanks", "hungry2.item", 0, 15, new SingleMessage("hungry.thanks")),
										new ChoiceMessage(new SingleMessage("hungry.starving"),
												new SingleMessage[] { new SingleMessage("hungry.agree"),
														new SingleMessage("hungry.too_cheap") },
												new MessageType[] { new GiveItemMessage("hungry.sell_item", "hungry2.item", 10, 0,
														new ChainMessage(1, new DescriptionMessage("hungry.finally", "nbt_item:hungry2.item"),
																new SingleMessage("hungry.finally"))),
														new SingleMessage("hungry.end") }) }))).consort(EnumConsort.SALAMANDER, EnumConsort.NAKAGATOR);
		
		addMessage(new MerchantGuiMessage(new SingleMessage("food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).consort(EnumConsort.SALAMANDER).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("fast_food"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).consort(EnumConsort.NAKAGATOR).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("grocery_store"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).consort(EnumConsort.IGUANA).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("tasty_welcome"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).consort(EnumConsort.TURTLE).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("breeze_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(WIND)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("blood_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(PULSE)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("rabbit_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(RABBITS)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("lightning_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(THUNDER)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("frog_leg_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(FROGS)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("frog_food_shop", "land_name"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(FROGS)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("time_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(CLOCKWORK)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("thyme_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(CLOCKWORK, THOUGHT)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("library_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(THOUGHT)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("cake_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(CAKE)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("light_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(LIGHT)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("silence_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(SILENCE)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("rage_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(MONSTERS)).lockToConsort();
		addMessage(new MerchantGuiMessage(new DescriptionMessage("hope_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(TOWERS)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("buckets_food_shop"), CONSORT_FOOD_STOCK)).type(MerchantType.FOOD).condition(titleLandByGroup(BUCKETS)).lockToConsort();
		
		addMessage(new MerchantGuiMessage(new SingleMessage("general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("got_the_goods"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("rising_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("breeze_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(WIND)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("blood_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(PULSE)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("life_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(RABBITS)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("doom_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(THUNDER)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("frog_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(FROGS)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("time_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(CLOCKWORK)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("book_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(THOUGHT)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("cake_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(CAKE)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("light_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(LIGHT)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("silence_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(SILENCE)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("rage_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(MONSTERS)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("tower_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(TOWERS)).lockToConsort();
		addMessage(new MerchantGuiMessage(new SingleMessage("buckets_general_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(titleLandByGroup(BUCKETS)).lockToConsort();
		
		addMessage(new MerchantGuiMessage(new SingleMessage("boring_shop"), CONSORT_GENERAL_STOCK)).type(MerchantType.GENERAL).condition(terrainLandByGroup(RAINBOW));
	}
	
	/**
	 * Not thread-safe. Make sure to only call this on the main thread
	 */
	public static DialogueWrapper addMessage(String message, String... args)
	{
		return addMessage(new SingleMessage(message, args));
	}
	
	/**
	 * Not thread-safe. Make sure to only call this on the main thread
	 */
	public static DialogueWrapper addMessage(MessageType message)
	{
		return addMessage(10, message);
	}
	
	/**
	 * Not thread-safe. Make sure to only call this on the main thread
	 */
	public static DialogueWrapper addMessage(int weight, MessageType message)
	{
		DialogueWrapper msg = new DialogueWrapper(weight);
		msg.messageStart = message;
		messages.add(msg);
		return msg;
	}
	
	public static DialogueWrapper getRandomMessage(ConsortEntity consort, boolean hasHadMessage)
	{
		LandTypePair aspects = LandTypePair.getTypes(consort.getServer(), consort.homeDimension).orElse(null);
		
		List<DialogueWrapper> list = new ArrayList<>();
		
		for(DialogueWrapper message : messages)
		{
			if(message.lockToConsort && hasHadMessage)
				continue;
			if(message.reqLand && aspects == null)
				continue;
			if(message.consortRequirement != null && !message.consortRequirement.contains(consort.getConsortType()))
				continue;
			if(message.terrainCondition != null && !(aspects != null && message.terrainCondition.test(aspects.getTerrain())))
				continue;
			if(message.titleCondition != null && !(aspects != null && message.titleCondition.test(aspects.getTitle())))
				continue;
			if(message.merchantRequirement == null && consort.merchantType != EnumConsort.MerchantType.NONE
					|| message.merchantRequirement != null && !message.merchantRequirement.contains(consort.merchantType))
				continue;
			if(message.additionalRequirement != null && !message.additionalRequirement.apply(consort))
				continue;
			list.add(message);
		}
		
		return WeightedRandom.getRandomItem(consort.level.random, list).orElseThrow();
	}
	
	public static DialogueWrapper getMessageFromString(String name)
	{
		for(DialogueWrapper message : messages)
			if(message.getString().equals(name))
				return message;
		return null;
	}
	
	public static class DialogueWrapper extends WeightedEntry.IntrusiveBase
	{
		
		private DialogueWrapper(int weight)
		{
			super(weight);
		}
		
		private boolean reqLand;
		private boolean lockToConsort;
		
		private MessageType messageStart;
		
		@Nullable
		private LandTypeConditions.Terrain terrainCondition;
		@Nullable
		private LandTypeConditions.Title titleCondition;
		private EnumSet<EnumConsort> consortRequirement;
		private EnumSet<MerchantType> merchantRequirement;
		private ConsortRequirement additionalRequirement;
		
		public DialogueWrapper reqLand()
		{
			reqLand = true;
			return this;
		}
		
		public DialogueWrapper lockToConsort()
		{
			lockToConsort = true;
			return this;
		}
		
		public boolean isLockedToConsort()
		{
			return lockToConsort;
		}
		
		public DialogueWrapper condition(LandTypeConditions.Terrain condition)
		{
			this.terrainCondition = condition;
			return this.reqLand();
		}
		
		public DialogueWrapper condition(LandTypeConditions.Title condition)
		{
			this.titleCondition = condition;
			return this.reqLand();
		}
		
		public DialogueWrapper consort(EnumConsort... types)
		{
			consortRequirement = EnumSet.of(types[0], types);
			return this;
		}
		
		public DialogueWrapper type(MerchantType... types)
		{
			merchantRequirement = EnumSet.of(types[0], types);
			return this;
		}
		
		public DialogueWrapper consortReq(ConsortRequirement req)
		{
			additionalRequirement = req;
			return this;
		}
		
		public Component getMessage(ConsortEntity consort, ServerPlayer player)
		{
			return messageStart.getMessage(consort, player, "");
		}
		
		public Component getFromChain(ConsortEntity consort, ServerPlayer player, String fromChain)
		{
			return messageStart.getFromChain(consort, player, "", fromChain);
		}
		
		public String getString()
		{
			return messageStart.getString();
		}
		
	}
	
	public interface ConsortRequirement
	{
		boolean apply(ConsortEntity consort);
	}
	
	public static void serverStarting()
	{
		//debugPrintAll();
	}
	
	@SuppressWarnings("unused")
	private static void debugPrintAll()
	{
		List<Component> list = new ArrayList<>();
		for(DialogueWrapper wrapper : messages)
		{
			wrapper.messageStart.debugAddAllMessages(list);
		}
		
		for(Component textComponent : list)
			LOGGER.info(textComponent.getString());
	}
}