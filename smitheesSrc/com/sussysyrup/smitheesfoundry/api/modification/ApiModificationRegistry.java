package com.sussysyrup.smitheesfoundry.api.modification;

import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;

import java.util.List;
import java.util.Set;

public interface ApiModificationRegistry {

    static ApiModificationRegistry getInstance()
    {
        return RegistryInstances.modificationRegistry;
    }

    void registerModification(String name, int level, ModificationContainer modificationContainer);

    void removeModification(String name, int level);

    ModificationContainer getModification(String name, int level);

    void registerModificationRecipe(String name, int level, ModificationRecipe modificationRecipe);

    void removeModificationRecipe(ModificationRecipe modificationRecipe);

    String getFromModificationRecipe(ModificationRecipe modificationRecipe);

    ModificationRecipe getFromStringRecipe(String recipeKey);

    void reload();

    List<String> getModificationKeys();
}
