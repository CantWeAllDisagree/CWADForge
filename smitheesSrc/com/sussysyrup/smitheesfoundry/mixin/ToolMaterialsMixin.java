package com.sussysyrup.smitheesfoundry.mixin;

import com.sussysyrup.smitheesfoundry.Main;
import net.minecraft.item.ToolMaterials;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToolMaterials.class)
public abstract class ToolMaterialsMixin {

    @Inject(method = "getDurability()I", at = @At("RETURN"), cancellable = true)
    public void getDurability(CallbackInfoReturnable<Integer> cir)
    {
        if(Main.config.isDisableVanillaMaterials())
        cir.setReturnValue(1);
    }
}
