package com.sussysyrup.smitheesfoundry.impl.client.item;

import com.sussysyrup.smitheesfoundry.api.client.item.ApiToolRegistryClient;
import com.sussysyrup.smitheesfoundry.api.item.ApiToolRegistry;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.client.model.provider.ToolModelProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class ImplToolRegistryClient implements ApiToolRegistryClient {

    private Set<String> reloadPreToolRenderedParts = new HashSet<>();
    private static Set<String> preToolRenderedParts = new HashSet<>();

    private Set<String> reloadToolRenderedParts = new HashSet<>();
    private static Set<String> toolRenderedParts = new HashSet<>();

    public static void itemRenderingInit()
    {
        for(String materialId : ApiMaterialRegistry.getInstance().getKeys().stream().toList())
        {
            for(String partName : preToolRenderedParts)
            {
                String partId = materialId + "_" + partName;
                toolRenderedParts.add(partId);
            }
        }

        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new ToolModelProvider());
    }


    public List<String> getPreToolRenderedParts() {
        return reloadPreToolRenderedParts.stream().toList();
    }

    public List<String> getReloadToolRenderedParts() {
        return reloadToolRenderedParts.stream().toList();
    }

    @Override
    public List<String> getToolRenderedParts() {
        return toolRenderedParts.stream().toList();
    }

    public void addPreToolRenderedPart(String id)
    {
        preToolRenderedParts.add(id);
    }

    public void addToolRenderedParts(String id)
    {
        toolRenderedParts.add(id);
    }

    @Override
    public void preReload() {
        reloadPreToolRenderedParts.addAll(preToolRenderedParts);
        reloadToolRenderedParts.addAll(toolRenderedParts);
    }

    @Override
    public void reload() {
        reloadPreToolRenderedParts.addAll(preToolRenderedParts);
        reloadToolRenderedParts.addAll(toolRenderedParts);
    }
}
