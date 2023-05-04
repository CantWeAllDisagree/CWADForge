package cantwe.alldisagree.CWADForge.mixin.client;

import cantwe.alldisagree.CWADForge.util.TranslationUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(LanguageManager.class)
public abstract class LanguageManagerMixin {

    @Shadow public abstract LanguageDefinition getLanguage();

    @Inject(method = "setLanguage", at = @At("TAIL"))
    public void setLanguage(LanguageDefinition language, CallbackInfo ci)
    {
        TranslationUtil.adjustForLanguage(language.getCode());
    }

    @Inject(method = "reload", at = @At("TAIL"))
    public void reload(ResourceManager manager, CallbackInfo ci)
    {
        TranslationUtil.adjustForLanguage(getLanguage().getCode());
    }
}
