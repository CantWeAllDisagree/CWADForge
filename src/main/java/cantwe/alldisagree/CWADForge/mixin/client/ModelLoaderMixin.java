package cantwe.alldisagree.CWADForge.mixin.client;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.impl.client.texture.TextureProcessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.ItemModelGenerator;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.AffineTransformation;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
@Mixin(targets = "net.minecraft.client.render.model.ModelLoader")
public abstract class ModelLoaderMixin {
    @Final
    private Function<SpriteIdentifier, Sprite> textureGetter;

    private static final ItemModelGenerator smitheesfoundry$item_model_gen = new ItemModelGenerator();

    @Shadow
    public abstract UnbakedModel getOrLoadModel(Identifier id);

    @Shadow @Final private Map<Triple<Identifier, AffineTransformation, Boolean>, BakedModel> bakedModelCache;

    @Shadow @Final private Map<Identifier, UnbakedModel> modelsToBake;

    @Shadow @Final private Set<Identifier> modelsToLoad;

    @Shadow @Nullable public abstract BakedModel bake(Identifier id, ModelBakeSettings settings);

    @Inject(method = "bake", at = @At(value = "HEAD"),
    cancellable = true)
    private void bake(Identifier id, ModelBakeSettings settings, CallbackInfoReturnable<BakedModel> cir)
    {
        if(!id.getNamespace().equals(Main.MODID))
        {
            return;
        }

        UnbakedModel unbakedModel = this.getOrLoadModel(id);
        if(unbakedModel instanceof JsonUnbakedModel)
        {
            JsonUnbakedModel jsonUnbakedModel = ((JsonUnbakedModel) unbakedModel).getRootModel();
            if(TextureProcessor.partRenderValidator.contains(id.getPath()))
            {
               // TODO Fix Baker
                cir.setReturnValue(smitheesfoundry$item_model_gen.create(this.textureGetter, jsonUnbakedModel).bake(null, jsonUnbakedModel, this.textureGetter, settings, id, false));
            }

        }
    }

}
