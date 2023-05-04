package cantwe.alldisagree.CWADForge.impl.casting;

import cantwe.alldisagree.CWADForge.api.casting.ApiBlockCastingRegistry;
import cantwe.alldisagree.CWADForge.api.casting.BlockCastingResource;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ImplBlockCastingRegistry implements ApiBlockCastingRegistry {

    private HashMap<Fluid, Item> reloadBlockFluidMap = new HashMap<>();
    private static HashMap<Identifier, Item> blockFluidMap = new HashMap<>();

    private static HashMap<Identifier, Identifier> identifierBlockFluidMap = new HashMap<>();

    private HashMap<Item, BlockCastingResource> reloadCastingResourceHashMap = new HashMap<>();
    private static HashMap<Item, BlockCastingResource> castingResourceHashMap = new HashMap<>();

    @Override
    public HashMap<Item, BlockCastingResource> getCastingResourceHashmap() {
        return reloadCastingResourceHashMap;
    }

    @Override
    public HashMap<Identifier, Item> preBlockFluidMap() {
        return blockFluidMap;
    }

    @Override
    public void setPreBlockFluidMap(HashMap<Identifier, Item> map) {
        blockFluidMap = map;
    }

    @Override
    public void addCastingResource(Item item, BlockCastingResource castingResource)
    {
        castingResourceHashMap.put(item, castingResource);
    }

    @Override
    public BlockCastingResource getCastingResource(Item item)
    {
        return reloadCastingResourceHashMap.get(item);
    }

    @Override
    public void addIdentifierBlock(Identifier fluid, Identifier blockID) {
        identifierBlockFluidMap.put(fluid, blockID);
    }

    @Override
    public void removeIdentifierBlock(Identifier fluid) {
        identifierBlockFluidMap.remove(fluid);
    }

    @Override
    public void clearIdentifierBlocks() {
        identifierBlockFluidMap.clear();
    }

    @Override
    public void reload()
    {
        for(Identifier fluid : blockFluidMap.keySet())
        {
            reloadBlockFluidMap.put(Registry.FLUID.get(fluid), blockFluidMap.get(fluid));
        }

        identifiers();

        addCastingResource(Items.AIR, new BlockCastingResource(reloadBlockFluidMap));

        reloadCastingResourceHashMap.putAll(castingResourceHashMap);
    }

    private void identifiers()
    {
        for(Identifier fluid : identifierBlockFluidMap.keySet())
        {
            reloadBlockFluidMap.put(Registry.FLUID.get(fluid), Registry.ITEM.get(identifierBlockFluidMap.get(fluid)));
        }
    }
}
