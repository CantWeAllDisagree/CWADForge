package com.sussysyrup.smitheesfoundry.api.trait;

import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;

public interface ApiTraitRegistry {

    static ApiTraitRegistry getInstance()
    {
        return RegistryInstances.traitRegistry;
    }

    void registerTrait(String name, TraitContainer trait);

    TraitContainer getTrait(String name);

    void reload();
}
