package cantwe.alldisagree.CWADForge.resource;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.resource.client.util.TemplateRecolourItemRecord;
import cantwe.alldisagree.CWADForge.resource.client.util.TemplateRecolourTextureRecord;
import net.minecraft.util.Identifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ResourcePackHelper {

    public static Set<TemplateRecolourItemRecord> recolourItemSet = new HashSet<>();
    public static Set<TemplateRecolourTextureRecord> recolourTextureSet = new HashSet<>();
    public static Map<Identifier, BufferedImage> templateTextureMap = new HashMap<>();

    /**
     * e.g. ResourcePackHelper.registerTexture(new Identifier("sussylib", "textures/item/test1.png"), new Identifier("textures/item/iron_ingot.png"));
     * @param id resourcepack ID
     * @param path texture path
     */
    public static void registerTexture(Identifier id, Identifier path)
    {
        try {
            SussyResourcePack.registerClientResource(id, ResourcePackHelper.class.getClassLoader().getResourceAsStream("assets/" + path.getNamespace() + "/" + path.getPath()));
        }
        catch (Exception e)
        {
            Main.LOGGER.error(e.toString());
        }
    }
    public static void registerTexture(Identifier id)
    {
        try {
            SussyResourcePack.registerClientResource(id, ResourcePackHelper.class.getClassLoader().getResourceAsStream("assets/" + id.getNamespace() + "/" + id.getPath()));
        }
        catch (Exception e)
        {
            Main.LOGGER.error(e.toString());
        }
    }

    /**
     * Register a template texture that can be used to generated recoloured items or textures
     * @param id internal template ID
     * @param path resource location
     */
    public static void registerTemplateTexture(Identifier id, Identifier path)
    {
        try {
            BufferedImage image = ImageIO.read(ResourcePackHelper.class.getClassLoader().getResourceAsStream("assets/" + path.getNamespace() + "/" + path.getPath()));
            templateTextureMap.put(id, image);
        } catch (Exception e)
        {
            Main.LOGGER.error(e.toString());
        }
    }

    /**
     * e.g. ResourcePackHelper.registerItemModel(new Identifier("sussylib", "models/item/test1.json"), new Identifier("sussylib","item/test1"));
     * @param id resourcepack ID
     * @param texturePath resource location
     */
    public static void registerItemModel(Identifier id, Identifier texturePath)
    {
        try {
            String model = "{\n" +
                    "  \"parent\": \"minecraft:item/generated" + "\",\n" +
                    "  \"textures\": {\n" +
                    "    \"layer0\": \""+ texturePath.getNamespace() + ":" + texturePath.getPath() + "\"\n" +
                    "  }\n" +
                    "}";

            SussyResourcePack.registerClientResource(id, new ByteArrayInputStream(model.getBytes(Charset.defaultCharset())));
        }
        catch (Exception e)
        {
            Main.LOGGER.error(e.toString());
        }
    }

    /**
     * ResourcePackHelper.registerItemModel(new Identifier("sussylib", "models/block/test1.json"), new Identifier("sussylib","block/test1"));
     * @param id resourcepack ID
     * @param texturePath resource location
     */
    public static void registerFluidModel(Identifier id, Identifier texturePath) {
        try {
            String model = "{\n" +
                    "  \"textures\": {\n" +
                    "    \"particle\": \"" + texturePath +"\"\n" +
                    "  }\n" +
                    "}";

            SussyResourcePack.registerClientResource(id, new ByteArrayInputStream(model.getBytes(Charset.defaultCharset())));
        } catch (Exception e) {
            Main.LOGGER.error(e.toString());
        }
    }

    /**
     * ResourcePackHelper.registerMeta(new Identifier("sussylib", "textures/block/test1.png.mcmeta"), mcmetaString)
     * @param id resourcepack ID
     * @param mcmeta mcmeta data as a string
     */
    public static void registerMeta(Identifier id, String mcmeta)
    {
        try {
            SussyResourcePack.registerClientResource(id, new ByteArrayInputStream(mcmeta.getBytes(Charset.defaultCharset())));
        } catch (IOException e)
        {
            Main.LOGGER.error(e.toString());
        }
    }
}
