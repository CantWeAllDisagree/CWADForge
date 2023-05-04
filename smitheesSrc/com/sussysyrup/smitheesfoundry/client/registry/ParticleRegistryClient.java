package com.sussysyrup.smitheesfoundry.client.registry;

import com.sussysyrup.smitheesfoundry.client.particle.NoGravCrackParticle;
import com.sussysyrup.smitheesfoundry.registry.ParticleRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

@Environment(EnvType.CLIENT)
public class ParticleRegistryClient {


    public static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.NO_GRAV_CRACK, new NoGravCrackParticle.NoGravFactory());
    }
}
