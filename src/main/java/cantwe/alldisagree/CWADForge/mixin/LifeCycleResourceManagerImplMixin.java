package cantwe.alldisagree.CWADForge.mixin;

import cantwe.alldisagree.CWADForge.resource.ResourcePackHelper;
import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.resource.SussyResourcePack;
import cantwe.alldisagree.CWADForge.resource.client.util.RecolourUtil;
import cantwe.alldisagree.CWADForge.resource.client.util.TemplateRecolourItemRecord;
import cantwe.alldisagree.CWADForge.resource.client.util.TemplateRecolourTextureRecord;
import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.List;


@Mixin(LifecycledResourceManagerImpl.class)
public class LifeCycleResourceManagerImplMixin {

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/util/List;copyOf(Ljava/util/Collection;)Ljava/util/List;"))
    private void initialiser(ResourceType type, List packs, CallbackInfo ci)
    {
        if(type.equals(ResourceType.CLIENT_RESOURCES))
        {
            for(TemplateRecolourTextureRecord record : ResourcePackHelper.recolourTextureSet)
            {
                if(!SussyResourcePack.clientResources.containsKey(record.outputID()))
                {
                    try {
                        SussyResourcePack.registerClientResource(record.outputID(), RecolourUtil.recolour(ResourcePackHelper.templateTextureMap.get(record.textureID()), record.colourScheme()));
                    } catch (IOException e)
                    {
                        Main.LOGGER.error(e.toString());
                    }
                }
            }

            for(TemplateRecolourItemRecord record : ResourcePackHelper.recolourItemSet)
            {
                if(!SussyResourcePack.clientResources.containsKey(record.outputID()))
                {
                    try {
                        String modelTex = record.outputID().getPath().replace("textures/", "").replace(".png", "");
                        SussyResourcePack.registerClientResource(record.outputID(), RecolourUtil.recolour(ResourcePackHelper.templateTextureMap.get(record.textureID()), record.colourScheme()));
                        ResourcePackHelper.registerItemModel(record.outputModelID(), new Identifier(record.outputID().getNamespace(), modelTex));
                    } catch (IOException e)
                    {
                        Main.LOGGER.error(e.toString());
                    }
                }
            }
        }
    }
}