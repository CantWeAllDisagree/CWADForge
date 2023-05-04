package cantwe.alldisagree.CWADForge;

import cantwe.alldisagree.CWADForge.api.entrypoints.CommonSetup;
import cantwe.alldisagree.CWADForge.config.Configuration;
import cantwe.alldisagree.CWADForge.impl.registry.RegistryInstances;
import cantwe.alldisagree.CWADForge.networking.c2s.C2SReceivers;
import cantwe.alldisagree.CWADForge.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


		FluidRegistry.main();

		SmelteryResourceRegistry.main();

		BlocksRegistry.main();

		ModScreenHandlerRegistry.main();
		C2SReceivers.main();

		RecipeRegistry.main();


		ParticleRegistry.main();

		RegistryInstances.registerReloader();
		RegistryInstances.preReloadData();


		//new layout

		//Register (have entrypoint for this here)
		ItemsRegistry.main();

	}
}
