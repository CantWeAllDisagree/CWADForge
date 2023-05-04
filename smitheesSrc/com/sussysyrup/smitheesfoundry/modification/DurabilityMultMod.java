package com.sussysyrup.smitheesfoundry.modification;

import com.sussysyrup.smitheesfoundry.api.modification.IStatModification;
import com.sussysyrup.smitheesfoundry.api.modification.ModificationContainer;

public class DurabilityMultMod extends ModificationContainer implements IStatModification {

    public DurabilityMultMod(String name, int level) {
        super(name, level);
    }

    @Override
    public int durabilityAdd() {
        return 0;
    }

    @Override
    public float durabilityMultiply() {
        return 1F+((((float) getLevel())* ((float) getLevel())*2F)/100F);
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
