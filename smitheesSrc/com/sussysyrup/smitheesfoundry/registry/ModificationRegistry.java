package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.modification.ApiModificationRegistry;
import com.sussysyrup.smitheesfoundry.api.modification.ModificationRecipe;
import com.sussysyrup.smitheesfoundry.modification.DurabilityBonusMod;
import com.sussysyrup.smitheesfoundry.modification.DurabilityMultMod;
import com.sussysyrup.smitheesfoundry.modification.MiningSpeedBonusMod;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ModificationRegistry {

    public static void main()
    {
        //durability bonus
        ApiModificationRegistry.getInstance().registerModification("durabilitybonus", 1, new DurabilityBonusMod("durabilitybonus", 1));
        ApiModificationRegistry.getInstance().registerModification("durabilitybonus", 2, new DurabilityBonusMod("durabilitybonus", 2));

        ApiModificationRegistry.getInstance().registerModificationRecipe("durabilitybonus", 1, new ModificationRecipe(new Identifier(Main.MODID, "crude_resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("diamond"), 16);
                }}));
        ApiModificationRegistry.getInstance().registerModificationRecipe("durabilitybonus", 2, new ModificationRecipe(new Identifier(Main.MODID, "crude_resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("diamond_block"), 16);
                }}));

        //mining speed bonus
        ApiModificationRegistry.getInstance().registerModification("miningspeedbonus", 1, new MiningSpeedBonusMod("miningspeedbonus", 1));
        ApiModificationRegistry.getInstance().registerModification("miningspeedbonus", 2, new MiningSpeedBonusMod("miningspeedbonus", 2));
        ApiModificationRegistry.getInstance().registerModification("miningspeedbonus", 3, new MiningSpeedBonusMod("miningspeedbonus", 3));
        ApiModificationRegistry.getInstance().registerModification("miningspeedbonus", 4, new MiningSpeedBonusMod("miningspeedbonus", 4));
        ApiModificationRegistry.getInstance().registerModification("miningspeedbonus", 5, new MiningSpeedBonusMod("miningspeedbonus", 5));

        ApiModificationRegistry.getInstance().registerModificationRecipe("miningspeedbonus", 1, new ModificationRecipe(new Identifier(Main.MODID, "crude_resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("redstone"), 128);
                }}));
        ApiModificationRegistry.getInstance().registerModificationRecipe("miningspeedbonus", 2, new ModificationRecipe(new Identifier(Main.MODID, "crude_resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("redstone"), 384);
                }}));
        ApiModificationRegistry.getInstance().registerModificationRecipe("miningspeedbonus", 3, new ModificationRecipe(new Identifier(Main.MODID, "resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("redstone"), 768);
                }}));
        ApiModificationRegistry.getInstance().registerModificationRecipe("miningspeedbonus", 4, new ModificationRecipe(new Identifier(Main.MODID, "resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("redstone"), 896);
                }}));
        ApiModificationRegistry.getInstance().registerModificationRecipe("miningspeedbonus", 5, new ModificationRecipe(new Identifier(Main.MODID, "resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("redstone"), 1152);
                }}));

        //mining speed bonus
        ApiModificationRegistry.getInstance().registerModification("durabilitymult", 1, new DurabilityMultMod("durabilitymult", 1));
        ApiModificationRegistry.getInstance().registerModification("durabilitymult", 2, new DurabilityMultMod("durabilitymult", 2));
        ApiModificationRegistry.getInstance().registerModification("durabilitymult", 3, new DurabilityMultMod("durabilitymult", 3));
        ApiModificationRegistry.getInstance().registerModification("durabilitymult", 4, new DurabilityMultMod("durabilitymult", 4));
        ApiModificationRegistry.getInstance().registerModification("durabilitymult", 5, new DurabilityMultMod("durabilitymult", 5));

        ApiModificationRegistry.getInstance().registerModificationRecipe("durabilitymult", 1, new ModificationRecipe(new Identifier(Main.MODID, "resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("netherite_scrap"), 16);
                    put(new Identifier("diamond"), 4);
                }}));
        ApiModificationRegistry.getInstance().registerModificationRecipe("durabilitymult", 2, new ModificationRecipe(new Identifier(Main.MODID, "resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("netherite_scrap"), 64);
                    put(new Identifier("diamond"), 8);
                }}));
        ApiModificationRegistry.getInstance().registerModificationRecipe("durabilitymult", 3, new ModificationRecipe(new Identifier(Main.MODID, "resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("netherite_scrap"), 32);
                    put(new Identifier("netherite_ingot"), 4);
                }}));
        ApiModificationRegistry.getInstance().registerModificationRecipe("durabilitymult", 4, new ModificationRecipe(new Identifier(Main.MODID, "resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("netherite_ingot"), 16);
                    put(new Identifier("diamond"), 16);
                }}));
        ApiModificationRegistry.getInstance().registerModificationRecipe("durabilitymult", 5, new ModificationRecipe(new Identifier(Main.MODID, "resonator"),
                new HashMap<Identifier,Integer>() {{
                    put(new Identifier("netherite_ingot"), 64);
                    put(new Identifier("diamond"), 24);
                }}));
    }

}
