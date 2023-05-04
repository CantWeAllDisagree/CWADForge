package com.sussysyrup.smitheesfoundry.impl.registry;

import com.google.gson.Gson;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.config.Configuration;
import com.sussysyrup.smitheesfoundry.impl.block.ImplAlloySmelteryRegistry;
import com.sussysyrup.smitheesfoundry.impl.block.ImplVariationRegistry;
import com.sussysyrup.smitheesfoundry.impl.casting.ImplBlockCastingRegistry;
import com.sussysyrup.smitheesfoundry.impl.casting.ImplCastingRegistry;
import com.sussysyrup.smitheesfoundry.impl.fluid.ImplAlloyRegistry;
import com.sussysyrup.smitheesfoundry.impl.fluid.ImplMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.impl.fluid.ImplSmelteryResourceRegistry;
import com.sussysyrup.smitheesfoundry.impl.item.ImplPartRegistry;
import com.sussysyrup.smitheesfoundry.impl.item.ImplToolRegistry;
import com.sussysyrup.smitheesfoundry.impl.material.ImplMaterialRegistry;
import com.sussysyrup.smitheesfoundry.impl.modification.ImplModificationRegistry;
import com.sussysyrup.smitheesfoundry.impl.recipe.ImplEnderResonatorRegistry;
import com.sussysyrup.smitheesfoundry.impl.recipe.ImplToolRecipeRegistry;
import com.sussysyrup.smitheesfoundry.impl.trait.ImplTraitRegistry;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

public class RegistryInstances {

    public static ImplAlloySmelteryRegistry alloySmelteryRegistry;
    public static ImplVariationRegistry variationRegistry;
    public static ImplBlockCastingRegistry blockCastingRegistry;
    public static ImplCastingRegistry castingRegistry;
    public static ImplAlloyRegistry alloyRegistry;
    public static ImplMoltenFluidRegistry moltenFluidRegistry;
    public static ImplSmelteryResourceRegistry smelteryResourceRegistry;
    public static ImplPartRegistry partRegistry;
    public static ImplToolRegistry toolRegistry;
    public static ImplMaterialRegistry materialRegistry;
    public static ImplToolRecipeRegistry toolRecipeRegistry;
    public static ImplTraitRegistry traitRegistry;
    public static ImplModificationRegistry modificationRegistry;
    public static ImplEnderResonatorRegistry enderResonatorRegistry;

    public static void flushAndCreate()
    {
        alloySmelteryRegistry = new ImplAlloySmelteryRegistry();
        variationRegistry = new ImplVariationRegistry();
        blockCastingRegistry = new ImplBlockCastingRegistry();
        castingRegistry = new ImplCastingRegistry();
        alloyRegistry = new ImplAlloyRegistry();
        moltenFluidRegistry = new ImplMoltenFluidRegistry();
        smelteryResourceRegistry = new ImplSmelteryResourceRegistry();
        partRegistry = new ImplPartRegistry();
        toolRegistry = new ImplToolRegistry();
        materialRegistry = new ImplMaterialRegistry();
        toolRecipeRegistry = new ImplToolRecipeRegistry();
        traitRegistry = new ImplTraitRegistry();
        modificationRegistry = new ImplModificationRegistry();
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
        variationRegistry = new ImplVariationRegistry();
        variationRegistry.reload();
        variationRegistry.postReload();

        materialRegistry = new ImplMaterialRegistry();
        materialRegistry.reload();

        partRegistry = new ImplPartRegistry();
        partRegistry.preReload();

        smelteryResourceRegistry = new ImplSmelteryResourceRegistry();
        smelteryResourceRegistry.preReload();

        moltenFluidRegistry = new ImplMoltenFluidRegistry();
        moltenFluidRegistry.preReload();

        toolRegistry = new ImplToolRegistry();
        toolRegistry.preReload();

        modificationRegistry = new ImplModificationRegistry();
        modificationRegistry.reload();
    }

    /**
     * Anything that can be registered outside of the vanilla environment goes here
     */
    public static void reloadData()
    {
        materialRegistry = new ImplMaterialRegistry();
        materialRegistry.reload();

        partRegistry = new ImplPartRegistry();
        partRegistry.reload();

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

        toolRegistry = new ImplToolRegistry();
        toolRegistry.reload();

        toolRecipeRegistry = new ImplToolRecipeRegistry();
        toolRecipeRegistry.reload();

        traitRegistry = new ImplTraitRegistry();
        traitRegistry.reload();

        modificationRegistry = new ImplModificationRegistry();
        modificationRegistry.reload();

        enderResonatorRegistry = new ImplEnderResonatorRegistry();
        enderResonatorRegistry.reload();
    }
}
