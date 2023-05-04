package com.sussysyrup.smitheesfoundry.util;

import com.sussysyrup.smitheesfoundry.api.item.ApiPartRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import com.sussysyrup.smitheesfoundry.api.modification.*;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiToolRecipe;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiToolRecipeRegistry;
import com.sussysyrup.smitheesfoundry.api.trait.*;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtTypes;

import java.util.*;

public class ToolUtil {

    public static List<TraitContainer> getStackTraits(ItemStack stack)
    {
        List<TraitContainer> listOut = new ArrayList<>();

        ToolItem toolItem = ((ToolItem) stack.getItem());

        NbtCompound nbt = stack.getNbt();

        if(nbt == null)
        {
            return listOut;
        }

        ApiToolRecipe recipe = ApiToolRecipeRegistry.getInstance().getRecipeByType(toolItem.getToolType());

        List<String> parts = Arrays.stream(recipe.getKey().split(",")).toList();

        List<String> partCategories = new ArrayList<>();

        for(String part : parts)
        {
            if(part.equals("empty"))
            {
                continue;
            }

            partCategories.add(ApiPartRegistry.getInstance().getPrePartCategory(part));
        }

        List<String> materialKeys = new ArrayList<>();
        materialKeys.add(nbt.getString(ToolItem.HANDLE_KEY));
        materialKeys.add(nbt.getString(ToolItem.BINDING_KEY));
        materialKeys.add(nbt.getString(ToolItem.HEAD_KEY));
        materialKeys.add(nbt.getString(ToolItem.EXTRA1_KEY));
        materialKeys.add(nbt.getString(ToolItem.EXTRA2_KEY));

        String materialKey;
        Material material;

        int counter = 0;
        List<TraitContainer> processing;

        for(int i = 0; i < materialKeys.size(); i++)
        {
            materialKey = materialKeys.get(i);
            if(materialKey.equals("empty"))
            {
                continue;
            }

            material = ApiMaterialRegistry.getInstance().getMaterial(materialKey);

            if(material == null)
            {
                continue;
            }

            processing = material.getTraits(partCategories.get(counter));

            if(processing == null)
            {
                continue;
            }

            for (TraitContainer trait : processing)
            {
                if(!listOut.contains(trait))
                listOut.add(trait);
            }

            counter++;
        }

        return listOut;
    }

    public static List<TraitContainer> getActiveToolTraits(ItemStack stack)
    {

        List<TraitContainer> traits = new ArrayList<>();

        for(TraitContainer trait : getStackTraits(stack))
        {
            if(trait instanceof IActiveTrait)
            {
                traits.add(trait);
            }
        }

        return traits;
    }

    public static List<TraitContainer> getStatsTraits(ItemStack stack)
    {
        List<TraitContainer> traits = new ArrayList<>();

        for(TraitContainer trait : getStackTraits(stack))
        {
            if(trait instanceof IStatTrait)
            {
                traits.add(trait);
            }
        }

        return traits;
    }

    public static List<TraitContainer> getRepairTraits(ItemStack stack)
    {
        List<TraitContainer> traits = new ArrayList<>();

        for(TraitContainer trait : getStackTraits(stack))
        {
            if(trait instanceof IRepairTrait)
            {
                traits.add(trait);
            }
        }

        return traits;
    }

    public static ItemStack getDurabilityCorrectStack(ItemStack stack)
    {
        ToolItem tool = ((ToolItem) stack.getItem());

        int dur = tool.getMaxDurability(stack);

        stack.getNbt().putInt(ToolItem.DURABILITY_KEY, dur);

        return stack;
    }

    public static int getDurabilityCorrection(ItemStack stack)
    {
        ToolItem tool = ((ToolItem) stack.getItem());

        int dur = tool.getMaxDurability(stack);

        return dur;
    }

    public static List<String> getModifications(ItemStack stack)
    {
        NbtCompound mainNbt = stack.getNbt();

        NbtList list = mainNbt.getList(ToolItem.MODIFICATIONS_KEY, 8);

        if(list != null) {
            return Util.decodeStringList(list);
        }
        return Collections.EMPTY_LIST;
    }

    public static void setModifications(ItemStack stack, List<String> modifications)
    {
        stack.getNbt().put(ToolItem.MODIFICATIONS_KEY, Util.encodeStringList(modifications));
    }

    /**
     * Code adopted from https://stackoverflow.com/questions/12967896/converting-integers-to-roman-numerals-java
     */
    private static TreeMap<Integer, String> numMap = new TreeMap<Integer, String>()
    {
        {
            put(1000, "M");
            put(900, "CM");
            put(500, "D");
            put(400, "CD");
            put(100, "C");
            put(90, "XC");
            put(50, "L");
            put(40, "XL");
            put(10, "X");
            put(9, "IX");
            put(5, "V");
            put(4, "IV");
            put(1, "I");
        }};

    public static String toRoman(int number) {
        int l =  numMap.floorKey(number);
        if ( number == l ) {
            return numMap.get(number);
        }
        return numMap.get(l) + toRoman(number-l);
    }

    public static List<ModificationContainer> getStatModifications(ItemStack stack) {

        List<ModificationContainer> mods = new ArrayList<>();

        List<String> keys = getModifications(stack);

        String[] split;
        ModificationContainer mod;

        ApiModificationRegistry inst = ApiModificationRegistry.getInstance();

        for(String key : keys)
        {
            split = key.split(":");
            mod = inst.getModification(split[0], Integer.parseInt(split[1]));

            if(mod instanceof IStatModification)
            {
                mods.add(mod);
            }
        }

        return mods;
    }

    public static List<ModificationContainer> getActiveModifications(ItemStack stack) {

        List<ModificationContainer> mods = new ArrayList<>();

        List<String> keys = getModifications(stack);

        String[] split;
        ModificationContainer mod;

        ApiModificationRegistry inst = ApiModificationRegistry.getInstance();

        for(String key : keys)
        {
            split = key.split(":");
            mod = inst.getModification(split[0], Integer.parseInt(split[1]));

            if(mod instanceof IActiveModification)
            {
                mods.add(mod);
            }
        }

        return mods;
    }

    public static List<ModificationContainer> getEnchantModifications(ItemStack stack) {

        List<ModificationContainer> mods = new ArrayList<>();

        List<String> keys = getModifications(stack);

        String[] split;
        ModificationContainer mod;

        ApiModificationRegistry inst = ApiModificationRegistry.getInstance();

        for(String key : keys)
        {
            split = key.split(":");
            mod = inst.getModification(split[0], Integer.parseInt(split[1]));

            if(mod instanceof IEnchantmentModification)
            {
                mods.add(mod);
            }
        }

        return mods;
    }
}
