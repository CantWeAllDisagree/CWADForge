package com.sussysyrup.smitheesfoundry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sussysyrup.smitheesfoundry.api.entrypoints.CommonSetup;
import com.sussysyrup.smitheesfoundry.config.Configuration;
import com.sussysyrup.smitheesfoundry.impl.registry.RegistryInstances;
import com.sussysyrup.smitheesfoundry.networking.c2s.C2SReceivers;
import com.sussysyrup.smitheesfoundry.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Path;

public class Main implements ModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("smitheesfoundry");
	public static final String MODID = "smitheesfoundry";

	public static Configuration config;

	@Override
	public void onInitialize() {
		RegistryInstances.flushAndCreate();

		FabricLoader.getInstance().getEntrypoints("smitheesfoundry:setup", CommonSetup.class).forEach(c -> c.init());

		AlloyRegistry.main();
		CastingRegistry.preInit();

		MaterialRegistry.main();

		PartRegistry.main();

		VariantRegistry.main();

		FluidRegistry.main();

		SmelteryResourceRegistry.main();

		BlocksRegistry.main();
		ItemsRegistry.main();

		ModScreenHandlerRegistry.main();
		C2SReceivers.main();

		RecipeRegistry.main();

		ModificationRegistry.main();

		ParticleRegistry.main();

		RegistryInstances.registerReloader();
		RegistryInstances.preReloadData();
	}
}
