package cantwe.alldisagree.CWADForge.impl.client.fluid;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.api.fluid.ApiMoltenFluidRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ImplMoltenFluidRegistryClient {

    public static void regClient(String fluidName)
    {
        FluidRenderHandlerRegistry.INSTANCE.register(Registry.FLUID.get(new Identifier(Main.MODID, fluidName)), Registry.FLUID.get(new Identifier(Main.MODID, "flowing_"+fluidName)
        ), new SimpleFluidRenderHandler(
                new Identifier(Main.MODID, "block/still_" + fluidName),
                new Identifier(Main.MODID, "block/flow_" + fluidName)
        ));
    }

    public static void clientInit()
    {
        for(String s : ApiMoltenFluidRegistry.getInstance().getFluidPropertiesRegistry().keySet()) {
            regClient(s);
        }
    }
}
