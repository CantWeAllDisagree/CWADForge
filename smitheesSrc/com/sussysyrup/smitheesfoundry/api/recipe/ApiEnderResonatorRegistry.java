package com.sussysyrup.smitheesfoundry.api.recipe;

import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public interface ApiEnderResonatorRegistry {

    static ApiEnderResonatorRegistry getInstance()
    {
        return RegistryInstances.enderResonatorRegistry;
    }

    void registerRecipe(Identifier inputItem, EnderResonatorRecipe resonatorRecipe);

    void removeRecipe(Identifier inputItem);

    EnderResonatorRecipe getRecipe(Identifier inputItem);

    HashMap<Identifier, EnderResonatorRecipe> getMap();

    void reload();
}
