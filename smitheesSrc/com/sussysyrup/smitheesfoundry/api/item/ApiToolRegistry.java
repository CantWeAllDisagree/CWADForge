package com.sussysyrup.smitheesfoundry.api.item;

import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.item.Item;

import java.util.List;

public interface ApiToolRegistry {

    static ApiToolRegistry getInstance()
    {
        return RegistryInstances.toolRegistry;
    }

    List<String> getTools();

    List<Item> getToolsItem();

    void registerTool(String name, Item item);

    List<String> getSweepWeapons();
    void addSweepWeapon(String weapon);

    void removeSweepWeapon(String weapon);

    void preReload();

    void reload();
}
