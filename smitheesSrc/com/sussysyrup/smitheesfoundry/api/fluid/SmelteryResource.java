package com.sussysyrup.smitheesfoundry.api.fluid;

public record SmelteryResource(String fluidID, long fluidValue) {

    public SmelteryResource
    {
        java.util.Objects.requireNonNull(fluidID);
        java.util.Objects.requireNonNull(fluidValue);
    }

}
