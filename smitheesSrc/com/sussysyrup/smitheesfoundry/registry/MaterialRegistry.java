package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.trait.ApiTraitRegistry;
import com.sussysyrup.smitheesfoundry.api.trait.TraitContainer;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import com.sussysyrup.smitheesfoundry.api.material.MaterialResource;
import com.sussysyrup.smitheesfoundry.trait.active.MagneticTrait;
import com.sussysyrup.smitheesfoundry.trait.active.RegrowthTrait;
import com.sussysyrup.smitheesfoundry.trait.repair.CorrodingTrait;
import com.sussysyrup.smitheesfoundry.trait.repair.GrowthTrait;
import com.sussysyrup.smitheesfoundry.trait.stat.*;
import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import java.util.Arrays;
import java.util.List;

public class MaterialRegistry {

    private static final MaterialResource WOOD = new MaterialResource("wood", 1);
    private static final TagKey<Item> WOOD_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/wood_resource_material"));

    private static final MaterialResource STONE = new MaterialResource("stone", 1);
    private static final TagKey<Item> STONE_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/stone_resource_material"));

    private static final MaterialResource FLINT = new MaterialResource("flint", 1);
    private static final TagKey<Item> FLINT_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/flint_resource_material"));

    private static final MaterialResource IRONNUG = new MaterialResource("iron", 1F/9F);
    private static final TagKey<Item> IRONNUG_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/ironnug_resource_material"));
    private static final MaterialResource IRONING = new MaterialResource("iron", 1);
    private static final TagKey<Item> IRONING_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/ironing_resource_material"));
    private static final MaterialResource IRONBLOCK = new MaterialResource("iron", 9);
    private static final TagKey<Item> IRONBLOCK_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/ironblock_resource_material"));

    private static final MaterialResource COPPERING = new MaterialResource("copper", 1);
    private static final TagKey<Item> COPPERING_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/coppering_resource_material"));
    private static final MaterialResource COPPERBLOCK = new MaterialResource("copper", 9);
    private static final TagKey<Item> COPPERBLOCK_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/copperblock_resource_material"));
    private static final MaterialResource COPPERNUG = new MaterialResource("copper", 1/9F);
    private static final TagKey<Item> COPPERNUG_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/coppernug_resource_material"));

    private static final MaterialResource DIAMOND = new MaterialResource("diamond", 1);
    private static final TagKey<Item> DIAMOND_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/diamond_resource_material"));

    private static final MaterialResource NETHERITEING = new MaterialResource("netherite", 1);
    private static final TagKey<Item> NETHERITEING_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/netheriteing_resource_material"));
    private static final MaterialResource NETHERITEBLOCK = new MaterialResource("netherite", 9);
    private static final TagKey<Item> NETHERITEBLOCK_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/netheriteblock_resource_material"));
    private static final MaterialResource NETHERITENUG = new MaterialResource("netherite", 1/9F);
    private static final TagKey<Item> NETHERITENUG_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/netheritenug_resource_material"));

    private static final MaterialResource ROSEGOLDNUG = new MaterialResource("rosegold", 1F/9F);
    private static final TagKey<Item> ROSEGOLDNUG_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/rosegoldnug_resource_material"));
    private static final MaterialResource ROSEGOLDING = new MaterialResource("rosegold", 1);
    private static final TagKey<Item> ROSEGOLDING_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/rosegolding_resource_material"));
    private static final MaterialResource ROSEGOLDBLOCK = new MaterialResource("rosegold", 9);
    private static final TagKey<Item> ROSEGOLDBLOCK_TAG = TagKey.of(Registry.ITEM_KEY, new Identifier(Main.MODID, "resource/rosegoldblock_resource_material"));

    public static TraitContainer REGROWTH_TRAIT = new RegrowthTrait("regrowth",Formatting.DARK_GREEN);
    public static TraitContainer MAGNETIC1_TRAIT = new MagneticTrait("magnetic1", Formatting.GRAY, 4);
    public static TraitContainer MAGNETIC2_TRAIT = new MagneticTrait("magnetic2", Formatting.GRAY, 4.5F);

    public static TraitContainer BRITTLE_TRAIT = new BrittleTrait("brittle", Formatting.DARK_GRAY);
    public static TraitContainer CRUDE_TRAIT = new CrudeTrait("crude", Formatting.BLACK);
    public static TraitContainer PRIMAL_TRAIT = new PrimalTrait("primal", Formatting.DARK_GRAY);
    public static TraitContainer MUNDANE_TRAIT = new MundaneTrait("mundane", Formatting.DARK_GRAY);
    public static TraitContainer ANCIENT_TRAIT = new AncientTrait("ancient", Formatting.GOLD);

    public static TraitContainer GROWTH_TRAIT = new GrowthTrait("growth", Formatting.DARK_GREEN);
    public static TraitContainer CORRODING_TRAIT = new CorrodingTrait("corroding",Formatting.DARK_RED);

    public static void main()
    {
        registerTraits();

        ApiMaterialRegistry.getInstance().registerMaterial("wood", new Material("wood", false, "empty", MiningLevels.WOOD, 59, 1F, 2, 0.0F,
                createTraitsList(GROWTH_TRAIT), createTraitsList(REGROWTH_TRAIT), createTraitsList(REGROWTH_TRAIT), createTraitsList(GROWTH_TRAIT)));

        ApiMaterialRegistry.getInstance().registerMaterial("stone", new Material("stone", false, "empty", MiningLevels.STONE, 131, 0.2F, 4, 1.0F,
                createTraitsList(BRITTLE_TRAIT, CRUDE_TRAIT), createTraitsList(BRITTLE_TRAIT), createTraitsList(BRITTLE_TRAIT), createTraitsList(CRUDE_TRAIT)));

        ApiMaterialRegistry.getInstance().registerMaterial("flint", new Material("flint", false, "empty", MiningLevels.STONE, 112, 1.2F, 3.5F, 0.8F,
                createTraitsList(CRUDE_TRAIT, PRIMAL_TRAIT), createTraitsList(BRITTLE_TRAIT), createTraitsList(BRITTLE_TRAIT), createTraitsList(CRUDE_TRAIT)));

        ApiMaterialRegistry.getInstance().registerMaterial("iron", new Material("iron", true, "molten_iron", MiningLevels.IRON, 250, 1.05F, 6F, 2.0F,
                createTraitsList(MAGNETIC1_TRAIT), createTraitsList(), createTraitsList(), createTraitsList(MAGNETIC1_TRAIT)));

        ApiMaterialRegistry.getInstance().registerMaterial("copper", new Material("copper", true, "molten_copper", MiningLevels.IRON, 125, 0.8F, 4.2F, 1.5F,
                createTraitsList(MAGNETIC2_TRAIT, CORRODING_TRAIT), createTraitsList(CORRODING_TRAIT), createTraitsList(CORRODING_TRAIT), createTraitsList(MAGNETIC2_TRAIT)));

        //Use of diamond should be allowed but always discouraged
        ApiMaterialRegistry.getInstance().registerMaterial("diamond", new Material("diamond", false, "empty", MiningLevels.DIAMOND, 1561, 1.3F, 8F, 3.0F,
                createTraitsList(MUNDANE_TRAIT), createTraitsList(BRITTLE_TRAIT, MUNDANE_TRAIT), createTraitsList(MUNDANE_TRAIT), createTraitsList(MUNDANE_TRAIT)));

        ApiMaterialRegistry.getInstance().registerMaterial("netherite", new Material("netherite", true, "molten_netherite", MiningLevels.NETHERITE, 2031, 0.9F, 9F, 4.0F,
                createTraitsList(), createTraitsList(ANCIENT_TRAIT), createTraitsList(), createTraitsList(ANCIENT_TRAIT)));

        ApiMaterialRegistry.getInstance().registerMaterial("rosegold", new Material("rosegold", true, "molten_rosegold", MiningLevels.STONE, 56, 1F, 12F, 1.0F,
                createTraitsList(MAGNETIC1_TRAIT), createTraitsList(CORRODING_TRAIT), createTraitsList(CORRODING_TRAIT), createTraitsList(MAGNETIC1_TRAIT)));

        ApiMaterialRegistry.getInstance().registerPreMaterialResource(WOOD_TAG, WOOD);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(STONE_TAG, STONE);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(FLINT_TAG, FLINT);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(IRONNUG_TAG, IRONNUG);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(IRONING_TAG, IRONING);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(IRONBLOCK_TAG, IRONBLOCK);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(COPPERING_TAG, COPPERING);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(COPPERBLOCK_TAG, COPPERBLOCK);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(DIAMOND_TAG, DIAMOND);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(NETHERITEING_TAG, NETHERITEING);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(NETHERITEBLOCK_TAG, NETHERITEBLOCK);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(ROSEGOLDNUG_TAG, ROSEGOLDNUG);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(ROSEGOLDING_TAG, ROSEGOLDING);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(ROSEGOLDBLOCK_TAG, ROSEGOLDBLOCK);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(COPPERNUG_TAG, COPPERNUG);
        ApiMaterialRegistry.getInstance().registerPreMaterialResource(NETHERITENUG_TAG, NETHERITENUG);
    }

    private static void registerTraits()
    {
        ApiTraitRegistry.getInstance().registerTrait("regrowth", REGROWTH_TRAIT);
        ApiTraitRegistry.getInstance().registerTrait("magnetic1", MAGNETIC1_TRAIT);
        ApiTraitRegistry.getInstance().registerTrait("magnetic2", MAGNETIC2_TRAIT);

        ApiTraitRegistry.getInstance().registerTrait("brittle", BRITTLE_TRAIT);
        ApiTraitRegistry.getInstance().registerTrait("crude", CRUDE_TRAIT);
        ApiTraitRegistry.getInstance().registerTrait("primal", PRIMAL_TRAIT);
        ApiTraitRegistry.getInstance().registerTrait("mundane", MUNDANE_TRAIT);
        ApiTraitRegistry.getInstance().registerTrait("ancient", ANCIENT_TRAIT);

        ApiTraitRegistry.getInstance().registerTrait("growth", GROWTH_TRAIT);
        ApiTraitRegistry.getInstance().registerTrait("corroding", CORRODING_TRAIT);
    }

    private static List<TraitContainer> createTraitsList(TraitContainer... containers)
    {
        return Arrays.stream(containers).toList();
    }
}
