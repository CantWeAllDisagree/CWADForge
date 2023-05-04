package com.sussysyrup.smitheesfoundry.impl.fluid;

import com.sussysyrup.smitheesfoundry.api.fluid.ApiSmelteryResourceRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.SmelteryResource;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import java.util.HashMap;
import java.util.Map;

public class ImplSmelteryResourceRegistry implements ApiSmelteryResourceRegistry {

    private Map<Item, SmelteryResource> reloadSmelteryResourceMap = new HashMap<>();
    private static Map<Item, SmelteryResource> smelteryResourceMap = new HashMap<>();

    private Map<TagKey<Item>,SmelteryResource> reloadTagSmelteryResourceMap = new HashMap<>();
    private static Map<TagKey<Item>,SmelteryResource> tagSmelteryResourceMap = new HashMap<>();

    private static Map<Identifier, SmelteryResource> identifierSmelteryResourceMap = new HashMap<>();

    public  Map<Item, SmelteryResource> getSmelteryResourceMap()
    {
        return reloadSmelteryResourceMap;
    }

    public void registerSmelteryResource(Item item, SmelteryResource smelteryResource)
    {
        smelteryResourceMap.put(item, smelteryResource);
    }

    public void removeSmelteryResource(Item item)
    {
        smelteryResourceMap.remove(item);
    }

    public SmelteryResource getSmelteryResource(Item item)
    {
        return reloadSmelteryResourceMap.get(item);
    }

    public Map<TagKey<Item>,SmelteryResource> getTagSmelteryResourceMap()
    {
        return reloadTagSmelteryResourceMap;
    }

    public void addTagSmelteryResource(TagKey<Item> tag, SmelteryResource resource)
    {
        tagSmelteryResourceMap.put(tag, resource);
    }

    @Override
    public void addIDSmelteryResource(Identifier identifier, SmelteryResource resource) {
        identifierSmelteryResourceMap.put(identifier, resource);
    }

    @Override
    public void removeIDSmelteryResource(Identifier identifier) {
        identifierSmelteryResourceMap.remove(identifier);
    }

    @Override
    public void clearIDSmelteryResource() {
        identifierSmelteryResourceMap.clear();
    }

    @Override
    public void preReload() {
        reloadTagSmelteryResourceMap.putAll(tagSmelteryResourceMap);
    }

    @Override
    public void reload() {
        reloadTagSmelteryResourceMap.putAll(tagSmelteryResourceMap);
        reloadSmelteryResourceMap.putAll(smelteryResourceMap);

        for(Identifier id : identifierSmelteryResourceMap.keySet())
        {
            reloadSmelteryResourceMap.put(Registry.ITEM.get(id), identifierSmelteryResourceMap.get(id));
        }
    }
}
