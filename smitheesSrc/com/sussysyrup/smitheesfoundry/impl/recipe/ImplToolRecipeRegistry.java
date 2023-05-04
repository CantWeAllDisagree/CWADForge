package com.sussysyrup.smitheesfoundry.impl.recipe;

import com.sussysyrup.smitheesfoundry.api.item.PartItem;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiToolRecipe;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiToolRecipeRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImplToolRecipeRegistry implements ApiToolRecipeRegistry {

    private HashMap<String, ApiToolRecipe> reloadRecipeStringRegistry = new HashMap<>();
    private static HashMap<String, ApiToolRecipe> recipeStringRegistry = new HashMap<>();

    private HashMap<String, ApiToolRecipe> reloadToolTypeRecipeRegistry = new HashMap<>();
    private static HashMap<String, ApiToolRecipe> toolTypeRecipeRegistry = new HashMap<>();

    public ItemStack lookup(List<ItemStack> inventory)
    {
        List<String> keys = new ArrayList<>();

        Item item;

        for(ItemStack stack: inventory)
        {
            item = stack.getItem();

            if(item instanceof PartItem item1)
            {
                keys.add(item1.getPartName());
            }
            else
            {
                keys.add("empty");
            }
        }

        String key = createKey(keys.get(0), keys.get(1), keys.get(2), keys.get(3), keys.get(4));

        if(reloadRecipeStringRegistry.containsKey(key)) {
            return reloadRecipeStringRegistry.get(key).getTool(inventory);
        }
        else
        {
            return null;
        }
    }

    public ApiToolRecipe getRecipeByType(String toolName)
    {
        return reloadToolTypeRecipeRegistry.get(toolName);
    }

    public String createKey(String handle, String binding, String head, String ex1, String ex2)
    {
        return handle + "," + binding + "," + head + "," + ex1 + "," + ex2;
    }

    public void register(String toolType, String key, ApiToolRecipe recipe)
    {
        recipe.setKey(key);
        recipeStringRegistry.put(key, recipe);
        toolTypeRecipeRegistry.put(toolType, recipe);
    }

    @Override
    public void reload() {
        reloadRecipeStringRegistry.putAll(recipeStringRegistry);
        reloadToolTypeRecipeRegistry.putAll(toolTypeRecipeRegistry);
    }
}
