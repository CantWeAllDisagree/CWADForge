package cantwe.alldisagree.CWADForge.mixin.client;


import cantwe.alldisagree.CWADForge.resource.SussyResourcePack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.FileResourcePackProvider;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

//POTENTIALLY USE //TODO this causes crashes + Is reason for missing textures
@Environment(EnvType.CLIENT)
@Mixin(FileResourcePackProvider.class)
public abstract class FileResourcePackProviderMixin {


    @Final
    private ResourceType type;

    @Inject(method = "register", at = @At("HEAD"))
    private void reg(Consumer<ResourcePackProfile> profileAdder, ResourcePackProfile.Factory factory, CallbackInfo ci) {

        ResourcePackProfile profile = ResourcePackProfile.of("Sussy Resource Pack", Text.literal("Sussy Resource Pack"),
                true, () -> new SussyResourcePack("Sussy Resource Pack"), this.type, ResourcePackProfile.InsertionPosition.BOTTOM,
                ResourcePackSource.onlyName());

        if (profile != null) {
            profileAdder.accept(profile);
        }
    }
}