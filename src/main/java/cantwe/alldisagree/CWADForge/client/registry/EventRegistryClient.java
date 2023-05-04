package cantwe.alldisagree.CWADForge.client.registry;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.api.fluid.ApiMoltenFluidRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EventRegistryClient {
    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {

        ClientSpriteRegistryCallback.event(new Identifier("textures/atlas/blocks.png")).register((spriteAtlasTexture, registry) ->
        {
            for(String s : ApiMoltenFluidRegistry.getInstance().getFluidPropertiesRegistry().keySet())
            {
                registry.register(new Identifier(Main.MODID, "block/moltenflow_" + s));
            }


            registry.register(new Identifier(Main.MODID, "block/crude_resonator_still"));
            registry.register(new Identifier(Main.MODID, "block/crude_resonator_flow"));

            registry.register(new Identifier(Main.MODID, "block/resonator_still"));
            registry.register(new Identifier(Main.MODID, "block/resonator_flow"));
        });

    }
}
