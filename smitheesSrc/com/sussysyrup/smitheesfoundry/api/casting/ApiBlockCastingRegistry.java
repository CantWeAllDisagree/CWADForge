package com.sussysyrup.smitheesfoundry.api.casting;


import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public interface ApiBlockCastingRegistry {

    static ApiBlockCastingRegistry getInstance() {
        return RegistryInstances.blockCastingRegistry;
    }

    HashMap<Item, BlockCastingResource> getCastingResourceHashmap();

    HashMap<Identifier, Item> preBlockFluidMap();

    void setPreBlockFluidMap(HashMap<Identifier, Item> map);

    void addCastingResource(Item item, BlockCastingResource castingResource);

    BlockCastingResource getCastingResource(Item item);

    void addIdentifierBlock(Identifier fluid, Identifier blockID);

    void removeIdentifierBlock(Identifier fluid);

    void clearIdentifierBlocks();

    void reload();
}
