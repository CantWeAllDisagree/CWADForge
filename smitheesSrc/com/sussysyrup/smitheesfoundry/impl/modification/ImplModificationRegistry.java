package com.sussysyrup.smitheesfoundry.impl.modification;

import com.sussysyrup.smitheesfoundry.api.modification.ApiModificationRegistry;
import com.sussysyrup.smitheesfoundry.api.modification.ModificationContainer;
import com.sussysyrup.smitheesfoundry.api.modification.ModificationRecipe;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ImplModificationRegistry implements ApiModificationRegistry {

    private HashMap<String, ModificationContainer> reloadModificationMap = new HashMap<>();
    private static HashMap<String, ModificationContainer> modificationMap = new HashMap<>();

    private HashMap<ModificationRecipe,String> reloadModificationRMap = new HashMap<>();
    private HashMap<String,ModificationRecipe> reloadStringRMap = new HashMap<>();
    private static HashMap<ModificationRecipe,String> modificationRMap = new HashMap<>();

    @Override
    public void registerModification(String name, int level, ModificationContainer modificationContainer) {
        modificationMap.put(name + ":" + level, modificationContainer);
    }

    @Override
    public void removeModification(String name, int level) {
        modificationMap.remove(name + ":" + level);
    }

    @Override
    public ModificationContainer getModification(String name, int level) {
        return reloadModificationMap.get(name + ":" +level);
    }

    @Override
    public void registerModificationRecipe(String name, int level, ModificationRecipe modificationRecipe) {
        modificationRMap.put(modificationRecipe, name + ":" +level);
    }

    @Override
    public void removeModificationRecipe(ModificationRecipe modificationRecipe) {
        modificationRMap.get(modificationRecipe);
    }

    @Override
    public String getFromModificationRecipe(ModificationRecipe modificationRecipe) {
        return reloadModificationRMap.get(modificationRecipe);
    }

    @Override
    public ModificationRecipe getFromStringRecipe(String recipeKey) {
        return reloadStringRMap.get(recipeKey);
    }

    @Override
    public void reload() {
        reloadModificationMap.putAll(modificationMap);
        reloadModificationRMap.putAll(modificationRMap);

        for(ModificationRecipe recipe :reloadModificationRMap.keySet())
        {
            reloadStringRMap.put(reloadModificationRMap.get(recipe), recipe);
        }
    }

    @Override
    public List<String> getModificationKeys() {
        return reloadStringRMap.keySet().stream().toList();
    }
}
