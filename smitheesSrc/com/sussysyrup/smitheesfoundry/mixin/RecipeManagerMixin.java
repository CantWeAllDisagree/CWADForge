package com.sussysyrup.smitheesfoundry.mixin;

import com.google.gson.JsonElement;
import com.sussysyrup.smitheesfoundry.api.block.ApiVariationRegistry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class RecipeManagerMixin {

    @Inject(method = "apply", at = @At("HEAD"))
    public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
        for(Identifier id : ApiVariationRegistry.getInstance().getRecipes().keySet())
        {
            map.put(id, ApiVariationRegistry.getInstance().getRecipe(id));
        }
    }
}
