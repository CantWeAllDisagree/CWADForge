package com.sussysyrup.smitheesfoundry.api.entrypoints;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@FunctionalInterface @Environment(EnvType.CLIENT)
public interface ClientSetup {

    void init();
}
