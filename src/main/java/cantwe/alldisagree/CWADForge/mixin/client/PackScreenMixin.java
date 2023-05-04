package cantwe.alldisagree.CWADForge.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(PackScreen.class)
public class PackScreenMixin {

    @Inject(method = "loadPackIcon", at = @At(value = "HEAD"), cancellable = true)
    private void inject(TextureManager textureManager, ResourcePackProfile resourcePackProfile, CallbackInfoReturnable<Identifier> cir)
    {
        if(resourcePackProfile.getName().equals("Sussy Resource Pack"))
            cir.setReturnValue(new Identifier("sussylib", "icon.png"));
    }

}
