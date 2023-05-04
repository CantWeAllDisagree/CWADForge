package com.sussysyrup.smitheesfoundry.client.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.client.item.ApiToolRegistryClient;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ApiToolRegistry;
import com.sussysyrup.smitheesfoundry.api.modification.ApiModificationRegistry;
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
            for(String s : ApiToolRegistryClient.getInstance().getToolRenderedParts()) {
                registry.register(new Identifier(Main.MODID, "item/partrender_" + s));
            }
            for(String s : ApiMoltenFluidRegistry.getInstance().getFluidPropertiesRegistry().keySet())
            {
                registry.register(new Identifier(Main.MODID, "block/moltenflow_" + s));
            }

            String[] modSplit;
            for(String tool : ApiToolRegistry.getInstance().getTools())
            {
                for(String mod : ApiModificationRegistry.getInstance().getModificationKeys())
                {
                    modSplit = mod.split(":");
                    registry.register(new Identifier(Main.MODID, "tool/"+tool+"/"+modSplit[0]));
                }
            }

            registry.register(new Identifier(Main.MODID, "block/crude_resonator_still"));
            registry.register(new Identifier(Main.MODID, "block/crude_resonator_flow"));

            registry.register(new Identifier(Main.MODID, "block/resonator_still"));
            registry.register(new Identifier(Main.MODID, "block/resonator_flow"));
        });
    }
}
