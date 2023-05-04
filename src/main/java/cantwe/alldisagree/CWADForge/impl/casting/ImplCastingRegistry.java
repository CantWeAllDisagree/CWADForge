package cantwe.alldisagree.CWADForge.impl.casting;

import cantwe.alldisagree.CWADForge.api.casting.ApiCastingRegistry;
import cantwe.alldisagree.CWADForge.api.casting.CastingResource;
import cantwe.alldisagree.CWADForge.api.item.CastItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ImplCastingRegistry implements ApiCastingRegistry {

    private HashMap<Fluid, Item> reloadIngotFluidMap = new HashMap<>();
    private static HashMap<Identifier, Item> ingotFluidMap = new HashMap<>();

    private static HashMap<Identifier, Identifier> ingotIdentifier = new HashMap<>();

    private HashMap<Fluid, Item> reloadNuggetFluidMap = new HashMap<>();
    private static HashMap<Identifier, Item> nuggetFluidMap = new HashMap<>();

    private static HashMap<Identifier, Identifier> nuggetIdentifier = new HashMap<>();

    private Map<String, CastItem> reloadTypeCastItemMap = new HashMap<>();
    private static Map<String, CastItem> typeCastItemMap = new HashMap<>();

    private Map<Item, String> reloadItemTypeMap = new HashMap<>();
    private static Map<Item, String> itemTypeMap = new HashMap<>();
    private static Map<String, CastingResource> typeCastingResourceMap = new HashMap<>();

    @Override
    public HashMap<Identifier, Item> getPreIngotFluidMap() {
        return ingotFluidMap;
    }

    @Override
    public HashMap<Identifier, Item> getPreNuggetFluidMap() {
        return nuggetFluidMap;
    }

    @Override
    public Map<Item, String> getItemTypeMap() {
        return reloadItemTypeMap;
    }

    @Override
    public Map<String, CastingResource> getTypeCastingMap() {
        return typeCastingResourceMap;
    }

    @Override
    public void setPreIngotFluidMap(HashMap<Identifier, Item> map) {
        ingotFluidMap = map;
    }

    @Override
    public void setPreNuggetFluidMap(HashMap<Identifier, Item> map) {
        nuggetFluidMap = map;
    }

    @Override
    public void addCastItem(String type, CastItem item)
    {
        typeCastItemMap.put(type, item);
    }

    @Override
    public void removeCastItem(String type)
    {
        typeCastItemMap.remove(type);
    }

    @Override
    public void addItemToType(String type, Item item)
    {
        itemTypeMap.put(item, type);
    }

    @Override
    public void removeItemToType(Item item)
    {
        itemTypeMap.remove(item);
    }

    @Override
    public String getTypeFromItem(Item item)
    {
        return reloadItemTypeMap.get(item);
    }

    @Override
    public CastItem getCastItem(String type)
    {
        return reloadTypeCastItemMap.get(type);
    }

    @Override
    public void addCastingResource(String type, CastingResource castingResource)
    {
        typeCastingResourceMap.put(type, castingResource);
    }

    @Override
    public void removeCastingResource(String type)
    {
        typeCastingResourceMap.remove(type);
    }

    @Override
    public CastingResource getCastingResource(String type)
    {
        return typeCastingResourceMap.get(type);
    }

    @Override
    public void addIdentifierNugget(Identifier fluid, Identifier nuggetID) {
        nuggetIdentifier.put(fluid, nuggetID);
    }

    @Override
    public void removeIdentifierNugget(Identifier fluid) {
        nuggetIdentifier.remove(fluid);
    }

    @Override
    public void clearIdentifierNuggets() {
        nuggetIdentifier.clear();
    }

    @Override
    public void addIdentifierIngot(Identifier fluid, Identifier ingotID) {
        ingotIdentifier.put(fluid, ingotID);
    }

    @Override
    public void removeIdentifierIngot(Identifier fluid) {
        ingotIdentifier.remove(fluid);
    }

    @Override
    public void clearIdentifierIngots() {
        ingotIdentifier.clear();
    }

    @Override
    public void preReload() {
        processor();
        identifiers();

        for(Item item : reloadIngotFluidMap.values())
        {
            addItemToType("ingot", item);
        }

        for(Item item : reloadNuggetFluidMap.values())
        {
            addItemToType("nugget", item);
        }

        addCastingResource("ingot", new CastingResource(FluidConstants.INGOT, reloadIngotFluidMap));
        addCastingResource("nugget", new CastingResource(FluidConstants.NUGGET, reloadNuggetFluidMap));
    }

    @Override
    public void reload()
    {
        reloadItemTypeMap.putAll(itemTypeMap);
        reloadTypeCastItemMap.putAll(typeCastItemMap);
    }

    private void processor()
    {
        for(Identifier fluid : nuggetFluidMap.keySet())
        {
            reloadNuggetFluidMap.put(Registry.FLUID.get(fluid), nuggetFluidMap.get(fluid));
        }
        for(Identifier fluid : ingotFluidMap.keySet())
        {
            reloadIngotFluidMap.put(Registry.FLUID.get(fluid), ingotFluidMap.get(fluid));
        }
    }
    private void identifiers()
    {
        for(Identifier fluid : nuggetIdentifier.keySet())
        {
            reloadNuggetFluidMap.put(Registry.FLUID.get(fluid), Registry.ITEM.get(nuggetIdentifier.get(fluid)));
        }
        for(Identifier fluid : ingotIdentifier.keySet())
        {
            reloadIngotFluidMap.put(Registry.FLUID.get(fluid), Registry.ITEM.get(ingotIdentifier.get(fluid)));
        }
    }
}
