package com.sussysyrup.smitheesfoundry.mixin;

import com.sussysyrup.smitheesfoundry.api.block.ApiVariationRegistry;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.fabricmc.fabric.impl.loot.LootUtil;
import net.minecraft.block.Block;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

//This mod depends on fabric api so this compat is necessary
@Mixin(LootUtil.class)
public class LootUtilMixin {

    @Inject(method = "determineSource", at = @At("HEAD"), cancellable = true)
    private static void determineSource(Identifier packSource, ResourceManager resource, CallbackInfoReturnable<LootTableSource> cir)
    {
        List<Identifier> list = new ArrayList<>();

        for(Block block : ApiVariationRegistry.getInstance().getPartBenchBlocks())
        {
            list.add(Registry.BLOCK.getId(block));
        }
        for(Block block : ApiVariationRegistry.getInstance().getForgeBlocks())
        {
            list.add(Registry.BLOCK.getId(block));
        }

        if(!packSource.getNamespace().equals("smitheesfoundry"))
        {
            return;
        }
        if(list.contains(new Identifier(packSource.getNamespace(), packSource.getPath().replaceAll("blocks/", ""))))
        {
            cir.setReturnValue(LootTableSource.MOD);
        }
    }
}
