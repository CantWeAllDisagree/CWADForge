package com.sussysyrup.smitheesfoundry.api.material;

import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public interface ApiMaterialRegistry {

    static ApiMaterialRegistry getInstance()
    {
        return RegistryInstances.materialRegistry;
    }

    void registerMaterial(String id, Material material);

    List<String> getKeys();

    List<Material> getMaterials();

    void removeMaterial(String id);

    Material getMaterial(String id);

    void registerColours(String materialKey, Color first, Color second, Color third, Color fourth, Color fifth, Color sixth, Color seventh);

    MaterialResource getMaterialResource(Item item);

    void registerMaterialResource(Item item, MaterialResource partRecipe);

    void registerPreMaterialResource(TagKey<Item> tagKey, MaterialResource recipe);

    void removePreMaterialResource(TagKey<Item> tagKey);

    void registerIDmaterialResource(Identifier id, MaterialResource resource);

    void removeIDmaterialResource(Identifier id);

    void clearIDmaterialResources();

    HashMap<TagKey<Item>, MaterialResource> getPreMaterialResourceMap();

    MaterialResource getPreMaterialResource(TagKey<Item> tagKey);

    void reload();
}
