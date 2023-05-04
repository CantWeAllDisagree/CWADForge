package cantwe.alldisagree.CWADForge.impl.client.registry;

import cantwe.alldisagree.CWADForge.impl.client.texture.ImplTemplateTextureRegistry;
import cantwe.alldisagree.CWADForge.impl.registry.RegistryInstances;
import cantwe.alldisagree.CWADForge.impl.registry.Reloader;
import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.impl.client.fluid.ImplMoltenFluidRegistryClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ClientRegistryInstances {
    public static ImplTemplateTextureRegistry templateTextureRegistry;

    public static void registerReloader()
    {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public void reload(ResourceManager manager) {
                reloadResources();
            }

            @Override
            public Identifier getFabricId() {
                return new Identifier(Main.MODID, "resource_reload");
            }
        });
    }

    public static void flushAndCreate()
    {
        templateTextureRegistry = new ImplTemplateTextureRegistry();
    }

    public static void preReload()
    {
        ImplMoltenFluidRegistryClient.clientInit();

        templateTextureRegistry = new ImplTemplateTextureRegistry();
        templateTextureRegistry.reload();

        ClientPlayConnectionEvents.INIT.register(((handler, client) -> {
            Reloader.reload();
            RegistryInstances.reloadData();
        }));
        ClientPlayConnectionEvents.JOIN.register(((handler, sender, client) -> {
            Reloader.reload();
            RegistryInstances.reloadData();
        }));
    }

    public static void reloadResources()
    {
    }
}
