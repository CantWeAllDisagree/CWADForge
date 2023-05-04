package cantwe.alldisagree.CWADForge;

import cantwe.alldisagree.CWADForge.config.Configuration;
import cantwe.alldisagree.CWADForge.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PreLaunch implements PreLaunchEntrypoint {

    public static ClassLoader classLoader;

    @Override
    public void onPreLaunch() {

        classLoader = PreLaunch.class.getClassLoader();

        Path path = null;
        try {
            path = Paths.get(PreLaunch.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Util.setResourcePath(path);

        config();
    }

    public void config()
    {
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

            gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter writer = new FileWriter(configFile)) {
                gson.toJson(Main.config, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Main.config = new Configuration();

            try (FileWriter writer = new FileWriter(configFile)) {
                gson.toJson(Main.config, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
