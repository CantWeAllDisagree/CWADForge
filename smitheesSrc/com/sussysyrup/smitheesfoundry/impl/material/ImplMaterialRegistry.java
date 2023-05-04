package com.sussysyrup.smitheesfoundry.impl.material;

import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiSmelteryResourceRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.SmelteryResource;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import com.sussysyrup.smitheesfoundry.api.material.MaterialResource;
import com.sussysyrup.smitheesfoundry.registry.SmelteryResourceRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImplMaterialRegistry implements ApiMaterialRegistry {

    private Map<String, Material> reloadRegistry = new HashMap<>();
    private static Map<String, Material> registry = new HashMap<>();

    private HashMap<Item, MaterialResource> reloadMaterialResourceMap = new HashMap<>();
    private static HashMap<Item, MaterialResource> materialResourceMap = new HashMap<>();

    private HashMap<TagKey<Item>, MaterialResource> reloadTagKeyMaterialResourceMap = new HashMap<>();
    private static HashMap<TagKey<Item>, MaterialResource> tagKeyMaterialResourceMap = new HashMap<>();

    private static HashMap<Identifier, MaterialResource> identifierMaterialResourceHashMap = new HashMap<>();

    public void registerMaterial(String id, Material material)
    {
            registry.put(id, material);
    }

    public List<String> getKeys()
    {
        return reloadRegistry.keySet().stream().toList();
    }

    public List<Material> getMaterials()
    {
        return reloadRegistry.values().stream().toList();
    }

    public void removeMaterial(String id)
    {
        registry.remove(id.toString());
    }

    public Material getMaterial(String id) {
        return reloadRegistry.get(id);
    }
    
    public void registerColours(String materialKey, Color first, Color second, Color third, Color fourth, Color fifth, Color sixth, Color seventh)
    {
        Material material = ApiMaterialRegistry.getInstance().getMaterial(materialKey);

        if(material == null)
        {
            return;
        }

        material.setColours(first, second, third, fourth, fifth, sixth, seventh);
    }

    public MaterialResource getMaterialResource(Item item)
    {
        return reloadMaterialResourceMap.get(item);
    }

    public void registerMaterialResource(Item item, MaterialResource partRecipe)
    {
        materialResourceMap.put(item, partRecipe);
    }

    public void registerPreMaterialResource(TagKey<Item> tagKey, MaterialResource recipe)
    {
        tagKeyMaterialResourceMap.put(tagKey, recipe);
    }

    public void removePreMaterialResource(TagKey<Item> tagKey)
    {
        tagKeyMaterialResourceMap.remove(tagKey);
    }

    @Override
    public void registerIDmaterialResource(Identifier id, MaterialResource resource) {
        identifierMaterialResourceHashMap.put(id, resource);
    }

    @Override
    public void removeIDmaterialResource(Identifier id) {
        identifierMaterialResourceHashMap.remove(id);
    }

    @Override
    public void clearIDmaterialResources() {
        identifierMaterialResourceHashMap.clear();
    }

    public HashMap<TagKey<Item>, MaterialResource> getPreMaterialResourceMap()
    {
        return reloadTagKeyMaterialResourceMap;
    }

    public MaterialResource getPreMaterialResource(TagKey<Item> tagKey)
    {
        return reloadTagKeyMaterialResourceMap.get(tagKey);
    }

    @Override
    public void reload() {
        reloadRegistry.putAll(registry);
        reloadTagKeyMaterialResourceMap.putAll(tagKeyMaterialResourceMap);
        reloadMaterialResourceMap.putAll(materialResourceMap);

        for(Identifier id : identifierMaterialResourceHashMap.keySet())
        {
            reloadMaterialResourceMap.put(Registry.ITEM.get(id), identifierMaterialResourceHashMap.get(id));
            ApiSmelteryResourceRegistry.getInstance().registerSmelteryResource(Registry.ITEM.get(id), new SmelteryResource(ApiMaterialRegistry.getInstance().getMaterial(identifierMaterialResourceHashMap.get(id).materialId()).getFluidID(), (long) (identifierMaterialResourceHashMap.get(id).materialValue() * FluidConstants.INGOT)));
        }
    }

}
