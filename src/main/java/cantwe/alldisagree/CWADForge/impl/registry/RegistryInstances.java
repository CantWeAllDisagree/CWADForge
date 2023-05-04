package cantwe.alldisagree.CWADForge.impl.registry;

import cantwe.alldisagree.CWADForge.config.Configuration;
import cantwe.alldisagree.CWADForge.impl.material.ImplMaterialRegistry;
import com.google.gson.Gson;
import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.impl.block.ImplAlloySmelteryRegistry;
import cantwe.alldisagree.CWADForge.impl.casting.ImplBlockCastingRegistry;
import cantwe.alldisagree.CWADForge.impl.casting.ImplCastingRegistry;
import cantwe.alldisagree.CWADForge.impl.fluid.ImplAlloyRegistry;
import cantwe.alldisagree.CWADForge.impl.fluid.ImplMoltenFluidRegistry;
import cantwe.alldisagree.CWADForge.impl.fluid.ImplSmelteryResourceRegistry;
import cantwe.alldisagree.CWADForge.impl.recipe.ImplEnderResonatorRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

public class RegistryInstances {

    public static ImplAlloySmelteryRegistry alloySmelteryRegistry;
    public static ImplBlockCastingRegistry blockCastingRegistry;
    public static ImplCastingRegistry castingRegistry;
    public static ImplAlloyRegistry alloyRegistry;
    public static ImplMoltenFluidRegistry moltenFluidRegistry;
    public static ImplSmelteryResourceRegistry smelteryResourceRegistry;
    public static ImplMaterialRegistry materialRegistry;
    public static ImplEnderResonatorRegistry enderResonatorRegistry;

    public static void flushAndCreate()
    {
        alloySmelteryRegistry = new ImplAlloySmelteryRegistry();
        blockCastingRegistry = new ImplBlockCastingRegistry();
        castingRegistry = new ImplCastingRegistry();
        alloyRegistry = new ImplAlloyRegistry();
        moltenFluidRegistry = new ImplMoltenFluidRegistry();
        smelteryResourceRegistry = new ImplSmelteryResourceRegistry();
        materialRegistry = new ImplMaterialRegistry();
        enderResonatorRegistry = new ImplEnderResonatorRegistry();
    }


    public static void registerReloader()
    {
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(((server, resourceManager, success) -> {
            Reloader.reload();
            reloadData();

            Path configDir = FabricLoader.getInstance().getConfigDir().resolve("smithees-foundry.json");

            File configFile = new File(configDir.toUri());

            if(configFile.exists())
            {
                Gson gson = new Gson();

                try (Reader reader = new FileReader(configFile)) {

                    Main.config = gson.fromJson(reader, Configuration.class);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }));
        ServerLifecycleEvents.SERVER_STARTING.register((server -> {
            Reloader.reload();
            reloadData();
        }));
    }

    /**
     * Anything that depends on vanilla instance creation goes here
     */
    public static void preReloadData()
    {

        materialRegistry = new ImplMaterialRegistry();
        materialRegistry.reload();

        smelteryResourceRegistry = new ImplSmelteryResourceRegistry();
        smelteryResourceRegistry.preReload();

        moltenFluidRegistry = new ImplMoltenFluidRegistry();
        moltenFluidRegistry.preReload();

    }

    /**
     * Anything that can be registered outside of the vanilla environment goes here
     */
    public static void reloadData()
    {
        materialRegistry = new ImplMaterialRegistry();
        materialRegistry.reload();


        alloySmelteryRegistry = new ImplAlloySmelteryRegistry();
        alloySmelteryRegistry.reload();

        castingRegistry = new ImplCastingRegistry();
        castingRegistry.preReload();
        castingRegistry.reload();

        blockCastingRegistry = new ImplBlockCastingRegistry();
        blockCastingRegistry.reload();

        smelteryResourceRegistry = new ImplSmelteryResourceRegistry();
        smelteryResourceRegistry.preReload();
        smelteryResourceRegistry.reload();

        moltenFluidRegistry = new ImplMoltenFluidRegistry();
        moltenFluidRegistry.reload();

        alloyRegistry = new ImplAlloyRegistry();
        alloyRegistry.reload();


        enderResonatorRegistry = new ImplEnderResonatorRegistry();
        enderResonatorRegistry.reload();
    }
}
