package com.sussysyrup.smitheesfoundry.impl.trait;

import com.sussysyrup.smitheesfoundry.api.trait.ApiTraitRegistry;
import com.sussysyrup.smitheesfoundry.api.trait.TraitContainer;

import java.util.HashMap;

public class ImplTraitRegistry implements ApiTraitRegistry {

    private HashMap<String, TraitContainer> reloadNameTraitMap = new HashMap<>();
    private static HashMap<String, TraitContainer> nameTraitMap = new HashMap<>();

    public void registerTrait(String name, TraitContainer trait)
    {

        nameTraitMap.put(name, trait);
    }

    public TraitContainer getTrait(String name)
    {
        return reloadNameTraitMap.get(name);
    }

    @Override
    public void reload() {
        reloadNameTraitMap.putAll(nameTraitMap);
    }

}
