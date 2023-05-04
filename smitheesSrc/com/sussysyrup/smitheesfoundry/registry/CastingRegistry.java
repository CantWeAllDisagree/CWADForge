package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.casting.ApiBlockCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.casting.ApiCastingRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.HashMap;

public class CastingRegistry {

    public static void preInit()
    {
        HashMap<Identifier, Item> ingotFluidMap = ApiCastingRegistry.getInstance().getPreIngotFluidMap();
        HashMap<Identifier, Item> nuggetFluidMap = ApiCastingRegistry.getInstance().getPreNuggetFluidMap();
        HashMap<Identifier, Item> blockFluidMap = ApiBlockCastingRegistry.getInstance().preBlockFluidMap();

        ingotFluidMap.put(new Identifier(Main.MODID, "molten_iron"), Items.IRON_INGOT);
        ingotFluidMap.put(new Identifier(Main.MODID, "molten_copper"), Items.COPPER_INGOT);
        ingotFluidMap.put(new Identifier(Main.MODID, "molten_netherite"), Items.NETHERITE_INGOT);
        ingotFluidMap.put(new Identifier(Main.MODID, "molten_gold"), Items.GOLD_INGOT);
        ingotFluidMap.put(new Identifier(Main.MODID, "molten_rosegold"), ItemsRegistry.ROSEGOLD_INGOT);

        ApiCastingRegistry.getInstance().setPreIngotFluidMap(ingotFluidMap);

        nuggetFluidMap.put(new Identifier(Main.MODID, "molten_iron"), Items.IRON_NUGGET);
        nuggetFluidMap.put(new Identifier(Main.MODID, "molten_gold"), Items.GOLD_NUGGET);
        nuggetFluidMap.put(new Identifier(Main.MODID, "molten_rosegold"), ItemsRegistry.ROSEGOLD_NUGGET);
        nuggetFluidMap.put(new Identifier(Main.MODID, "molten_copper"), ItemsRegistry.COPPER_NUGGET);
        nuggetFluidMap.put(new Identifier(Main.MODID, "molten_netherite"), ItemsRegistry.NETHERITE_NUGGET);

        ApiCastingRegistry.getInstance().setPreNuggetFluidMap(nuggetFluidMap);

        blockFluidMap.put(new Identifier(Main.MODID, "molten_iron"), Items.IRON_BLOCK);
        blockFluidMap.put(new Identifier(Main.MODID, "molten_gold"), Items.GOLD_BLOCK);
        blockFluidMap.put(new Identifier(Main.MODID, "molten_rosegold"), Registry.ITEM.get(new Identifier(Main.MODID, "rosegold_block")));
        blockFluidMap.put(new Identifier(Main.MODID, "molten_copper"), Items.COPPER_BLOCK);
        blockFluidMap.put(new Identifier(Main.MODID, "molten_netherite"), Items.NETHERITE_BLOCK);
        blockFluidMap.put(new Identifier(Main.MODID, "molten_gold"), Items.GOLD_BLOCK);

        ApiBlockCastingRegistry.getInstance().setPreBlockFluidMap(blockFluidMap);
    }

}
