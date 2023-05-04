package com.sussysyrup.smitheesfoundry.api.fluid;

import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public interface ApiSmelteryResourceRegistry {
    
    static ApiSmelteryResourceRegistry getInstance()
    {
        return RegistryInstances.smelteryResourceRegistry;
    }
    Map<Item, SmelteryResource> getSmelteryResourceMap();

    void registerSmelteryResource(Item item, SmelteryResource smelteryResource);

    void removeSmelteryResource(Item item);

    SmelteryResource getSmelteryResource(Item item);

    Map<TagKey<Item>,SmelteryResource> preSmelteryResourceMap = new HashMap<>();

    Map<TagKey<Item>,SmelteryResource> getTagSmelteryResourceMap();

    void addTagSmelteryResource(TagKey<Item> tag, SmelteryResource resource);

    void addIDSmelteryResource(Identifier identifier, SmelteryResource resource);

    void removeIDSmelteryResource(Identifier identifier);

    void clearIDSmelteryResource();

    void preReload();

    void reload();
}
