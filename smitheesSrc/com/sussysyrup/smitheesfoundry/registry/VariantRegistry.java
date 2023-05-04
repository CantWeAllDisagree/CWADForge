package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.block.ApiVariationRegistry;
import com.sussysyrup.smitheesfoundry.api.block.VariationMetalRecord;
import net.minecraft.util.Identifier;

public class VariantRegistry {

    public static void main()
    {
        ApiVariationRegistry.getInstance().registerVariantWoodQuick(new Identifier(Main.MODID, "oak"), new Identifier("oak"));
        ApiVariationRegistry.getInstance().registerVariantWoodQuick(new Identifier(Main.MODID, "spruce"), new Identifier("spruce"));
        ApiVariationRegistry.getInstance().registerVariantWoodQuick(new Identifier(Main.MODID, "birch"), new Identifier("birch"));
        ApiVariationRegistry.getInstance().registerVariantWoodQuick(new Identifier(Main.MODID, "jungle"), new Identifier("jungle"));
        ApiVariationRegistry.getInstance().registerVariantWoodQuick(new Identifier(Main.MODID, "birch"), new Identifier("birch"));
        ApiVariationRegistry.getInstance().registerVariantWoodQuick(new Identifier(Main.MODID, "dark_oak"), new Identifier("dark_oak"));

        ApiVariationRegistry.getInstance().registerVariantMetalQuick(new Identifier(Main.MODID, "iron"), new Identifier("iron"));
        ApiVariationRegistry.getInstance().registerVariantMetalQuick(new Identifier(Main.MODID, "gold"), new Identifier("gold"));

        ApiVariationRegistry.getInstance().registerVariantMetal(new Identifier(Main.MODID, "netherite"), new VariationMetalRecord(new Identifier(
                "netherite_block"),
                "netherite",
                new Identifier("netherite_scrap"),
                new Identifier("netherite_ingot"),
                new Identifier("netherite_block")));
        ApiVariationRegistry.getInstance().registerVariantMetal(new Identifier(Main.MODID, "copper"), new VariationMetalRecord(new Identifier(
                "copper_block"),
                "copper",
                new Identifier(Main.MODID, "copper_nugget"),
                new Identifier("copper_ingot"),
                new Identifier("copper_block")));

        ApiVariationRegistry.getInstance().registerVariantMetalQuick(new Identifier(Main.MODID, "rosegold"), new Identifier(Main.MODID, "rosegold"));
    }
}
