package com.sussysyrup.smitheesfoundry.mixin.client;

import com.sussysyrup.smitheesfoundry.api.client.item.ApiToolRegistryClient;
import com.sussysyrup.smitheesfoundry.client.model.ToolModel;
import com.sussysyrup.smitheesfoundry.util.ClientUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.SpriteAtlasManager;
import net.minecraft.client.render.model.json.ItemModelGenerator;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ModelLoader.class)
public class ModelLoaderMixin {

    @Shadow
    private static ItemModelGenerator ITEM_MODEL_GENERATOR;

    @Shadow
    private SpriteAtlasManager spriteAtlasManager;

    @Inject(method = "bake", at = @At("HEAD"), cancellable = true)
    private void bakeParts(Identifier id, ModelBakeSettings settings, CallbackInfoReturnable<BakedModel> cir)
    {
        if(!id.getNamespace().equals("smitheesfoundry")) return;

        if(!ApiToolRegistryClient.getInstance().getReloadToolRenderedParts().contains(id.getPath().replaceAll("#inventory", ""))) return;

        JsonUnbakedModel jsonUnbakedModel = JsonUnbakedModel.deserialize(ClientUtil.createPartJsonString("partrender", id.getPath()));

        cir.setReturnValue(ITEM_MODEL_GENERATOR.create(this.spriteAtlasManager::getSprite, jsonUnbakedModel).bake((ModelLoader) (Object)this, jsonUnbakedModel, this.spriteAtlasManager::getSprite, settings, id, false));
    }

    @Inject(method = "bake", at = @At("HEAD"), cancellable = true)
    private void bakeMods(Identifier id, ModelBakeSettings settings, CallbackInfoReturnable<BakedModel> cir)
    {
        if(!id.getNamespace().equals("smitheesfoundry")) return;

        String[] strings = id.getPath().split("/");

        if(!strings[0].equals("tool")) return;

        String testString = strings[1] + "_" + strings[2];

        if(!ToolModel.MODIFICATION_MODELS_BAKE.contains(testString)) return;

        String out = id.getPath().replaceAll("#inventory", "");

        JsonUnbakedModel jsonUnbakedModel = JsonUnbakedModel.deserialize(ClientUtil.createJsonString(out));

        cir.setReturnValue(ITEM_MODEL_GENERATOR.create(this.spriteAtlasManager::getSprite, jsonUnbakedModel).bake((ModelLoader) (Object)this, jsonUnbakedModel, this.spriteAtlasManager::getSprite, settings, id, false));
    }
}
