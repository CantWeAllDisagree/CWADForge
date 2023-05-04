package cantwe.alldisagree.CWADForge.impl.client.texture;

import cantwe.alldisagree.CWADForge.util.ClientUtil;
import cantwe.alldisagree.CWADForge.api.client.texture.ApiTemplateTextureRegistry;
//import cantwe.alldisagree.smitheesfoundry.resource.ResourcePackHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class ImplTemplateTextureRegistry implements ApiTemplateTextureRegistry {

    private HashMap<String, BufferedImage> reloadTextureMap = new HashMap<>();
    private static HashMap<String, BufferedImage> textureMap = new HashMap<>();

    private static Set<Identifier> partRenders = new HashSet<>();
    public boolean preTextureMapContains(String name)
    {
        return textureMap.containsKey(name);
    }

    @Override
    public BufferedImage getTexture(String name)
    {
        return ClientUtil.copyImage(reloadTextureMap.get(name));
    }

    public void registerTexture(String name, Identifier resourceID)
    {
        textureMap.put(name, ClientUtil.getImageFromID(resourceID));
    }

    public void removeTexture(String name)
    {
        textureMap.remove(name);
    }

    @Override
    public void registerPartRender(String modid, String part, String tool) {
        partRenders.add(new Identifier(modid, tool + "_" + part));
   //TODO    // ResourcePackHelper.registerTemplateTexture(new Identifier(modid, tool + "_" + part), new Identifier(modid, "textures/tool/" + tool + "/" + part + ".png"));
    }

    @Override
    public Set<Identifier> getPartRenders() {
        return partRenders;
    }

    @Override
    public void clearTextures() {
        textureMap.clear();
    }

    @Override
    public void reload() {
        reloadTextureMap.putAll(textureMap);
    }
}
