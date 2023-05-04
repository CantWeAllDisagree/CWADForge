package com.sussysyrup.smitheesfoundry.modification;

import com.sussysyrup.smitheesfoundry.api.modification.IStatModification;
import com.sussysyrup.smitheesfoundry.api.modification.ModificationContainer;

public class DurabilityBonusMod extends ModificationContainer implements IStatModification {

    public DurabilityBonusMod(String name, int level) {
        super(name, level);
    }

    @Override
    public int durabilityAdd() {
        return getLevel() * 500;
    }

    @Override
    public float durabilityMultiply() {
        return 1;
    }

    @Override
    public float damageAdd() {
        return 0;
    }

    @Override
    public float damageMultiply() {
        return 1;
    }

    @Override
    public float swingSpeedAdd() {
        return 0;
    }

    @Override
    public float swingSpeedMultiply() {
        return 1;
    }

    @Override
    public float miningSpeedAdd() {
        return 0;
    }

    @Override
    public float miningSpeedMultiply() {
        return 1;
    }

    @Override
    public float miningLevelAdd() {
        return 0;
    }
}
