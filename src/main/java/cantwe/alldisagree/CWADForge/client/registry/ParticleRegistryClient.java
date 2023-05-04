package cantwe.alldisagree.CWADForge.client.registry;

import cantwe.alldisagree.CWADForge.registry.ParticleRegistry;
import cantwe.alldisagree.CWADForge.client.particle.NoGravCrackParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

@Environment(EnvType.CLIENT)
public class ParticleRegistryClient {


    public static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(ParticleRegistry.NO_GRAV_CRACK, new NoGravCrackParticle.NoGravFactory());
    }
}
