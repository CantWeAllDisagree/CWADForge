package cantwe.alldisagree.CWADForge.api.client.texture;

import cantwe.alldisagree.CWADForge.impl.client.registry.ClientRegistryInstances;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

import java.awt.image.BufferedImage;
import java.util.Set;

@Environment(EnvType.CLIENT)
public interface ApiTemplateTextureRegistry {

    static ApiTemplateTextureRegistry getInstance()
    {
        return ClientRegistryInstances.templateTextureRegistry;
    }
    boolean preTextureMapContains(String name);

    BufferedImage getTexture(String name);

    void registerTexture(String name, Identifier resourceID);

    void removeTexture(String name);

    void registerPartRender(String modid, String part, String tool);

    Set<Identifier> getPartRenders();

    void clearTextures();

    void reload();
}
