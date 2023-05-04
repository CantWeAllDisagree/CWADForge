package com.sussysyrup.smitheesfoundry.api.inventory;

import net.minecraft.inventory.SimpleInventory;

public class SmelteryInventory extends SimpleInventory {

    public SmelteryInventory(int size) {
        super(size);
    }

    @Override
    public int getMaxCountPerStack() {
        return 1;
    }
}
