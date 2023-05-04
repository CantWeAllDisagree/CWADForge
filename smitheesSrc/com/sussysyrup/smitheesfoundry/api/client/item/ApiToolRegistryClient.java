package com.sussysyrup.smitheesfoundry.api.client.item;

import com.sussysyrup.smitheesfoundry.impl.client.registry.ClientRegistryInstances;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.List;

@Environment(EnvType.CLIENT)
public interface ApiToolRegistryClient {

    static ApiToolRegistryClient getInstance()
    {
        return ClientRegistryInstances.toolRegistryClient;
    }

    List<String> getPreToolRenderedParts();

    List<String> getReloadToolRenderedParts();

    List<String> getToolRenderedParts();

    void addPreToolRenderedPart(String id);

    void addToolRenderedParts(String id);

    void preReload();

    void reload();
}
