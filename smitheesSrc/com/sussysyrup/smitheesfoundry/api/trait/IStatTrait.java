package com.sussysyrup.smitheesfoundry.api.trait;

public interface IStatTrait {

    int durabilityAdd();

    default float durabilityMultiply()
    {
        return 1F;
    }

    float damageAdd();

    default float damageMultiply()
    {
        return 1F;
    }

    float swingSpeedAdd();

    default float swingSpeedMultiply()
    {
        return 1F;
    }

    float miningSpeedAdd();

    default float miningSpeedMultiply()
    {
        return 1F;
    }

    float miningLevelAdd();

}
