package com.sussysyrup.smitheesfoundry.modification;

import com.sussysyrup.smitheesfoundry.api.modification.IStatModification;
import com.sussysyrup.smitheesfoundry.api.modification.ModificationContainer;

public class MiningSpeedBonusMod extends ModificationContainer implements IStatModification {

    public MiningSpeedBonusMod(String name, int level) {
        super(name, level);
    }

    @Override
    public int durabilityAdd() {
        return 0;
    }

    @Override
    public float damageAdd() {
        return 0;
    }

    @Override
    public float swingSpeedAdd() {
        return 0;
    }

    @Override
    public float miningSpeedAdd() {
        return 1.25F * getLevel();
    }

    @Override
    public float miningLevelAdd() {
        return 0;
    }
}
