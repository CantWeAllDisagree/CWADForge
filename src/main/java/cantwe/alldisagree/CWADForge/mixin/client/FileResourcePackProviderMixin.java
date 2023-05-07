package cantwe.alldisagree.CWADForge.mixin.client;


/*import cantwe.alldisagree.CWADForge.resource.SussyResourcePack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.*;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

//POTENTIALLY USE //TODO this causes crashes + Is reason for missing textures + BOOTLOOP OF DEATH
@Environment(EnvType.CLIENT)
@Mixin(FileResourcePackProvider.class)
public abstract class FileResourcePackProviderMixin {


    @Final
    private ResourceType type;

    @Inject(method = "register", at = @At("HEAD"))
    private void reg(Consumer<ResourcePackProfile> profileAdder, ResourcePackProfile.Factory factory, CallbackInfo ci) {

        ResourcePackProfile profile = ResourcePackProfile.of("sussy_resource_pack", true,
                () -> new SussyResourcePack("sussy_resource_pack"), factory,
                ResourcePackProfile.InsertionPosition.TOP,ResourcePackSource.PACK_SOURCE_WORLD);

      {
            profileAdder.accept(profile);
        }
    }
}*/