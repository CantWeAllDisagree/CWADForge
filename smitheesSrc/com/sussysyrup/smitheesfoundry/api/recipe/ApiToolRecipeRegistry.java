package com.sussysyrup.smitheesfoundry.api.recipe;

import com.sussysyrup.smitheesfoundry.api.item.PartItem;
import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ApiToolRecipeRegistry {

    static ApiToolRecipeRegistry getInstance()
    {
        return RegistryInstances.toolRecipeRegistry;
    }
    ItemStack lookup(List<ItemStack> inventory);

    ApiToolRecipe getRecipeByType(String toolName);

    String createKey(String handle, String binding, String head, String ex1, String ex2);

    void register(String toolType, String key, ApiToolRecipe recipe);

    void reload();
}
