package com.sussysyrup.smitheesfoundry;

import com.sussysyrup.smitheesfoundry.api.entrypoints.ClientSetup;
import com.sussysyrup.smitheesfoundry.client.registry.*;
import com.sussysyrup.smitheesfoundry.impl.client.registry.ClientRegistryInstances;
import com.sussysyrup.smitheesfoundry.networking.s2c.S2CReceivers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    //Order of loading doesn't matter here so go mad
    @Override
    public void onInitializeClient() {
        ClientRegistryInstances.flushAndCreate();

        FabricLoader.getInstance().getEntrypoints("smitheesfoundry:client", ClientSetup.class).forEach(c -> c.init());

        ModScreenRegistry.clientInit();

        MaterialRegistryClient.clientInit();

        FluidRegistryClient.clientInit();

        ItemsRegistryClient.clientInit();

        BlocksRegistryClient.clientInit();

        EventRegistryClient.clientInit();

        TextureRegistry.clientInit();

        S2CReceivers.clientInit();

        ParticleRegistryClient.clientInit();

        ClientRegistryInstances.preReload();
        ClientRegistryInstances.registerReloader();
    }

}
