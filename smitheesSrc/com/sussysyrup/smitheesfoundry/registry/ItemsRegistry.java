package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.item.CastItem;
import com.sussysyrup.smitheesfoundry.api.casting.ApiCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ApiToolRegistry;
import com.sussysyrup.smitheesfoundry.api.itemgroup.ItemGroups;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiToolRecipeRegistry;
import com.sussysyrup.smitheesfoundry.items.*;
import com.sussysyrup.smitheesfoundry.recipe.ThreePartToolRecipe;
import com.sussysyrup.smitheesfoundry.recipe.TwoPartToolRecipe;
import com.sussysyrup.smitheesfoundry.util.CompatibilityUtil;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ItemsRegistry {

    public static final TagKey<Block> SWORD_MINEABLE = TagKey.of(Registry.BLOCK_KEY, new Identifier("c", "mineable/sword"));

    public static Item FORGE_PICKAXE = new PickaxeToolItem(new FabricItemSettings().group(ItemGroups.TOOL_GROUP).maxCount(1), "pickaxe", BlockTags.PICKAXE_MINEABLE);
    public static Item FORGE_AXE = new AxeToolItem(new FabricItemSettings().group(ItemGroups.TOOL_GROUP).maxCount(1), "axe", BlockTags.AXE_MINEABLE);
    public static Item FORGE_HOE = new HoeToolItem(new FabricItemSettings().group(ItemGroups.TOOL_GROUP).maxCount(1), "hoe", BlockTags.HOE_MINEABLE);
    public static Item FORGE_SHOVEL = new ShovelToolItem(new FabricItemSettings().group(ItemGroups.TOOL_GROUP).maxCount(1), "shovel", BlockTags.SHOVEL_MINEABLE);
    public static Item FORGE_SWORD = new SwordToolItem(new FabricItemSettings().group(ItemGroups.TOOL_GROUP).maxCount(1), "sword", SWORD_MINEABLE);
    public static Item FORGE_CHISEL;

    public static Item CRUDE_CHISEL = new Item(new FabricItemSettings().group(ItemGroups.ITEM_GROUP).maxCount(1).maxDamage(3));
    public static Item GUIDE_BOOK_ITEM = new GuideBookItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP).maxCount(1));

    public static Item COMPOUND_CLAY = new Item(new FabricItemSettings().group(ItemGroups.ITEM_GROUP));
    public static Item REINFORCED_BRICK = new Item(new FabricItemSettings().group(ItemGroups.ITEM_GROUP));
    public static Item ROSEGOLD_INGOT = new Item((new FabricItemSettings().group(ItemGroups.ITEM_GROUP)));
    public static Item ROSEGOLD_NUGGET = new Item((new FabricItemSettings().group(ItemGroups.ITEM_GROUP)));
    public static Item NETHERITE_NUGGET = new Item((new FabricItemSettings().group(ItemGroups.ITEM_GROUP)));
    public static Item COPPER_NUGGET = new Item((new FabricItemSettings().group(ItemGroups.ITEM_GROUP)));


    public static CastItem BLANK_CAST = new CastItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP), "blank");
    public static CastItem INGOT_CAST = new CastItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP), "ingot");
    public static CastItem NUGGET_CAST = new CastItem(new FabricItemSettings().group(ItemGroups.ITEM_GROUP), "nugget");

    public static void main()
    {
        if(!CompatibilityUtil.disableDefaultChisel)
        {
           FORGE_CHISEL = new ChiselToolItem(new FabricItemSettings().group(ItemGroups.TOOL_GROUP).maxCount(1), "chisel", null);
        }

        register("smithee_guide", GUIDE_BOOK_ITEM);

        register("crude_chisel", CRUDE_CHISEL);

        register("compound_clay", COMPOUND_CLAY);
        register("reinforced_brick", REINFORCED_BRICK);
        register("rosegold_ingot", ROSEGOLD_INGOT);
        register("rosegold_nugget", ROSEGOLD_NUGGET);
        register("netherite_nugget", NETHERITE_NUGGET);
        register("copper_nugget", COPPER_NUGGET);

        ApiToolRegistry.getInstance().registerTool("pickaxe", FORGE_PICKAXE);
        ApiToolRecipeRegistry.getInstance().register("pickaxe", ApiToolRecipeRegistry.getInstance().createKey("toolhandle", "toolbinding", "pickaxehead", "empty", "empty"), new ThreePartToolRecipe(FORGE_PICKAXE));

        ApiToolRegistry.getInstance().registerTool("axe", FORGE_AXE);
        ApiToolRecipeRegistry.getInstance().register("axe", ApiToolRecipeRegistry.getInstance().createKey("toolhandle", "toolbinding", "axehead", "empty", "empty"), new ThreePartToolRecipe(FORGE_AXE));

        ApiToolRegistry.getInstance().registerTool("hoe", FORGE_HOE);
        ApiToolRecipeRegistry.getInstance().register("hoe", ApiToolRecipeRegistry.getInstance().createKey("toolhandle", "toolbinding", "hoehead", "empty", "empty"), new ThreePartToolRecipe(FORGE_HOE));

        ApiToolRegistry.getInstance().registerTool("shovel", FORGE_SHOVEL);
        ApiToolRecipeRegistry.getInstance().register("shovel", ApiToolRecipeRegistry.getInstance().createKey("toolhandle", "toolbinding", "shovelhead", "empty", "empty"), new ThreePartToolRecipe(FORGE_SHOVEL));

        ApiToolRegistry.getInstance().registerTool("sword", FORGE_SWORD);
        ApiToolRecipeRegistry.getInstance().register("sword", ApiToolRecipeRegistry.getInstance().createKey("toolhandle", "swordguard", "swordblade", "empty", "empty"), new ThreePartToolRecipe(FORGE_SWORD));

        ApiToolRegistry.getInstance().registerTool("chisel", FORGE_CHISEL);
        ApiToolRecipeRegistry.getInstance().register("chisel", ApiToolRecipeRegistry.getInstance().createKey("toolhandle", "empty", "chiselblade", "empty", "empty"), new TwoPartToolRecipe(FORGE_CHISEL));

        ApiToolRegistry.getInstance().addSweepWeapon("sword");

        register("blank_cast", BLANK_CAST);
        ApiCastingRegistry.getInstance().addCastItem("blank", BLANK_CAST);

        register("ingot_cast", INGOT_CAST);
        ApiCastingRegistry.getInstance().addCastItem("ingot", INGOT_CAST);

        register("nugget_cast", NUGGET_CAST);
        ApiCastingRegistry.getInstance().addCastItem("nugget", NUGGET_CAST);
    }

    private static void register(String name, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, name), item);
    }

}
