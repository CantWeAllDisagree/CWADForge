package com.sussysyrup.smitheesfoundry.impl.item;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.item.ApiToolRegistry;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

public class ImplToolRegistry implements ApiToolRegistry {

    private List<String> reloadTools = new ArrayList<>();
    private static List<String> tools = new ArrayList<>();

    private List<Item> reloadToolsItem = new ArrayList<>();
    private static List<Item> toolsItem = new ArrayList<>();

    private List<String> reloadSweepWeapons = new ArrayList<>();
    private static List<String> sweepWeapons = new ArrayList<>();

    public List<String> getTools() {
        return reloadTools;
    }

    @Override
    public List<Item> getToolsItem() {
        return reloadToolsItem;
    }

    public void registerTool(String name, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, "forge_" + name), item);
        tools.add(name);
        toolsItem.add(item);
    }

    public List<String> getSweepWeapons()
    {
        return reloadSweepWeapons;
    }

    public void addSweepWeapon(String weapon)
    {
        sweepWeapons.add(weapon);
    }

    public void removeSweepWeapon(String weapon)
    {
        sweepWeapons.remove(weapon);
    }

    @Override
    public void preReload() {
        reloadTools.addAll(tools);
        reloadSweepWeapons.addAll(sweepWeapons);
        reloadToolsItem.addAll(toolsItem);
    }

    @Override
    public void reload() {
        reloadTools.addAll(tools);
        reloadSweepWeapons.addAll(sweepWeapons);
        reloadToolsItem.addAll(toolsItem);
    }
}
