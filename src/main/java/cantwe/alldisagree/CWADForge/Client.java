package cantwe.alldisagree.CWADForge;

import cantwe.alldisagree.CWADForge.api.entrypoints.ClientSetup;
import cantwe.alldisagree.CWADForge.client.registry.*;
import cantwe.alldisagree.CWADForge.impl.client.registry.ClientRegistryInstances;
import cantwe.alldisagree.CWADForge.impl.client.texture.TextureProcessor;
import cantwe.alldisagree.CWADForge.networking.s2c.S2CReceivers;
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

        FluidRegistryClient.clientInit();

        ItemsRegistryClient.clientInit();

        BlocksRegistryClient.clientInit();

        EventRegistryClient.clientInit();


        TextureProcessor.process();

        S2CReceivers.clientInit();

        ParticleRegistryClient.clientInit();

        ClientRegistryInstances.preReload();
        ClientRegistryInstances.registerReloader();
    }

}
