package com.sussysyrup.smitheesfoundry.api.item;

import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import java.util.List;

public interface ApiPartRegistry {

    static ApiPartRegistry getInstance()
    {
        return RegistryInstances.partRegistry;
    }

    void addPrePart(String partName, String category);

    void removePrePart(String partName);

    String getPrePartCategory(String prePartName);

    List<String> getPrePartNames();

    List<String> getPartNames();

    void registerPartCost(String part, float cost);

    void removePartCost(String part);

    Float getPartCost(String part);

    void preReload();

    void reload();
}
