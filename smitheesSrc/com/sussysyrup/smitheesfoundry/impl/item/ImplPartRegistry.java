package com.sussysyrup.smitheesfoundry.impl.item;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.item.ApiPartRegistry;
import com.sussysyrup.smitheesfoundry.api.item.PartItem;
import com.sussysyrup.smitheesfoundry.api.itemgroup.ItemGroups;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.*;

public class ImplPartRegistry implements ApiPartRegistry {

    private void register(String name, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, name), item);
    }

    private List<String> reloadPrePartNames = new ArrayList<>();
    private static List<String> prePartNames = new ArrayList<>();

    private List<String> reloadPrePartCategory = new ArrayList<>();
    private static List<String> prePartCategory = new ArrayList<>();

    private HashMap<String, Float> reloadPartCostMap = new HashMap<>();
    private static HashMap<String, Float> partCostMap = new HashMap<>();

    private List<String> reloadPartNames = new ArrayList<>();
    private static List<String> partNames = new ArrayList<>();

    @Override
    public void addPrePart(String partName, String category)
    {
        prePartNames.add(partName);
        prePartCategory.add(category);
    }

    @Override
    public void removePrePart(String partName)
    {
        prePartCategory.remove(prePartNames.indexOf(partName));
        prePartNames.remove(partName);
    }

    @Override
    public String getPrePartCategory(String prePartName)
    {
        return reloadPrePartCategory.get(reloadPrePartNames.indexOf(prePartName));
    }

    @Override
    public List<String> getPrePartNames()
    {
        return reloadPrePartNames;
    }

    @Override
    public List<String> getPartNames()
    {
        return reloadPartNames;
    }

    @Override
    public void registerPartCost(String part, float cost)
    {
        partCostMap.put(part, cost);
    }

    @Override
    public void removePartCost(String part)
    {
        partCostMap.remove(part);
    }

    @Override
    public Float getPartCost(String part)
    {
        return reloadPartCostMap.get(part);
    }

    @Override
    public void preReload() {
        generateParts();

        reloadPartNames.addAll(partNames);
        reloadPartCostMap.putAll(partCostMap);
        reloadPrePartCategory.addAll(prePartCategory);
        reloadPrePartNames.addAll(prePartNames);
    }

    @Override
    public void reload() {
        reloadPartNames.addAll(partNames);
        reloadPartCostMap.putAll(partCostMap);
        reloadPrePartCategory.addAll(prePartCategory);
        reloadPrePartNames.addAll(prePartNames);
    }

    private void generateParts()
    {
        Material material;

        for(String materialId : ApiMaterialRegistry.getInstance().getKeys().stream().toList())
        {
            material = ApiMaterialRegistry.getInstance().getMaterial(materialId);

            for(String partName : prePartNames)
            {
                String id = material.getName() + "_" + partName;

                Item item = new PartItem(new FabricItemSettings().maxCount(1).group(ItemGroups.PART_GROUP), partName, materialId);

                register(id, item);

                partNames.add(id);
            }
        }
    }
}
