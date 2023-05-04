package cantwe.alldisagree.CWADForge.registry;

import cantwe.alldisagree.CWADForge.Main;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

public class ParticleRegistry {

    public static final ParticleType<ItemStackParticleEffect> NO_GRAV_CRACK = FabricParticleTypes.complex(ItemStackParticleEffect.PARAMETERS_FACTORY);

    public static void main()
    {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MODID, "nograv_crack"), NO_GRAV_CRACK);
    }
}
